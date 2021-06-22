package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/** the static DatabaseLists class - holds all static local observablelists that
 * mirror the MySql databases as well as filtered lists for searching and lists
 * used in reports.
 */
public class DatabaseLists {

    //Declare fields and lists**************************************************************

    public static ObservableList<User> users = FXCollections.observableArrayList();
    public static ObservableList<Contact> contacts = FXCollections.observableArrayList();
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    public static ObservableList<Division> divisions = FXCollections.observableArrayList();
    public static ObservableList<Country> countries = FXCollections.observableArrayList();
    public static ObservableList<MeetingType> meetingTypes = FXCollections.observableArrayList();
    public static ObservableList<Division> filteredDivisions  = FXCollections.observableArrayList();

    public static ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
    public static ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();
    public static ObservableList<NumApptsByType> numApptsByType = FXCollections.observableArrayList();
    public static ObservableList<NumApptsByMonth> numApptsByMonth = FXCollections.observableArrayList();

    public static ObservableList<Month> months = FXCollections.observableArrayList();
    public static ObservableList<String> reports = FXCollections.observableArrayList();

    public static String userName;
    public static int userIDNumber;


    // Declare methods***************************************************************************

    /** addUser method - adds User objects to the users ObservableList
     *
     * @param user  the user object
     */
    public static void addUser(User user) {users.add(user);}

    /** addAppt method - adds Appointment objects to the appointments ObservableList
     *
     * @param appointment  the appointment object
     */
    public static void addAppt(Appointment appointment) {appointments.add(appointment);}

    /** addCustomer method - adds Customer objects to the customers ObservableList
     *
     * @param customer  the customer object
     */
    public static void addCustomer(Customer customer) {customers.add(customer);}

    /** addContact method - adds Contact objects to the contacts ObservableList
     *
     * @param contact the contact object
     */
    public static void addContact(Contact contact) {
        contacts.add(contact);
    }

    /** addDivision method - adds Division objects to the divisions ObservableList
     *
     * @param division the division object
     */
    public static void addDivision(Division division) {divisions.add(division);}

    /** addCountry method - adds Country objects to the countries ObservableList
     *
     * @param country the country object
     */
    public static void addCountry(Country country) {countries.add(country);}

    /** addType method - adds MeetingType objects to the meetingTypes ObservableList
     *
     * @param type  the MeetingType object
     */
    public static void addType(MeetingType type) { meetingTypes.add(type);}

    /** getCountries method
     *
     * @return the countries ObservableList
     */
    public static ObservableList<Country> getCountries() {return countries;}

    /** getDivisions method
     *
     * @return the divisions ObservableList
     */
    public static ObservableList<Division> getDivisions() {return divisions;}

    /** getCustomers method
     *
     * @return the customers ObservableList
     */
    public static ObservableList<Customer> getCustomers() {return customers;}

    /** getAppointments method
     *
     * @return the appointments ObservableList
     */
    public static ObservableList<Appointment> getAppointments() { return appointments;}

    /** getUsers method
     *
     * @return the users ObservableList
     */
    public static ObservableList<User> getUsers() {return users;}

    /** getTypes method
     *
     * @return the meetingTypes ObservableList
     */
    public static ObservableList<MeetingType> getTypes() { return meetingTypes;}

    /** getContacts method
     *
     * @return  the contacts ObservableList
     */
    public static ObservableList<Contact> getContacts() { return contacts; }

    /** getmeetingTypes method
     *
     * @return the meetingTypes ObservableList
     */
    public static ObservableList<MeetingType> getMeetingTypes() {
        return meetingTypes;
    }

    /** setDivisions method - sets the divisions ObservableList
     *
     * @param divisions state/province objects
     */
    public static void setDivisions(ObservableList<Division> divisions) {
        DatabaseLists.divisions = divisions;
    }

    /** setCountries method - sets the countries ObservableList
     *
     * @param countries  country objects
     */
    public static void setCountries(ObservableList<Country> countries) {
        DatabaseLists.countries = countries;
    }

    /** setMeetingTypes method - sets the meetingTypes ObservableList
     *
     * @param meetingTypes
     */
    public static void setMeetingTypes(ObservableList<MeetingType> meetingTypes) {
        DatabaseLists.meetingTypes = meetingTypes;
    }

    /** getFilteredDivisions method
     *
     * @return a filtered set of divisions for search results
     */
    public static ObservableList<Division> getFilteredDivisions() {
        return filteredDivisions;
    }

    /** getFilteredAppointments method
     *
     * @return a filtered set of appointments for search results
     */
    public static ObservableList<Appointment> getFilteredAppointments() {
        return filteredAppointments;
    }

    /** setFilteredAppointments method - sets the filtered list
     *
     * @param filteredAppointments
     */
    public static void setFilteredAppointments(ObservableList<Appointment> filteredAppointments) {
        DatabaseLists.filteredAppointments = filteredAppointments;
    }

    /** getFilteredCustomers method
     *
     * @return the filteredCustomers ObservableList
     */
    public static ObservableList<Customer> getFilteredCustomers() {
        return filteredCustomers;
    }

    /** setFilteredCustomers method
     *
     * @param filteredCustomers  the filteredCustomers ObservableList
     */
    public static void setFilteredCustomers(ObservableList<Customer> filteredCustomers) {
        DatabaseLists.filteredCustomers = filteredCustomers;
    }

    /** getMonths method
     *
     * @return  the months ObservableList
     */
    public static ObservableList<Month> getMonths() {
        return months;
    }

    /** setMonths - sets the months ObservableList
     *
     * @param months the months ObservableList
     */
    public static void setMonths(ObservableList<Month> months) {
        DatabaseLists.months = months;
    }

    /** getReports method
     *
     * @return  the reports ObservableList
     */
    public static ObservableList<String> getReports() {
        return reports;
    }

    /** setReports - sets the reports ObservableList
     *
     * @param reports  the list of reports
     */
    public static void setReports(ObservableList<String> reports) {
        DatabaseLists.reports = reports;
    }

    /** isInteger method - used in searches to determine if the ID number is
     * the value that is being searched
     *
     * @param input the string that is being searched
     * @return
     */
    public static boolean isInteger (String input) {
        if (input == null) {
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c <= '/' || c >= ':') {
                return false;
            }
        }
        return true;
    }
}
