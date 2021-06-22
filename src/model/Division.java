package model;

/** This is the Division class - the blueprint for first-level division objects.
 * divisions include US states, Canadian provinces, and Great British provinces
 */
public class Division {

    // Declare fields***********************************************************
    private int divisionID;
    private String division;
    private int countryID;

    // Constructor*************************************************************

    /** This is the full constructor
     *
     * @param divisionID  the division ID number
     * @param division  the division name
     * @param countryID  the country ID number that this division belongs to
     */
    public Division(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    // Declare methods********************************************************

    /** getCountryID method
     *
     * @return the country ID number
     */
    public int getCountryID() {
        return countryID;
    }

    /** setCountryID method
     *
     * @param countryID  the country ID number
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /** getDivisionID method
     *
     * @return  the division ID number
     */
    public int getDivisionID() {
        return divisionID;
    }

    /** setDivisionID method
     *
     * @param divisionID  the division ID number
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /** getDivision method
     *
     * @return  the division name
     */
    public String getDivision() {
        return division;
    }

    /** setDivision method
     *
     * @param division  the division name
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /** the overridden toString method
     *
     * @return  the division name
     */
    @Override
    public String toString() {return division;}
}
