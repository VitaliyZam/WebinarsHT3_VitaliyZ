package Tests;

import Utils.BaseScript;
import Utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * Created by User on 01.04.2017.
 */


public class Tests extends BaseScript {
    EventFiringWebDriver driver;
    private String BaseUrl = Properties.getBaseAdminUrl();
    private String login = Properties.getLogin();
    private String password = Properties.getPassword();

    public WebDriverWait explicitWait(int sec) {
        return new WebDriverWait(this.driver, sec);
    }

    Tests() {
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);
        this.driver = getDriver();
        PageLoad();
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void mouseOver(WebElement element) {
        String code = "var fireOnThis = arguments[0];"
                + "var evObj = document.createEvent('MouseEvents');"
                + "evObj.initEvent( 'mouseover', true, true );"
                + "fireOnThis.dispatchEvent(evObj);";
        ((JavascriptExecutor) driver).executeScript(code, element);
        WebElement child = element.findElement(By.tagName("span"));
        System.out.println("Hovering over the \"" + child.getText() + "\" tab");
    }

    public void PageLoad() {
        driver.register(new EventHandler());
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.navigate().to(BaseUrl);
        explicitWait(10).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });
        driver.manage().window().maximize();
    }

    public void Login() {
        String email = "email";
        try {
            explicitWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#email")));
            WebElement inputEmail = driver.findElement(By.cssSelector("#email"));
            inputEmail.sendKeys(login);
        } catch (NotFoundException e) {
            addError(email + " input not found");
            driver.quit();
        }
        try {
            explicitWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#passwd")));
            WebElement inputPassword = driver.findElement(By.cssSelector("#passwd"));
            inputPassword.clear();
            inputPassword.sendKeys(password);
        } catch (NotFoundException e) {
            addError("password input not found");
            driver.quit();
        }
        try {
            explicitWait(10).until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-primary:nth-child(1)")));
            WebElement submitBtn = driver.findElement(By.cssSelector("button.btn-primary:nth-child(1)"));
            submitBtn.submit();
        } catch (NotFoundException e) {
            addError("submit button not found");
            driver.quit();
        }
        System.out.println("Log in succeeded");
    }

    public void LogOut() {
        sleep(5000);
        try {
            explicitWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.className("employee_avatar_small")));
            WebElement avatar = driver.findElement(By.className("employee_avatar_small"));
            avatar.click();
        } catch (NotFoundException e) {
            addError("avatar not found");
        }
        try {
            explicitWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.id("header_logout")));
            WebElement logoutBtn = driver.findElement(By.id("header_logout"));
            logoutBtn.click();
            sleep(1000);
            System.out.println("Log out succeeded");

        } catch (NotFoundException e) {
            addError("Logout button not found");
        }
    }

    public void quit() {
        sleep(7000);
        driver.quit();
    }

    public void GoToCatalogueTab() {
        try {
            explicitWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-sidebar")));

            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Hurrah!
            explicitWait(10).until(ExpectedConditions.invisibilityOfElementLocated(By.id("ajax_running")));
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Hurrah!

            WebElement catalogueTab = driver.findElement(By.id("subtab-AdminCatalog"));
            mouseOver(catalogueTab);

            explicitWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.id("subtab-AdminCategories")));
            WebElement createCategSubTab = driver.findElement(By.id("subtab-AdminCategories"));
            createCategSubTab.click();
            sleep(2000);
        } catch (NotFoundException e) {
            addError("Element with id = \"subtab-AdminCatalog\" not found");
        }
    }

    public void createCategory() {
        explicitWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.id("page-header-desc-category-new_category")));
        WebElement addCateg = driver.findElement(By.id("page-header-desc-category-new_category"));
        addCateg.click();

        explicitWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.id("fieldset_0")));
        WebElement categNameInput = driver.findElement(By.id("name_1"));
        String newCategName = "New category by Vitaliy Z. (" + getBROWSER() + ")";
        categNameInput.sendKeys(newCategName);

        explicitWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.id("mce_29-body")));
        WebElement boldBtn = driver.findElement(By.xpath("//div[@id='mce_15']/button"));
        WebElement italicBtn = driver.findElement(By.xpath("//div[@id='mce_16']/button"));
        WebElement underlineBtn = driver.findElement(By.xpath("//div[@id='mce_17']/button"));
        boldBtn.click();
        italicBtn.click();
        underlineBtn.click();

        explicitWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.id("description_1_ifr")));
        WebElement iframeDescription = driver.findElement(By.id("description_1_ifr"));
        driver.switchTo().frame(iframeDescription);
        WebElement descriptionInput = driver.findElement(By.xpath("//body[@id='tinymce']"));
        descriptionInput.sendKeys("Test description");
        driver.switchTo().defaultContent();
        sleep(2000);

        WebElement submitNewCateg = driver.findElement(By.id("category_form_submit_btn"));
        submitNewCateg.click();

        explicitWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@class='nodrag nodrop filter row_hover']")));
        WebElement inputFiltByName = driver.findElement(By.xpath("//input[@name='categoryFilter_name']"));
        inputFiltByName.clear();
        inputFiltByName.sendKeys(newCategName);
        WebElement submitFilterBtn = driver.findElement(By.xpath("//button[@id='submitFilterButtoncategory']"));
        submitFilterBtn.click();
        
        try {
            explicitWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("tbody")));
            String compare = driver.findElement(By.xpath("(//tr/td)[last()-4]")).getText();
            System.out.println("Entered category name: " + newCategName);
            System.out.println("Found category name: " + compare);
            if (compare.contains(newCategName)) System.out.println("The category has been created");
        } catch (NotFoundException e) {
            System.out.println("The category hasn't been created");
        }
    }
}
