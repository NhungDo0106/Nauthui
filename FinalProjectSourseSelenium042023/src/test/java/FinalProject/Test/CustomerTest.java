package FinalProject.Test.Test;

import Common.BaseTest;
import FinalProject.Page.CustomerPage;
import FinalProject.Page.DashboardPage;
import FinalProject.Page.DataProviderFactoryCustomer;
import FinalProject.Page.LoginPage;
import contants.ConfigData;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class CustomerTest extends BaseTest {

    LoginPage loginPage;
    DashboardPage dashboardPage;
    CustomerPage customerPage;


    @Test(priority = 1, dataProvider = "DataAddCustomerSuccessHashtable", dataProviderClass = DataProviderFactoryCustomer.class)
    public void addNewCustomer(Hashtable < String, String > data) {

        //Login
        loginPage = new LoginPage();
        dashboardPage = loginPage.loginCRM(ConfigData.EMAIL1, ConfigData.PASSWORD1);

        //Click menu Customer
        customerPage = dashboardPage.clickMenuCustomer();

        //Click button New Customser
        customerPage.clickButtonAddNew();

        customerPage.verifyCustomerSuccess();
    }

    @Test(priority = 4, dataProvider = "DataAddCustomerSuccessHashtable", dataProviderClass = DataProviderFactoryCustomer.class)
    public void testLoginSuccess1(Hashtable< String, String > data){
        loginPage = new LoginPage();
        String email = data.get("email");
        String password = data.get("password");

        dashboardPage = loginPage.loginCRM(email, password);
        loginPage.verifyLoginSuccess();
    }




//    @Test(priority = 3)
//    public void searchCustomer() {
//        //Login
//        loginPage = new LoginPage();
//        dashboardPage = loginPage.loginCRM("admin@example.com", "123456");
//        //Click menu Customer
//        customerPage = dashboardPage.clickMenuCustomer();
//        //Click textbox Search
//        customerPage.searchCustomer("Customer");
//    }


//    @Test(priority = 18)
//    public void testAddNewCustomer() {
//        //Login
//        loginPage = new LoginPage();
//        dashboardPage = loginPage.loginCRM(ConfigData.EMAIL1, ConfigData.PASSWORD1);
//        loginPage.verifyLoginSuccess();
//        //Click menu Customer
//        customerPage = dashboardPage.clickMenuCustomer();
//        //Click add
//        customerPage.clickButtonAddNew();
//
//        String COMPANY_NAME = "CompanyA";
//        //customerPage.inputFormData(COMPANY_NAME);
//        dashboardPage.clickMenuCustomer();
//        customerPage.searchCustomer(COMPANY_NAME);
//        customerPage.verifyCustomerDetail(COMPANY_NAME);
//    }

}
