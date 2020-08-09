package Absa_Web_UI_test.core;

import Absa_Web_UI_test.tools.SeleniumDriver;

/* this class holds / positions the the directory of the extent report*/
public class BaseClass {

    public static SeleniumDriver SeleniumDriverInstance;

    private static String _reportDirectory;
    public static void setReportDirectory(String directory){_reportDirectory = directory;}
    public static String getReportDirectory(){return _reportDirectory;}

    public static String testName;

    public static boolean EnableScreenshots;
}
