package Common;

import Listeners.TestListener;
import drivers.DriverManager;
import helpers.PropertiesHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;


@Listeners(TestListener.class)
public class BaseTest {

    @BeforeSuite
    //Chỉ cần load 1 lần là đã lưu giá trị vào bộ nhớ tạm, áp dụng cho toàn phiên chạy
    public void beforeSuite(){
        PropertiesHelper.loadAllFiles();
    }


    @BeforeMethod
    @Parameters({"browser"})
    public void createDriver(@Optional("chrome") String browserName) {
        WebDriver driver = setupBrowser(browserName);
        //WebDriver driver = setupBrowser(PropertieHelper.getValue("browser"));//Khoi tao browser gan vao driver bai 28
        //new WebUI(driver);
        DriverManager.setDriver(driver);//Mang gia tri driver da khoi tao vao trong ThreadLoacal
    }

    //Viết hàm trung gian để lựa chọn Browser cần chạy với giá trị tham số "browser" bên trên truyền vào
    public WebDriver setupBrowser(String browserName){
        WebDriver driver;

        switch (browserName.trim().toLowerCase()) {
            case "chrome":

                // set chrome as Headless
//                ChromeOptions options = new ChromeOptions();
//                if (PropertiesHelper.getValue("HEADLESS").equals("true")){
//                options.addArguments("--headless");
//                }
//                driver = new ChromeDriver(options);
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                break;
            case "firefox":
                driver = new EdgeDriver();
                driver.manage().window().maximize();
                break;
            case "edge":
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                break;
            default:
                System.out.println("Browser: " + browserName + " is invalid, Launching Chrome as browser of choice...");
                driver = new ChromeDriver();
                driver.manage().window().maximize();
        }
        return driver;
    }


    @AfterMethod
    public void closeDriver(ITestResult iTestResult) {

        //Chụp màn hình khi test case bị FAIL. Ngược lại không chụp
//        if(ITestResult.FAILURE == iTestResult.getStatus()){
//            CaptureHelper.captureScreenshot(iTestResult.getName());
//        }
//
//            CaptureHelper.stopRecord();

            DriverManager.quit();
    }
}
