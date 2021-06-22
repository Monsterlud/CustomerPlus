package DAO;

import javafx.collections.ObservableList;
import model.DatabaseLists;
import model.User;
import utilities.DBConnection;
import utilities.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the data access utility for the User class.
 *
 */
public class UserDao {

    /** selectAllUsers method - performs an SQL select method that sets the
     * users ObservableList with data from the database.
     * @return
     * @throws SQLException
     */
    public static ObservableList<User> selectAllUsers() throws SQLException {
        String sqlStatement = "SELECT * FROM users";
        DBQuery.setPreparedStatement(DBConnection.conn, sqlStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();

        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");

            User user = new User(userID, userName, password);
            DatabaseLists.addUser(user);
        }
        return DatabaseLists.users;
    }
}
