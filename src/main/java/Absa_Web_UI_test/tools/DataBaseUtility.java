package Absa_Web_UI_test.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseUtility {

    private Connection conn;
    private String connectionURL;

    public DataBaseUtility(String connectionURL)
    {
        this.connectionURL = connectionURL;
        try {
            this.conn = DriverManager.getConnection(connectionURL);
        }
        catch(Exception e)
        {

        }
    }

    public ResultSet executeQuery(String query)
    {
        try {
            Statement stmnt = this.conn.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            stmnt.close();
            return rs;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public int executeNonQuery(String nonQuery)
    {
        try {
            Statement stmnt = this.conn.createStatement();
            int affected = stmnt.executeUpdate(nonQuery);
            stmnt.close();
            return affected;
        }
        catch(Exception e)
        {
            return 0;
        }
    }

    public boolean closeConnection()
    {
        try {
            if(conn != null) conn.close();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean openConnection()
    {
        try {
            if(conn == null)
            {
                this.conn = DriverManager.getConnection(this.connectionURL);
            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
