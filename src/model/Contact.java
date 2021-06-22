package model;

/** the Contact class - this class is used for instantiating new Contact objects
 * within the Appointment class. It includes all three fields from the MySql
 * database.
 */
public class Contact {

    // Declare fields************************************************************
    int contactID;
    String contactName;
    String contactEmail;

    // Constructor**************************************************************

    /** This is the full constructor
     *
     * @param contactID  the contact ID number
     * @param contactName  the contact's name
     * @param contactEmail the contact's email
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    // Declare methods**********************************************************

    /** getContactID method
     *
     * @return the contact's ID number
     */
    public int getContactID() {
        return contactID;
    }

    /** the overridden toString method
     *
     * @return  the contact's name
     */
    @Override
    public String toString() {
        return contactName;
    }
}
