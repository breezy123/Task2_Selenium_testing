package Absa_Web_UI_test.tools;

import Absa_Web_UI_test.entities.DataColumn;
import Absa_Web_UI_test.entities.DataRow;
import Absa_Web_UI_test.entities.DataTable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;

public class ExcelReaderUtility {

    public static XSSFWorkbook getWorkbook(String filePath)
    {

        try
        {
            FileInputStream excelFile = new FileInputStream((new File(filePath)));
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);

            return workbook;
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    public static XSSFSheet getWorkSheet(String filePath, String sheetName)
    {
        try
        {
            XSSFWorkbook workbook = getWorkbook(filePath);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            return sheet;
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    public static DataTable getSheetAsTable(String filepath, String sheetName)
    {
        try {
            DataTable table = new DataTable();
            LinkedList<DataRow> dataRows = new LinkedList<>();
            Sheet sheet = getWorkSheet(filepath,sheetName);
            Row firstRow = sheet.getRow(0);
            int lastColumn = firstRow.getLastCellNum();


            for(Row row : sheet)
            {
                DataRow currentRow = new DataRow();
                if(row.getRowNum() > 0 && !row.getCell(0).equals(""))
                {
                    for (int i =0 ; i < lastColumn; i++)
                    {
                        Cell currentCell = row.getCell(i);
                        DataColumn column;

                        if(currentCell == null)
                        {
                            column= new DataColumn(firstRow.getCell(i).getStringCellValue(), "");
                        }
                        else
                        {
                            column= new DataColumn(firstRow.getCell(i).getStringCellValue(), currentCell.getStringCellValue());
                        }
                        currentRow.DataColumns.add(column);
                    }
                    table.Rows.add(currentRow);
                }
            }

            return table;
        }catch (Exception e)
        {
            return null;
        }
    }
}
