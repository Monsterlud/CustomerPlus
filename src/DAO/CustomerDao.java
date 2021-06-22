package DAO;

import model.*;
import utilities.DBConnection;
import utilities.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** This is the data access utility for the Customer class.
 *
 */
public class CustomerDao {

    /** selectAllCustomers method - performs a select SQL statement that sets
     * the customers ObservableList with data from the database.
     * @throws SQLException
     */
    public static void selectAllCustomers() throws SQLException {
        String selectStatement = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, " +
                "Phone, d.Division_ID, d.Division, countries.Country, countries.Country_ID, d.COUNTRY_ID FROM customers c " +
                "JOIN first_level_divisions d ON c.Division_ID = d.Division_ID " +
                "JOIN countries ON d.COUNTRY_ID = countries.Country_ID";
        DBQuery.setPreparedStatement(DBConnection.conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();

        while (rs.next()) {
            String customerID = rs.getString("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerZip = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            String division = rs.getString("Division");
            String customerCountry = rs.getString("Country");
            String divisionID = rs.getString("Division_ID");
            String countryID = rs.getString("Country_ID");
            String COUNTRYID = rs.getString("COUNTRY_ID");

            Customer customer = new Customer(Integer.parseInt(customerID), customerName,
                    customerAddress, customerZip, customerPhone, new Division(Integer.parseInt(divisionID),
                    division, Integer.parseInt(COUNTRYID)), new Country(Integer.parseInt(countryID), customerCountry));

            DatabaseLists.addCustomer(customer);
        }

    }

    /** insertCustomer method - performs an SQL insert statement that adds a Customer object to
     * the database. Contains all of the fields from the Customer constructor.
     * @param customerName
     * @param customerAddress
     * @param customerZip
     * @param customerPhone
     * @param division
     * @throws SQLException
     */
    public static void insertCustomer(String customerName, String customerAddress,
                                      String customerZip, String customerPhone, Division division) throws SQLException {

        String insertStatement = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                "VALUES (?,?,?,?,?)";

        DBQuery.setPreparedStatement(DBConnection.conn, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        //key-value mapping
        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, customerZip);
        ps.setString(4, customerPhone);
        ps.setInt(5, division.getDivisionID());                                        //this needs to be fixed

        ps.execute();

    }

    /** selectAllCustomerAppointments method - performs an SQL select statement that sets
     * the customerAppointments ObservableList with data from the database.
     * @param customer
     * @throws SQLException
     */
    public static void selectAllCustomerAppointments(Customer customer) throws SQLException {
        customer.customerAppointments.clear();
        String custApptsStatement = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                "t.Contact_ID, t.Contact_Name, t.Email, c.Customer_ID, c.Customer_Name, Address, Postal_Code, Phone, " +
                "u.User_ID, u.User_Name, u.Password " +
                "FROM appointments a JOIN customers c " +
                "ON a.Customer_ID = c.Customer_ID JOIN contacts t " +
                "ON a.Contact_ID = t.Contact_ID " +
                "JOIN users u ON u.User_ID = a.User_ID";
        DBQuery.setPreparedStatement(DBConnection.conn, custApptsStatement);
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
            String apptContact = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");

            String userID = rs.getString("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");

            if (customer.getCustomerID() == Integer.parseInt(customerID)) {

                Appointment appointment = new Appointment(Integer.parseInt(apptID), apptTitle,
                        apptDescription, apptLocation, new MeetingType(apptType),
                        LocalDateTime.parse(apptStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        LocalDateTime.parse(apptEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        new Customer(Integer.parseInt(customerID), customerName, customerAddress, customerPostCode, customerPhone),
                        new Contact(Integer.parseInt(contactID), apptContact, contactEmail), new User(Integer.parseInt(userID), userName, userPassword));

                customer.addCustomerAppointment(appointment);
            }

        }
    }

    /** updateCustomer method - performs an SQL update statement that updates Customer
     * records on the database. Contains all of the fields from the Customer contructor.
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerZip
     * @param customerPhone
     * @param division
     * @throws SQLException
     */
    public static void updateCustomer (int customerID, String customerName, String customerAddress,
                                       String customerZip, String customerPhone, Division division) throws SQLException {
        String updateSQL = "UPDATE customers SET Customer_Name = '" + customerName + "', " +
                "Address = '" + customerAddress + "', " +
                "Postal_Code = '" + customerZip + "', " +
                "Phone = '" + customerPhone + "', " +
                "Division_ID = '" + division.getDivisionID() +
                "' WHERE Customer_ID = " + customerID;

        DBQuery.setPreparedStatement(DBConnection.conn, updateSQL);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.execute();

    }

    /** removeCustomerFromDB method - performs an SQL delete statement to remove a Customer
     * from the database.
     * @param customer
     * @throws SQLException
     */
    public static void removeCustomerFromDB (Customer customer) throws SQLException {
        String removeCustomerSQL = "DELETE FROM customers WHERE Customer_ID = " + customer.getCustomerID();
        DBQuery.setPreparedStatement(DBConnection.conn, removeCustomerSQL);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
    }

    /** removeCustomerAppointment method - performs an SQL delete statement that removes
     * a customer's appointment (used for the deleting of a customer record).
     * @param appointment
     * @throws SQLException
     */
    public static void removeCustomerAppointment (Appointment appointment) throws SQLException {
        String removeAppointmentSQL = "DELETE FROM appointments WHERE Appointment_ID = " + appointment.getApptID();
        DBQuery.setPreparedStatement(DBConnection.conn, removeAppointmentSQL);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
    }
}
