package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // jdbc url parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ06MXV";

    // jdbc url
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    // driver  and connection interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    public static Connection conn;

    private static final String userName = "U06MXV"; //username
    private static final String password = "53688806964"; //password


    public static void startConnection () {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, userName, password);
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed.");
        }
        catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

}
