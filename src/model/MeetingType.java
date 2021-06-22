package model;

/** This is the MeetingType class - blueprint for the MeetingType objects that
 *  are used by the Appointment class.
 */
public class MeetingType {

    // Declare fields******************************************************

    String typeName;

    // Constructor********************************************************

    /** This is the full constructor
     *
     * @param typeName  the name of the meeting type
     */
    public MeetingType(String typeName) {
        this.typeName = typeName;
    }

    // Declare methods***************************************************

    /** getTypeName method
     *
     * @return the name of the type
     */
    public String getTypeName() {
        return typeName;
    }

    /** setTypeName method
     *
     * @param typeName  the name of the type
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /** the overridden toString method
     *
     * @return the name of the type
     */
    @Override
    public String toString() {
        return typeName;
    }
}
