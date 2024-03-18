package drivers;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager() {
    }
//Thay the cho tat ca cac gia tri driver binh thuong trong project
    public static WebDriver getDriver() {
        return driver.get();
    }
//Dung tai BaseTest vi tri Before (can set gia tri driver truoc khi chay test case)
    public static void setDriver(WebDriver driver) {
        DriverManager.driver.set(driver);
    }
//Dung tai BaseTest vi tri After (de retest gia tri driver ve null
//va xoa luon vi tri cua driver do trong ThreadLocal sau moi test case
    public static void quit() {
            DriverManager.driver.get().quit();
            driver.remove();
    }
}
