package model;

import utilities.TimeConversion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** The Appointment class - blueprint for all Appointment objects. This class contains other objects -
 * Customer, Contact, and User. apptStartLDT and apptEndLDT are String variables that contain a
 * formatted version of the LocalDateTime objects apptStart and apptEnd.*/
public class Appointment {

    // Declare fields*******************************************************************
    int apptID;
    String apptTitle;
    String apptDescription;
    String apptLocation;
    MeetingType apptType;
    LocalDateTime apptStart;
    LocalDateTime apptEnd;
    Customer apptCustomer;
    Contact apptContact;
    int customerID;
    String customerName;
    String contactName;
    int userID;
    User apptUser;
    String apptStartLDT;
    String apptEndLDT;

    // Constructor*********************************************************************

    /** This is the full constructor
     *
     * @param apptID  the appointment ID number
     * @param apptTitle  the appointment title
     * @param apptDescription  the appointment description
     * @param apptLocation  the appointment location
     * @param apptType  the appointment type
     * @param apptStart  the appointment starting date & time
     * @param apptEnd  the appointment ending date & time
     * @param customer  the customer for this appointment
     * @param contact  the contact for this appointment
     * @param user  the user connected to this appointment
     */

    public Appointment(int apptID, String apptTitle, String apptDescription,
                       String apptLocation, MeetingType apptType,
                       LocalDateTime apptStart, LocalDateTime apptEnd,
                       Customer customer, Contact contact, User user){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MM/dd hh:mm a");
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.apptCustomer = customer;
        this.apptContact = contact;
        this.apptUser = user;
        this.customerID = customer.customerID;
        this.customerName = customer.customerName;
        this.contactName = contact.contactName;
        this.userID = user.userID;
        this.apptStartLDT = dtf.format(TimeConversion.utcToLDT(apptStart));
        this.apptEndLDT = dtf.format(TimeConversion.utcToLDT(apptEnd));
    }

    // Declare methods****************************************************************

    /** getApptID method
     *
     * @return  the appointment ID number
     */
    public int getApptID() {
        return apptID;
    }

    /** getApptTitle method
     *
     * @return  the appointment title
     */
    public String getApptTitle() {
        return apptTitle;
    }

    /** getApptDescription method
     *
     * @return  the appointment description
     */
    public String getApptDescription() {
        return apptDescription;
    }

    /** getApptLocation method
     *
     * @return  the appointment location
     */
    public String getApptLocation() {
        return apptLocation;
    }

    /** getApptType
     *
     * @return  MeetingType - the appointment type
     */
    public MeetingType getApptType() {
        return apptType;
    }

    /** getApptStart method
     *
     * @return  LocalDateTime - the starting date & time of the appointment
     */
    public LocalDateTime getApptStart() {
        return apptStart;
    }

    /** getApptEnd method
     *
     * @return  LocalDateTime - the ending date & time of the appointment
     */
    public LocalDateTime getApptEnd() {
        return apptEnd;
    }

    /** getApptCustomer method
     *
     * @return  Customer - the customer associated with this appointment
     */
    public Customer getApptCustomer() {
        return apptCustomer;
    }

    /** getApptContact method
     *
     * @return  Contact - the contact associated with this appointment
     */
    public Contact getApptContact() {
        return apptContact;
    }

    /** setApptContact method
     *
     * @param apptContact
     */
    public void setApptContact(Contact apptContact) {
        this.apptContact = apptContact;
    }

    /** getCustomerID method
     *
     * @return  the customer ID number
     */
    public int getCustomerID() {return customerID;}

    /** getApptUser method
     *
     * @return  User - the user associated with this appointment
     */
    public User getApptUser() {return apptUser; }

    /** getUserID method
     *
     * @return  the user ID number
     */
    public int getUserID() {
        return userID;
    }

    /** the overridden toString method
     *
     * @return appointment title & appointment customer
     */
    @Override
    public String toString() {
        return apptTitle + " | " + apptCustomer;
    }

    /** getApptStartLDT method
     *
     * @return the formatted string of the starting date & time
     */
    public String getApptStartLDT() {
        return apptStartLDT;
    }

    /** getApptEndLDT method
     *
     * @return the formatted string of the ending date & time
     */
    public String getApptEndLDT() {
        return apptEndLDT;
    }

    /** getCustomerName method
     *
     * @return the customer's name associated with this appointment
     */
    public String getCustomerName() { return customerName;}

    /** getContactName method
     *
     * @return the contact's name associated with this appointment
     */
    public String getContactName() { return contactName;}
}
