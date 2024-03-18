package keywords;

import com.aventstack.extentreports.Status;
import drivers.DriverManager;
import io.qameta.allure.Step;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import reports.AllureManager;
import reports.ExtentTestManager;
import utils.LogUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class WebUI {
    private static int TIMEOUT = 10;//Cho doi cua WebDriverWait
    private static double STEP_TIME = 0.5; // Cho doi cua ham sleep
    private static int PAGE_LOAD_TIMEOUT = 40;// Cho doi trang load

    public static void logConsole(Object message) {
        LogUtils.info(message);
    }


    public static WebElement getWebElement(By by) {
        return DriverManager.getDriver().findElement(by);
    }

        @Step("Open URL: {0}")
    public static void openURL(String url) {
        DriverManager.getDriver().get(url);
        LogUtils.info("Open URL: " + url);
        ExtentTestManager.logMessage(Status.PASS, "Open URL: " + url);
    }

    public static String getCurrentUrl() {
        waitForPageLoaded();
        LogUtils.info("Get Current URL: " + DriverManager.getDriver().getCurrentUrl());
        ExtentTestManager.logMessage(Status.PASS, "Get Current URL: " + DriverManager.getDriver().getCurrentUrl());
        return DriverManager.getDriver().getCurrentUrl();
    }

//    public static List<WebElement> getWebElements(By by){
//        return driver.findElements(by);
//    }


    public static void sleep(double second) {
        try {
            Thread.sleep((long) (2000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Check Element Exist: {0}")
    public static boolean checkElementExist(By by) {
        List<WebElement> listElement = DriverManager.getDriver().findElements(by);

        if (listElement.size() > 0) {
            LogUtils.info("Element " + by + " existing.");
            return true;
        } else {
            LogUtils.info("Element " + by + " NOT exist.");
            return false;
        }
    }

    @Step("Hover on element: {0}")
    public static void hoverOnElement(By by) {
        waitForElementVisible(by);
        Actions action = new Actions(DriverManager.getDriver());
        action.moveToElement(getWebElement(by));
        LogUtils.info("Hover on element " + by);
        ExtentTestManager.logMessage(Status.PASS, "Hover on element " + by);
    }

    @Step("CheckElementDisplay: {0}")
    public static boolean checkElementDisplay(By by){
        waitForElementVisible(by);//cho doi hien thi
        boolean check = getWebElement(by).isDisplayed();//dung getElement de truyen by vao de bien thanh WebElement
        return check;
    }

    @Step("Get text of element: {0}")
    public static String getElementText(By by) {
        waitForElementVisible(by);
        String text = DriverManager.getDriver().findElement(by).getText();
        LogUtils.info("Get text of element: "+ by + "is: " + text);
        ExtentTestManager.logMessage(Status.PASS, "Get text of element " + by+ "is: " + text);
        ExtentTestManager.logMessage(Status.INFO, "==> Text: " + getWebElement(by).getText());

        AllureManager.saveTextLog("===> " + text);
        return text; //Trả về một giá trị kiểu String
    }

    @Step("Get attribute value of element: {0} is value {1}")
    public static String getElementAttribute(By by, String attributeName) {
        waitForElementVisible(by);
        String value = DriverManager.getDriver().findElement(by).getAttribute(attributeName);
        LogUtils.info("Get attribute value of element: "+ by + "is: " + value);
        ExtentTestManager.logMessage(Status.PASS, "Get attribute value of element " + by+ "is: " + value);
        ExtentTestManager.logMessage(Status.INFO, "==> Attribute value: " + getWebElement(by).getAttribute(attributeName));
        return value; //Trả về một giá trị kiểu String
    }

    @Step("Click on element: {0}")
    public static void clickElement(By by){
        waitForElementClickable(by);
        getWebElement(by).click();
        LogUtils.info("Click on element: " + by);
        ExtentTestManager.logMessage(Status.PASS, "Click on element " + by);

    }
    @Step("CLick on element: {0} timeOut {1}")
    public static void clickElement(By by, int timeOut){
        waitForElementClickable(by, timeOut);
        getWebElement(by).click();
        LogUtils.info("CLick on element:"+ by + timeOut + "timeOut");
        ExtentTestManager.logMessage(Status.PASS, "Click on element " + by + timeOut+ "timeOut");
    }

    @Step("Set text: {0} on input {1}")
    public static void setText(By by, String text){
        waitForElementVisible(by);
        getWebElement(by).sendKeys(text);
        LogUtils.info("Set text " + text + " on input " + by);
        ExtentTestManager.logMessage(Status.PASS, "Set text " + text + " on input " + by);
    }

    @Step("Set text: {1} on input by {0} timeOut {2}")
    public static void setText(By by, String text, int timeOut){
        waitForElementVisible(by, timeOut);
        getWebElement(by).sendKeys(text);
        LogUtils.info("Set text "+ text + " on input "+ by + timeOut + "timeOut");
        ExtentTestManager.logMessage(Status.PASS, "Set text " + text + " on input " + by + timeOut+ "timeOut");
    }


    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        //Wait for Javascript to load
        ExpectedCondition< Boolean > jsLoad = new ExpectedCondition < Boolean > () {
            @Override
            public Boolean apply(WebDriver driver) {
                return js.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        //Check JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            LogUtils.info("Javascript is NOT Ready.");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                LogUtils.error(error.getStackTrace());
                Assert.fail("FAILED. Timeout waiting for page load.");
            }
        }
    }

    @Step("imageName {0}")
    public static void CaptureScreenImage(String imageName) {

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        //Get size screen browser
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        LogUtils.info(screenSize);
        //Khởi tạo kích thước khung hình với kích cỡ trên
        Rectangle screenRectangle = new Rectangle(screenSize);
        //Tạo hình chụp với độ lớn khung đã tạo trên
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        //Lưu hình vào dạng file với dạng png
        File file = new File("/Volumes/IT - data/autumation42023/JavaBasic_soucetree/SeleniumTestNGParallel042023/src/image/"+ imageName + ".png");
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //Wait for Element

    @Step("Timeout waiting for the element Visible {0}")
    public static void waitForElementVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    @Step("Timeout waiting for the element Visible {0} timeOut {1}")
    public static void waitForElementVisible(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error(("Timeout waiting for the element Visible. " + by.toString()));
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    @Step("Element not exist {0}")
    public static void waitForElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error(("Element not exist. " + by.toString()));
            Assert.fail("Element not exist. " + by.toString());
        }
    }
    @Step("Element not exist {0} timeOut {1}")
    public static void waitForElementPresent(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error(("Element not exist. " + by.toString()));
            Assert.fail("Element not exist. " + by.toString());
        }
    }

    @Step("Timeout waiting for the element ready to click {0}")
    public static void waitForElementClickable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            LogUtils.error(("Timeout waiting for the element ready to click. " + by.toString()));
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
    }

    @Step("Timeout waiting for the element ready to click {0} timeOut")
    public static void waitForElementClickable(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            LogUtils.error(("Timeout waiting for the element ready to click. " + by.toString()));
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
    }

    @Step("Timeout waiting for the element NOT visible {0}")
    public static void waitForElementNotVissible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.invisibilityOfElementLocated((by)));
        } catch (Throwable error) {
            LogUtils.error(("Timeout waiting for the element NOT visible. " + by.toString()));
            Assert.fail("Timeout waiting for the element NOT visible. " + by.toString());
        }
    }

    @Step("Timeout waiting for the element NOT visible {0} timeOut {1}")
    public static void waitForElementNotVissible(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.invisibilityOfElementLocated((by)));
        } catch (Throwable error) {
            LogUtils.error(("Timeout waiting for the element NOT visible. " + by.toString()));
            Assert.fail("Timeout waiting for the element NOT visible. " + by.toString());
        }
    }


//Các hàm xử lý

    public static void setKey(By by, Keys key) {
        waitForPageLoaded();
        getWebElement(by).sendKeys(key);
        LogUtils.info("Set text: " + key.name() + " on element " + by);
    }

    public static void setTextAndKey(By by, String value, Keys key) {
        waitForPageLoaded();
        getWebElement(by).sendKeys(value, key);
        LogUtils.info("Set text: " + value + " on element " + by);
    }

    public static void scrollToElement(By element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", getWebElement(element));
    }

    public static void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToPosition(int X, int Y) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.scrollTo(" + X + "," + Y + ");");
    }

    public static boolean moveToElement(By toElement) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static boolean moveToOffset(int X, int Y) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveByOffset(X, Y).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static boolean hoverElement(By by) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean mouseHover(By by) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean dragAndDrop(By fromElement, By toElement) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
            //action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static boolean dragAndDropElement(By fromElement, By toElement) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static boolean dragAndDropOffset(By fromElement, int X, int Y) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            //Tính từ vị trí click chuột đầu tiên (clickAndHold)
            action.clickAndHold(getWebElement(fromElement)).pause(1).moveByOffset(X, Y).release().build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static boolean pressENTER() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean pressESC() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean pressF11() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_F11);
            robot.keyRelease(KeyEvent.VK_F11);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param by truyền vào đối tượng element dạng By
     * @return Tô màu viền đỏ cho Element trên website
     */
    public static WebElement highLightElement(By by) {
        // Tô màu border ngoài chính element chỉ định - màu đỏ (có thể đổi màu khác)
        if (DriverManager.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].style.border='3px solid red'", getWebElement(by));
            sleep(1);
        }
        return getWebElement(by);
    }

    public static void rightClickElement(By by) {
        waitForElementVisible(by);
        Actions action = new Actions(DriverManager.getDriver());
        action.contextClick(getWebElement(by));
        LogUtils.info("Right click on element " + by);
        //ExtentTestManager.logMessage(Status.PASS, "Right click on element " + by);
    }


    //Verify va Assert
    public static boolean verifyEquals(Object actual, Object expected) {
        waitForPageLoaded();
        LogUtils.info("Verify equals: " + actual + " and " + expected);
        boolean check = actual.equals(expected);
        return check;
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        waitForPageLoaded();
        LogUtils.info("Assert equals: " + actual + " and " + expected);
        Assert.assertEquals(actual, expected, message);
    }

    public static boolean verifyContains(String actual, String expected) {
        waitForPageLoaded();
        LogUtils.info("Verify contains: " + actual + " and " + expected);
        boolean check = actual.contains(expected);
        return check;
    }

    public static void assertContains(String actual, String expected, String message) {
        waitForPageLoaded();
        LogUtils.info("Assert contains: " + actual + " and " + expected);
        boolean check = actual.contains(expected);
        Assert.assertTrue(check, message);
    }
}
