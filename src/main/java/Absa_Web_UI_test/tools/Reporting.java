package Absa_Web_UI_test.tools;

import Absa_Web_UI_test.core.BaseClass;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reporting extends BaseClass {

    private static ExtentReports report;
    private static ExtentTest currentTest;

    private static void setup()
    {
        setReportDirectory(System.getProperty("user.dir")+"\\Reports\\"+testName+"\\"+getCurrentTime()+"\\");
        new File(getReportDirectory()).mkdirs();

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(getReportDirectory()+"extentReport.html");
        report = new ExtentReports();
        report.attachReporter(htmlReporter);
        report.setAnalysisStrategy(AnalysisStrategy.TEST);
        report.flush();
    }

    private static String takeScreenshot(boolean status)
    {
        return SeleniumDriverInstance.takeScreenshot(status);
    }

    public static void createTest()
    {
        if(report == null) setup();
        currentTest = report.createTest(testName);
        report.flush();
    }
    public static String testFailed(String message)
    {
        if(report ==null) setup();
        if(!checkTests())createTest();

        try
        {
            currentTest.fail(message, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot(false)).build());
        }
        catch(Exception ex)
        {
            currentTest.info("Unable to add screenshot to report - "+ex.getMessage());
            currentTest.fail(message);
        }

        System.out.println("[FAILED] - "+message);
        report.flush();
        return message;
    }

    public static String finaliseTest(){
        if(report == null) setup();
        if(currentTest == null || !checkTests()) createTest();
        try
        {
            currentTest.pass("Test Complete", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot(true)).build());
        }
        catch(Exception e)
        {
            currentTest.info("Unable to add screenshot to report - "+e.getMessage());
            currentTest.pass("Test Complete");
        }
        System.out.println("[TEST PASSED] - "+"Test Complete");
        report.flush();
        return null;
    }

    public static void stepPassed(String message)
    {
        if(report == null) setup();
        if(!checkTests()) createTest();
        currentTest.pass(message);
        System.out.println("[STEP PASSED] - "+message);
    }

    public static void stepPassedScreenshot(String message)
    {
        if(report == null) setup();
        if(!checkTests()) createTest();
        try
        {
            currentTest.pass(message, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot(true)).build());
        }
        catch(Exception e)
        {
            currentTest.info("Unable to add screenshot to report - "+e.getMessage());
            currentTest.pass(message);
        }
        System.out.println("[STEP PASSED] - "+message);
    }

    public static void stepInformation(String message)
    {
        if(report == null) setup();
        if(!checkTests()) createTest();
        currentTest.info(message);
        System.out.println("[INFO] - "+message);
    }

    public static void warning(String message)
    {
        if(report == null) setup();
        if(!checkTests()) createTest();
        currentTest.warning(message);
        System.out.println("[WARNING] - "+message);
    }

    private static boolean checkTests()
    {
        return currentTest.getModel().getName().equals(testName);
    }

    public static String getCurrentTime()
    {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
        return ft.format(date);
    }
}
