package Absa_Web_UI_test.tools;

import Absa_Web_UI_test.core.BaseClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.Random;

public class SeleniumDriver extends BaseClass {

    public enum BrowserType{CHROME, FIREFOX, IE} //choosing browser type

    private WebDriver driver;
    private BrowserType currentBrowser;
    private int screenShotCounter=0;
    public static String uniqueText ="";
    public static String rowData="";
    public SeleniumDriver(BrowserType browser)
    {
        this.currentBrowser = browser;
        launchDriver();
    }

    public SeleniumDriver(BrowserType browser, Dimension dim)
    {
        this.currentBrowser = browser;
        launchDriver(dim);
    }

    public WebDriver getDriver()
    {
        return driver;
    }

    public BrowserType getCurrentBrowser()
    {
        return currentBrowser;
    }

    public void launchDriver()
    {
        try
        {
            switch(currentBrowser)
            {
                case CHROME:

                    WebDriverManager.chromedriver().setup();
                    this.driver = new ChromeDriver();
                    break;
                case FIREFOX:
                    WebDriverManager.firefoxdriver().setup();
                    this.driver = new FirefoxDriver();
                    break;
                case IE:
                    WebDriverManager.iedriver().setup();
                    InternetExplorerOptions ieOpts = new InternetExplorerOptions();
                    ieOpts.ignoreZoomSettings();
                    ieOpts.destructivelyEnsureCleanSession();
                    ieOpts.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
                    ieOpts.setCapability("ignoreProtectedModeSettings", true);
                    this.driver = new InternetExplorerDriver(ieOpts);
                    break;
                default:
                    System.err.println("Invalid Browser");
                    break;
            }
        }
        catch(Exception ex)
        {
            System.err.println("[Error]: Error Launching driver "+ex.getMessage());
        }

        this.driver.manage().window().maximize();
    }

    public void launchDriver(Dimension dim)
    {
        switch(currentBrowser)
        {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                this.driver = new ChromeDriver();
                break;

                default:
                    break;
        }
        this.driver.manage().window().maximize();
        this.driver.manage().window().setSize(dim);
    }

    public boolean navigate(String url)
    {
        try
        {
            this.driver.navigate().to(url);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public boolean clickElement (By selector)
    {
        try
        {
            waitForElement(selector);
            WebDriverWait wait = new WebDriverWait(this.driver, 1);
            WebElement elementToClick = wait.until(ExpectedConditions.elementToBeClickable(selector));
            elementToClick.click();
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public boolean EnterText(By selector, String text)
    {
        try
        {
            waitForElement(selector);
            WebElement element = this.driver.findElement(selector);
            element.sendKeys(text);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public boolean retrieve_TableRow(By selector)
    {
        try
        {
            waitForElement(selector);
            WebElement element = this.driver.findElement(selector);

            rowData=element.getText();
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public boolean waitForElement(By selector)
    {
        boolean found =false;
        int counter =0;

        try
        {
            WebDriverWait wait = new WebDriverWait(this.driver,1);
            while(!found && counter <40)
            {
                try
                {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
                    found = true;
                }
                catch(Exception ex)
                {
                    counter++;
                }
            }
            return found;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public boolean getInputText(By selector)
    {
        try
        {
            waitForElement(selector);
            WebElement element = this.driver.findElement(selector);

            element.getAttribute("value");
            //return true;
        }
        catch(Exception ex)
        {
            System.err.println("[Error]: Failed to find text field attributes");
            return false;
        }
        return true;
    }

    public boolean getDropDownText(By selector)
    {
        try
        {
            waitForElement(selector);
            WebElement element = this.driver.findElement(selector);

            element.getAttribute("selected");
            return true;
        }
        catch(Exception ex)
        {
            System.err.println("[Error]: Failed to find drop-down attributes");
            return false;
        }
    }

    public boolean clearText(By selector)
    {
        try
        {
            waitForElement(selector);
            WebElement element = this.driver.findElement(selector);

            element.clear();
            return true;
        }
        catch(Exception ex)
        {
            System.err.println("[Error]: Failed clear text-field");
            return false;
        }
    }

    public String  generate_UniqueText(){

            String generatedString;
            return  generatedString = RandomStringUtils.randomAlphabetic(5);
    }

    public boolean enter_Unique_text(By selector, String text)
    {
        try
        {
            waitForElement(selector);
            WebElement element = this.driver.findElement(selector);

            uniqueText = text+"-"+generate_UniqueText();
            element.clear();
            element.sendKeys(uniqueText);
            return true;
        }
        catch(Exception ex)
        {
            System.err.println("[Error]: Failed to Enter text-field");
            return false;
        }
    }

    public int numberOfRow(By selector, String xPath){

        List<WebElement> rows = driver.findElements(By.xpath(xPath));
        return rows.size();
    }

    public boolean shutdown()
    {
        try
        {
            this.driver.close();
            this.driver.quit();
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public String takeScreenshot(boolean status)
    {
        StringBuilder imagePathBuilder = new StringBuilder();
        StringBuilder relativePathBuilder = new StringBuilder();
        screenShotCounter++;
        try
        {
            imagePathBuilder.append(getReportDirectory());
            relativePathBuilder.append("Screenshots\\");
            new File(imagePathBuilder.toString()+(relativePathBuilder).toString()).mkdirs();

            relativePathBuilder.append(screenShotCounter+"_");
            if(status)
            {
                relativePathBuilder.append("PASSED");
            }
            else{
                relativePathBuilder.append("FAILED");
            }
            relativePathBuilder.append(".png");

            File screenshot = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot,new File(imagePathBuilder.append(relativePathBuilder).toString()));
            return "./"+relativePathBuilder.toString();
        }
        catch(Exception ex)
        {
            return null;
        }
    }
}
