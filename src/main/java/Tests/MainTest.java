package Tests;

import Utils.BaseScript;
import org.openqa.selenium.remote.BrowserType;

public class MainTest extends BaseScript{
    public static void sleep(int secs) {
        try {
            Thread.sleep(secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //Home task 3:
        setBROWSER(BrowserType.IEXPLORE);
        Tests tests = new Tests();
        tests.Login();
        tests.GoToCatalogueTab();
        tests.createCategory();
        //tests.LogOut();
        tests.quit();


        //Home Task 2:
        /*
        //tests.ScriptA_LoginTest();  // Скрипт А. Логин в Админ панель
        //tests.ScriptB_MainMenuTest(); //Скрипт Б. Проверка работоспособности главного меню Админ панели
        test.setDriver();
        test.PageLoad();*/
    }
}
