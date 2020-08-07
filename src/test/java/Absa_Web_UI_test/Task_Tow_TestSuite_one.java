package Absa_Web_UI_test;

import Absa_Web_UI_test.core.BaseClass;
import Absa_Web_UI_test.tools.Reporting;
import Absa_Web_UI_test.tools.SeleniumDriver;
import org.junit.*;
import org.junit.rules.TestName;

import static Absa_Web_UI_test.testing.testclasses.Actions.ExecuteTest;

//@RunWith(JUnitParamsRunner.class)
public class Task_Tow_TestSuite_one extends BaseClass
{

    SeleniumDriver.BrowserType browser;

    @Rule
    public TestName name = new TestName();

    @Before
    public void init()
    {
        testName = name .getMethodName();
        Reporting.createTest();
        browser = SeleniumDriver.BrowserType.CHROME;
        SeleniumDriverInstance = new SeleniumDriver(browser);
    }

    @Test
    public void Way2Automation_one()  {
        String test = ExecuteTest("C:\\Absa-Selenium-Project\\KeywordDrivenTestPacks\\TaskTwo_Automation_one.xlsx");
        Assert.assertNull(test,test);
    }

    @After
    public void tearDown()
    {
        SeleniumDriverInstance.shutdown();
    }
}
