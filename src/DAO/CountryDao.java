package DAO;

import model.Country;
import model.DatabaseLists;
import utilities.DBConnection;
import utilities.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the data access utility for the Country class
 *
 */
public class CountryDao {

    /** selectAllCountries method - performs an SQL statement that sets the
     * countries ObservableList with data from the database.
     * @throws SQLException
     */
    public static void selectAllCountries() throws SQLException {
        String selectStatement = "SELECT * FROM countries";
        DBQuery.setPreparedStatement(DBConnection.conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();

        while (rs.next()) {
            Country country = new Country(rs.getInt("Country_ID"), rs.getString("Country"));
            DatabaseLists.addCountry(country);
        }
    }
}
