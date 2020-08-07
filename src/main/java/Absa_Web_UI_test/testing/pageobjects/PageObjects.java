package Absa_Web_UI_test.testing.pageobjects;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.openqa.selenium.By;

public class PageObjects
{

    private static String xPath="";

    public static String NavigateToUrl(String url)
    {
        return url;
    }

    public static By ClickButton(String section, String path)
    {
        if(path.equals(null) || path.isEmpty())
        {
            xPath ="//label[text()='"+section+"'] | //button[contains(text(),'"+section+"')] | //select[@name='"+section+"'] | //select[@name='RoleId']/..//option[text()='"+section+"'] | " +
                    "//button[@class='"+section+"']";
        }
        else
        {
            xPath =path;
        }
        return By.xpath(xPath);
    }

    public static By EnterText(String section, String path)
    {
        if(path.equals(null) || path.isEmpty())
        {
            xPath ="//input[@placeholder='"+section+"'] | //input[@name='"+section+"']";
        }
        else
        {
            xPath =path;
        }
        return By.xpath(xPath);
    }

    public static By GetText(String section, String path)
    {
        if(path.equals(null) || path.isEmpty())
        {
            xPath ="//span[contains(text(),'"+section+"')] | //h3[text()='"+section+"']";
        }
        else
        {
            xPath =path;
        }
        return By.xpath(xPath);
    }

    public static By Retrieve_RowData(String section, String path)
    {
        if(path.equals(null) || path.isEmpty())
        {
            xPath ="//tr[@class='"+section+"']";
        }
        else
        {
            xPath =path;
        }
        return By.xpath(xPath);
    }
}
