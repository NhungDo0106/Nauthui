package FinalProject.Test;

import Common.BaseTest;
import FinalProject.Page.DashboardPage;
import FinalProject.Page.DataProviderFactoryLogin;
import FinalProject.Page.LoginPage;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class LoginTest extends BaseTest {

        LoginPage loginPage;
        DashboardPage dashboardPage;
        @Test(priority = 1, dataProvider = "DataLoginSuccess", dataProviderClass = DataProviderFactoryLogin.class)
        public void testLoginSuccess(String email, String password){
            loginPage = new LoginPage();

            dashboardPage = loginPage.loginCRM(email, password);
            loginPage.verifyLoginSuccess();
        }

        @Test(priority = 2, dataProvider = "DataLoginEmailInvalid", dataProviderClass = DataProviderFactoryLogin.class)
        public void testLoginWithEmailInvalid(String email, String password){
            loginPage = new LoginPage();

            dashboardPage = loginPage.loginCRM(email, password);

            loginPage.verifyLoginFail();
        }

        @Test(priority = 3, dataProvider = "DataLoginPasswordInvalid", dataProviderClass = DataProviderFactoryLogin.class)
        public void testLoginWithPasswordInvalid(String email, String password){
            loginPage = new LoginPage();

            dashboardPage = loginPage.loginCRM(email, password);
            loginPage.verifyLoginFail();
        }

    @Test(priority = 4, dataProvider = "DataLoginSuccessExcelHashtable", dataProviderClass = DataProviderFactoryLogin.class)
    public void testLoginSuccess1(Hashtable< String, String > data){
        loginPage = new LoginPage();
        String email = data.get("email");
        String password = data.get("password");

        dashboardPage = loginPage.loginCRM(email, password);
        loginPage.verifyLoginSuccess();
    }
}

