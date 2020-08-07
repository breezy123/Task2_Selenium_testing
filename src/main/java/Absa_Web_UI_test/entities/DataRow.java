package Absa_Web_UI_test.entities;

import static java.lang.System.err;
import java.util.LinkedList;
import java.util.function.Predicate;

public class DataRow
{

    public LinkedList<DataColumn> DataColumns;

    public DataRow()
    {
        DataColumns = new LinkedList<>();
    }

    public String getColumnValue(final String columnHeader)
    {
        try
        {
            Predicate<DataColumn> predicate = c-> c.columnHeader.equals(columnHeader);
            DataColumn  obj = DataColumns.stream().filter(predicate).findFirst().get();
            return obj.columnValue;
        }
        catch(Exception ex)
        {
            err.println("[ERROR] Could not find column - " + columnHeader + " - in table row");
            return "";
        }

    }

    public DataColumn getColumn(String columnHeader)
    {
        try
        {
            java.util.function.Predicate<DataColumn> predicate = c-> c.columnHeader.equals(columnHeader);
            DataColumn  obj = DataColumns.stream().filter(predicate).findFirst().get();
            return obj;
        }
        catch(Exception ex)
        {
            err.println("[ERROR] Could not find column - " + columnHeader + " - in table row");
            return null;
        }

    }
}
