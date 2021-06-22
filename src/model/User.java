package model;

/** This is the User class - blueprint for all User objects.
 *
 */
public class User {

    // Declare fields*********************************************************
    int userID;
    String userName;
    String userPassword;

    // Constructor***********************************************************

    /** This is the full constructor
     *
     * @param userID  the user's ID number
     * @param userName  the user's name
     * @param userPassword  the user's password
     */
    public User(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    // Declare methods******************************************************

    /** getUserID method
     *
     * @return the user's ID number
     */
    public int getUserID() {
        return userID;
    }

    /** getUserName method
     *
     * @return the user's name
     */
    public String getUserName() {
        return userName;
    }

    /** setUserName method
     *
     * @param userName the user's name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** getUserPassword method
     *
     * @return  the user's password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /** The overridden toString method
     *
     * @return the user's name
     */
    @Override
    public String toString() {
        return userName;
    }
}
