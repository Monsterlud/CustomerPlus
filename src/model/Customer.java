package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

/** the Customer class - blueprint for all Customer objects. this class contains
 * other objects - Division and Country.
 */
public class Customer {

    // Declare fields & lists*****************************************************
    int customerID;
    String customerName;
    String customerAddress;
    String customerPostCode;
    String customerPhone;
    String customerState;
    String customerCountry;
    Division division;
    Country country;



    // Constructor*************************************************************

    /** This is the full constructor
     *
     * @param customerID  the customer ID number
     * @param customerName  the customer's name
     * @param customerAddress  the customer's address
     * @param customerPostCode  the customer's zip code
     * @param customerPhone  the customer's phone number
     * @param division  the first-level division (state, province) associated with this customer
     * @param country  the country associated with this customer
     */

    public Customer(int customerID, String customerName, String customerAddress,
                    String customerPostCode, String customerPhone, Division division, Country country) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostCode = customerPostCode;
        this.customerPhone = customerPhone;
        this.division = division;
        this.country = country;
        this.customerState = division.getDivision();
        this.customerCountry = country.getCountry();
    }

    /** overloaded and abbreviated constructor
     *
     * @param customerID the customer ID number
     * @param customerName  the customer's name
     * @param customerAddress  the customer's address
     * @param customerPostCode  the customer's zip code
     * @param customerPhone  the customer's phone number
     */

    public Customer(int customerID, String customerName, String customerAddress, String customerPostCode, String customerPhone){
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostCode = customerPostCode;
        this.customerPhone = customerPhone;
    }

    public ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

    // Declare methods********************************************************************

    /** getDivision method
     *
     * @return  the first-level division object
     */
    public Division getDivision() {
        return division;
    }

    /** getCountry method
     *
     * @return  the country object
     */
    public Country getCountry() {
        return country;
    }

    /** getCustomerID method
     *
     * @return the customer id number
     */
    public int getCustomerID() { return customerID; }

    /** getCustomerName method
     *
     * @return  the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /** getCustomerAddress method
     *
     * @return  the customer address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /** getCustomerPostCode method
     *
     * @return the customer zip code
     */
    public String getCustomerPostCode() { return customerPostCode; }

    /** getCustomerPhone method
     *
     * @return  the customer phone number
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /** getCustomerAppointments method
     *
     * @return the observablelist of Appointments associated with this.customer
     * @throws SQLException
     */
    public ObservableList<Appointment> getCustomerAppointments() throws SQLException {
        return customerAppointments;
    }

    /** addCustomerAppointment method
     *
     * @param appointment  an Appointment object
     */
    public void addCustomerAppointment(Appointment appointment) {
        this.customerAppointments.add(appointment);
    }

    /** deleteCustomerAppointment method
     *
     * @param appointment  an Appointment object
     */
    public void deleteCustomerAppointment(Appointment appointment) {
        customerAppointments.remove(appointment);
    }

    /** getCustomerState method
     *
     * @return the customer's state
     */
    public String getCustomerState() {return customerState;}

    /** getCustomerCountry method
     *
     * @return  the customer's country
     */
    public String getCustomerCountry() {return customerCountry;}

    /** the overridden toString method
     *
     * @return  the customer's name
     */
    @Override
    public String toString() {
        return customerName;
    }



}
