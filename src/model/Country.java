package model;

/** The Country class - this class is used for instantiating Country objects
 * within the Appointment class.
 */
public class Country {

    // Declare fields*********************************************************
    private int countryID;
    private String country;

    // Constructor***********************************************************

    /** the full constructor
     *
     * @param countryID  the country ID number
     * @param country  the country's name
     */
    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    // Declare methods*******************************************************

    /** getCountry method
     *
     * @return the country's name
     */
    public String getCountry() {
        return country;
    }

    /** getCountryID method
     *
     * @return  the country's ID number
     */
    public int getCountryID() {
        return countryID;
    }

    /** the overridden toString method
     *
     * @return the country's name
     */
    @Override
    public String toString() {return country;}
}
