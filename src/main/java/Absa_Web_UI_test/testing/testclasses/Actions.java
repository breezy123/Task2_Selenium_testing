package Absa_Web_UI_test.testing.testclasses;

import Absa_Web_UI_test.core.BaseClass;
import Absa_Web_UI_test.entities.DataRow;
import Absa_Web_UI_test.entities.DataTable;
import Absa_Web_UI_test.testing.pageobjects.PageObjects;
import Absa_Web_UI_test.tools.ExcelReaderUtility;
import Absa_Web_UI_test.tools.Reporting;
import Absa_Web_UI_test.tools.SeleniumDriver;
import org.apache.poi.ss.usermodel.Workbook;

/*
 Test Action class, here is where the actual execution occurs. ExcelReaderUtility class is called
 to utilize the data stored in the external spreed-sheet file.

 Keywords are used to perform a certain action (CLICK_BUTTON, ENTER_TEXT). These keywords come straight
 from the spread-sheet file with the actual data to click, enter, validate text-field/button.

 */
public class Actions extends BaseClass {

    public static String ExecuteTest(String workBook) {

        try
        {
            String test = "";

            Workbook _workBook = ExcelReaderUtility.getWorkbook(workBook); //passing the actual file path to get the workbook

            DataTable table; // creating table structure to store the column names in the spread-sheet file (section, path, action and value

            for (int i = 0; i < _workBook.getNumberOfSheets(); i++) {
                table = ExcelReaderUtility.getSheetAsTable(workBook, _workBook.getSheetAt(i).getSheetName()); //getting the sheet as table, executing data on that actual Sheet name

                for (DataRow dr : table.Rows) {
                    String sectionValue = dr.getColumnValue("Section").toString();
                    String pathValue = dr.getColumnValue("Path").toString();
                    String actionValue = dr.getColumnValue("Action").toString();
                    String actualValue = dr.getColumnValue("Value").toString();

                    test = ActionPerform(sectionValue, pathValue, actionValue, actualValue); // the ActionPerform method is called here, to perform the desired actions

                    if (test != null) {
                        return Reporting.testFailed(test);
                    }
                }
            }
        }
        catch (Exception ex){
            System.err.println("[Error] Reading TestPack. " +ex.getMessage());
        }

        return Reporting.finaliseTest();
    }

    public static String ActionPerform(String section, String path, String action, String value) {

        switch (action.toUpperCase()) {
            case "NAVIGATE":

                if (!SeleniumDriverInstance.navigate(PageObjects.NavigateToUrl(section + path))) {
                    return "Failed to Navigate to: " + section + path;
                }
                Reporting.stepPassed("Navigate to: " + section + path);
                break;

            case "VALIDATE_TEXT":
                if (!SeleniumDriverInstance.waitForElement(PageObjects.GetText(section, path))) {
                    return "Failed to wait text: " + section + path + " using the Xpath " + PageObjects.GetText(section, path);
                }
                Reporting.stepPassed("Validate text exists: " + value);
                break;

            case "VALIDATE_INPUT":
                if (!SeleniumDriverInstance.waitForElement(PageObjects.EnterText(section, path))) {
                    return "Failed to wait for input field: " + section + path + " using the Xpath " + PageObjects.EnterText(section, path);
                }
                Reporting.stepPassed("Validate input exists: " + value);
                break;

            case "VALIDATE_BUTTON":
                if (!SeleniumDriverInstance.waitForElement(PageObjects.ClickButton(section, path))) {
                    return "Failed to wait for button: " + section + path + " using the Xpath " + PageObjects.ClickButton(section, path);
                }
                Reporting.stepPassed("Validate input exists: " + value);
                break;
            case "ENTER_TEXT":

                if (!SeleniumDriverInstance.EnterText(PageObjects.EnterText(section, path), value)) {
                    return "Failed to Enter text: " + value + " using the xpath: " + PageObjects.EnterText(section, path);
                }
                Reporting.stepPassed("Entered text: " + value);
                break;

            case "CLICK_BUTTON":
                if (!SeleniumDriverInstance.clickElement(PageObjects.ClickButton(section, path))) {
                    return "Failed to Click element: " + value + " using the xpath: " + PageObjects.ClickButton(section, path);
                }
                Reporting.stepPassed("Clicked element: " + value);
                break;

            case "GET_DROP_DOWN":
                if (!SeleniumDriverInstance.getDropDownText(PageObjects.GetText(section, path))) {
                    return "Failed to retrieve selected text from Dropdown using the xpath: " + SeleniumDriverInstance.getDropDownText(PageObjects.GetText(section, path));
                }
                Reporting.stepPassed("Retrieved text from dropdown: " + PageObjects.ClickButton(section, path));
                break;

            case "ENTER_UNIQUE_TEXT":
                if (!SeleniumDriverInstance.enter_Unique_text(PageObjects.EnterText(section, path),value)) {
                    return "Failed to enter unique text using the xpath: " + SeleniumDriverInstance.enter_Unique_text(PageObjects.EnterText(section, path),value);
                }
                Reporting.stepPassed("Entered unique text for username: "+value+"-"+SeleniumDriverInstance.generate_UniqueText());
                break;

            case "RETRIEVE_ROW_TEST":
                if (!SeleniumDriverInstance.retrieve_TableRow(PageObjects.Retrieve_RowData(section, path))) {
                    return "Failed to Table Row data using the xpath: " + section;
                }
                Reporting.rowData(SeleniumDriver.rowData);
                break;

            default:
                return "Failed to perform test action: "+action;
        }
        return null;
    }
}

