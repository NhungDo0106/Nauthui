package FinalProject.Page;

import contants.ConfigData;
import drivers.DriverManager;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class LoginPage {
    //Khai bao driver cuc bo trong chinh class nay

    //Khai bao cac element dang doi tuong By (phuong thuc tim kiem)
    private By headerPage = By.xpath("//h1[normalize-space()='Login']");
    private By inputEmail = By.xpath("//input[@id ='email']");
    private By inputPassword = By.xpath("//input[@id ='password']");
    private By buttonLogin = By.xpath("//button[@type ='submit']");
    private By errorMessage = By.xpath("//div[@id='alerts']");
    private By errorMessageFormat = By.xpath("//div[@class='alert alert-danger text-center']");
    private By menuDashboard = By.xpath("//ul[@id='side-menu']");
    //private By menuDashboard = By.xpath("//span[normalize-space()= 'Dashboard']");
    public static String menuDashboard1 = "//ul[@id='side-menu']";


    public void setEmail(String email) {
        //wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail));
        //driver.findElement(inputEmail).sendKeys(inputEmail); //Kieu truyen thong
        //WebUI.getWebElement(inputEmail).sendKeys(email); Kieu dung webUI
        WebUI.setText(inputEmail, email);
    }

    public void setPassword(String password) {
        //wait.until(ExpectedConditions.visibilityOfElementLocated(inputPassword));
        //driver.findElement(inputPassword).sendKeys(password);
        //WebUI.getWebElement(inputPassword).sendKeys(password); Kieu dung webUI
        WebUI.setText(inputPassword, password);
    }

    public void clickButtonLogin() {
        //wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLogin));
        //driver.findElement(buttonLogin).click();
        WebUI.clickElement(buttonLogin);
    }

    public void verifyLoginSuccess(){
        WebUI.waitForPageLoaded();
        Assert.assertFalse(DriverManager.getDriver().getCurrentUrl().contains("authentication"),"Login not successfully");
        String header = WebUI.getWebElement(menuDashboard).getText();
        //System.out.println(header);//in ra tat ca danh sach menu
    }

    public void verifyLoginFail(){
        WebUI.waitForPageLoaded();
        //checkElementExist checkElement = new checkElementExist(driver);
        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("authentication"),"FAIL. Khong con o trang Login");
        Assert.assertTrue(DriverManager.getDriver().findElement(errorMessage).isDisplayed(),"Error message NOT displays");
        //Assert.assertTrue(driver.findElement(errorMessageFormat).isDisplayed(),"Error message format NOT displays");
        //Assert.assertEquals(driver.findElement(errorMessage).getText(),"Invalid email or password","Content of error massage NOT match");//code chua that the WebUI
        Assert.assertEquals(WebUI.getElementText(errorMessage),"Invalid email or password","Content of error massage NOT match");
        //Assert.assertFalse(checkElement.checkElementExist(menuDashboard1),"Fail. Den duoc trang Dashboard");
    }

    public void verifyLoginFormat(){
        WebUI.waitForPageLoaded();
        Assert.assertTrue(DriverManager.getDriver().findElement(errorMessageFormat).isDisplayed(),"Error message format NOT displays");
    }

    // Cac ham xu ly cho chinh trang nay
    public DashboardPage loginCRM(String email, String password) {
        WebUI.openURL(ConfigData.URLCRM);
        WebUI.waitForPageLoaded();
        setEmail(email);
        setPassword(password);
        clickButtonLogin();

        return new DashboardPage();
    }
}
