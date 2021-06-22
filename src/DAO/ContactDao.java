package DAO;

import javafx.collections.ObservableList;
import model.Contact;
import model.DatabaseLists;
import utilities.DBConnection;
import utilities.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the data access utility for the Contact class
 *
 */
public class ContactDao {

    /** selectAllContacts method - performs an SQL select statement to set
     * the contacts ObservableList from data on the database.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Contact> selectAllContacts() throws SQLException {
        String sqlStatement = "SELECT * FROM contacts";
        DBQuery.setPreparedStatement(DBConnection.conn, sqlStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();

        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");

            Contact contact = new Contact(contactID, contactName, contactEmail);
            DatabaseLists.addContact(contact);
        }
        return DatabaseLists.contacts;
    }
}
