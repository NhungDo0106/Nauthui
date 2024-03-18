package Listeners;

import com.aventstack.extentreports.Status;
import drivers.DriverManager;
import helpers.CaptureHelper;
import helpers.PropertiesHelper;
import keywords.WebUI;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.AllureManager;
import reports.ExtentReportManager;
import reports.ExtentTestManager;
import utils.LogUtils;

public class TestListener implements ITestListener {


    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    @Override
    public void onStart(ITestContext arg0) {
        // TODO Auto-generated method stub
        LogUtils.info("**** " + arg0.getName() + " ****");

    }

    @Override
    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub
        LogUtils.info("onTestStart: " + result.getName());
        CaptureHelper.startRecord(result.getName());

        //Bat dau ghi 1 test case moi vao Extent Report
        ExtentTestManager.saveToReport(getTestName(result), getTestDescription(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub
        LogUtils.info("Success: " + result.getName() + "is successfully");
        CaptureHelper.captureScreenshot(result.getName());
        CaptureHelper.stopRecord();
        //Extent Report
        ExtentTestManager.logMessage(Status.PASS, result.getName() + " is passed.");
        AllureManager.saveScreenshotPNG();//chup mh

    }

    @Override
    public void onTestFailure(ITestResult result) {
        // TODO Auto-generated method stub
        LogUtils.error("Fail: " + result.getName() + "is Fail");
        CaptureHelper.captureScreenshot(result.getName());
        CaptureHelper.stopRecord();

        //Extent Report
        ExtentTestManager.addScreenShot(result.getName());//chup 1 anh
        ExtentTestManager.logMessage(Status.FAIL, result.getThrowable().toString());//show ra ly do
        ExtentTestManager.logMessage(Status.FAIL, result.getName() + " is failed.");//ket luat test case fail

        //Allure Report
        //AllureManager.saveTextLog(result.getName() + " is failed.");//Chi dinh mh fail
        AllureManager.saveScreenshotPNG();//chup mh
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub
        LogUtils.warn("onTestSkipped: " + result.getName());
        CaptureHelper.stopRecord();

        LogUtils.error(result.getThrowable().toString());

        hgygvhghvghjvgjhghjghjg

        //Extent Report
        ExtentTestManager.logMessage(Status.SKIP, result.getThrowable().toString());

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub
        LogUtils.info("onTestFailedButWithinSuccessPercentage: " + arg0.getName());

    }


    @Override
    public void onFinish(ITestContext arg0) {
        // TODO Auto-generated method stub
        LogUtils.info("onFinish: " + arg0.getName());
        //Ket thu va thuc thi Report
        ExtentReportManager.getExtentReports().flush();

    }
}
