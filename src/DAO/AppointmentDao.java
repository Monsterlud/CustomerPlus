package DAO;

import model.*;
import utilities.DBConnection;
import utilities.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** This is the data access utility for the Appointment class.
 *
 */
public class AppointmentDao {

    /** selectAllAppointments method - performs a SQL select statement used to set
     * the appointments ObservableList with data from the database.
     * @throws SQLException
     */
    public static void selectAllAppointments() throws SQLException {
        String sqlStatement = "SELECT Appointment_ID, Title, Description, Location, " +
                "Type, Start, End, b.Customer_ID, b.Customer_Name, b.Address, b.Postal_Code, " +
                "b.Phone, c.Contact_ID, c.Contact_Name, c.Email, users.User_ID, users.User_Name, users.Password " +
                "FROM appointments a " +
                "JOIN customers b ON a.Customer_ID = b.Customer_ID " +
                "JOIN contacts c ON a.Contact_ID = c.Contact_ID " +
                "JOIN users ON users.User_ID = a.User_ID";
        DBQuery.setPreparedStatement(DBConnection.conn, sqlStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();

        while (rs.next()) {
            String apptID = rs.getString("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String apptDescription = rs.getString("Description");
            String apptLocation = rs.getString("Location");
            String apptType = rs.getString("Type");
            String apptStart = rs.getString("Start");
            String apptEnd = rs.getString("End");

            String customerID = rs.getString("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostCode = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");

            String contactID = rs.getString("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");

            String userID = rs.getString("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");

            Appointment appointment = new Appointment(Integer.parseInt(apptID), apptTitle,
                    apptDescription, apptLocation, new MeetingType(apptType),
                    LocalDateTime.parse(apptStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    LocalDateTime.parse(apptEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    new Customer(Integer.parseInt(customerID), customerName, customerAddress, customerPostCode, customerPhone),
                    new Contact(Integer.parseInt(contactID), contactName, contactEmail), new User(Integer.parseInt(userID), userName, userPassword));

            DatabaseLists.addAppt(appointment);
//            System.out.println(apptTitle + " | " + customerName + " | " + apptStart + " | " + apptEnd);
//            System.out.println(appointment.getApptStartLDT());

        }
    }

    /** insertAppointment method - performs an SQL insert statement to add an appointment to
     * the database. contains the fields from the Appointment class.
     * @param apptTitle
     * @param apptDescription
     * @param apptLocation
     * @param type
     * @param apptStart
     * @param apptEnd
     * @param customer
     * @param user
     * @param contact
     * @throws SQLException
     */
    public static void insertAppointment(String apptTitle, String apptDescription, String apptLocation, MeetingType type,
                                         LocalDateTime apptStart, LocalDateTime apptEnd, Customer customer, User user,
                                         Contact contact) throws SQLException {
        String insertStatement = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        DBQuery.setPreparedStatement(DBConnection.conn, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //key-value mapping
        ps.setString(1, apptTitle);
        ps.setString(2, apptDescription);
        ps.setString(3, apptLocation);
        ps.setString(4, type.getTypeName());
        ps.setString(5, String.valueOf(apptStart));
        ps.setString(6, String.valueOf(apptEnd));
        ps.setInt(7, customer.getCustomerID());
        ps.setInt(8, user.getUserID());
        ps.setInt(9, contact.getContactID());

        ps.execute();
    }

    public static void deleteAppointment(int appointmentID) throws SQLException {
        String deleteSQL = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentID;
        DBQuery.setPreparedStatement(DBConnection.conn, deleteSQL);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
    }

    /** updateAppointment method - performs an SQL update statement to update an
     * appointment on the database. Contains all of the fields from the Appointment
     * class.
     * @param apptID
     * @param apptTitle
     * @param apptDescription
     * @param apptLocation
     * @param type
     * @param apptStart
     * @param apptEnd
     * @param customer
     * @param contact
     * @param user
     * @throws SQLException
     */
    public static void updateAppointment (int apptID, String apptTitle, String apptDescription,
                                       String apptLocation, MeetingType type, LocalDateTime apptStart,
                                       LocalDateTime apptEnd, Customer customer, Contact contact, User user) throws SQLException {
        String updateSQL = "UPDATE appointments SET " +
                "Title = '" + apptTitle + "', " +
                "Description = '" + apptDescription + "', " +
                "Location = '" + apptLocation + "', " +
                "Type = '" + type.getTypeName() + "', " +
                "Start = '" + apptStart + "', " +
                "End = '" + apptEnd + "', " +
                "Customer_ID = '" + customer.getCustomerID() + "', " +
                "Contact_ID = '" + contact.getContactID() +
                "' WHERE Appointment_ID = " + apptID;

        DBQuery.setPreparedStatement(DBConnection.conn, updateSQL);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.execute();

    }
}
