package FinalProject.Page;

import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class DashboardPage {

    private By menuDashboard = By.xpath("//span[normalize-space()='Dashboard']");
    private By menuCustomers = By.xpath("//span[normalize-space()='Customers']");
    private By menuProjects = By.xpath("//span[normalize-space()='Projects']");
    private By dropdownLogout = By.xpath("//li[contains(@class,'icon header-user-profile')]");
    private By Logout = By.xpath("//ul[@class='dropdown-menu animated fadeIn']//li[@class='header-logout']//a[normalize-space()='Logout']");
    private By menuReport = By.xpath("//span[normalize-space()='Reports']");
    private By IconProject = By.xpath("//a[@data-group='projects']//i[@class='fa-solid fa-chart-gantt menu-icon']");



    public void clickMenuDashboard(){
        WebUI.clickElement(menuDashboard);
    }

    public CustomerPage clickMenuCustomer(){
        WebUI.clickElement(menuCustomers);

        return new CustomerPage();
    }

//    public ProjectPage clickMenuProject(){
//        WebUI.clickElement(menuProjects);
//
//        return new ProjectPage();
//    }
//
//    public ProjectPage clickIconProject(){
//        WebUI.clickElement(IconProject);
//
//        return new ProjectPage();
//    }

    public LoginPage clickLogout(){
        WebUI.clickElement(dropdownLogout);
        WebUI.clickElement(Logout);

        return new LoginPage();
    }

    public void verifyMenuReportDisplay(){
        WebUI.waitForPageLoaded();
        Assert.assertTrue(WebUI.checkElementExist(menuReport),"memuReport is not display");
        Assert.assertTrue(WebUI.checkElementDisplay(menuReport),"memuReport is not display");
    }

    public void verifyMenuReportNotDisplay(){
        WebUI.waitForPageLoaded();
        Assert.assertFalse(WebUI.checkElementExist(menuReport),"memuReport is display");
    }
}
