package utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

    //create PreparedStatement object
    private static PreparedStatement statement;

    // set PreparedStatement
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }

    // return PreparedStatement object
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }



}
