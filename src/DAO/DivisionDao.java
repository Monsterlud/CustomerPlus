package DAO;

import model.DatabaseLists;
import model.Division;
import utilities.DBConnection;
import utilities.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the data access utility for the Division class.
 *
 */
public class DivisionDao {

    /** selectAllDivisions method - performs an SQL select statement that sets the
     * divisions ObservableList with data from the database.
     * @throws SQLException
     */
    public static void selectAllDivisions() throws SQLException {
        String selectStatement = "SELECT * FROM first_level_divisions";
        DBQuery.setPreparedStatement(DBConnection.conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();

        while (rs.next()) {
            String divisionID = rs.getString("Division_ID");
            String divisionName = rs.getString("Division");
            String countryID = rs.getString("COUNTRY_ID");

            Division division = new Division(Integer.parseInt(divisionID), divisionName, Integer.parseInt(countryID));
            DatabaseLists.addDivision(division);
        }
    }
}
