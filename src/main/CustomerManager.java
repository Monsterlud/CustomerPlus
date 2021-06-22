package main;

import DAO.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DatabaseLists;
import model.MeetingType;
import utilities.DBConnection;
import javafx.fxml.*;
import java.io.IOException;
import java.sql.*;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Supplier;

/** This is the main class and method for launching the application.
 *
 */
public class CustomerManager extends Application {

    // Declare fields***********************************************************************
    public static ZoneId localZone;

    // The Start Method*******************************************************************

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Locale.setDefault(Locale.forLanguageTag("fr"));

        ResourceBundle rb = ResourceBundle.getBundle("utilities/Lang", Locale.getDefault());
        Parent main = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            loader.setResources(rb);
            main = loader.load();
            Scene scene = new Scene(main);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    /** the main method. starts the database connection, loads the ObservableLists,
     * sets MeetingTypes and reports.
     *
     * @param args
     * @throws SQLException
     * @throws IOException
     */
    public static void main(String[] args) throws SQLException, IOException {
        DBConnection.startConnection();
        System.out.println("Connection made.");

        localZone = ZoneId.systemDefault();

        CountryDao.selectAllCountries();
        DivisionDao.selectAllDivisions();
        ContactDao.selectAllContacts();
        AppointmentDao.selectAllAppointments();

        MeetingType consultation = new MeetingType("Consultation");
        MeetingType salesMeeting = new MeetingType("Sales Meeting");
        MeetingType marketingPromotion = new MeetingType("Marketing/Promotion");
        MeetingType planningSession = new MeetingType("Planning Session");
        MeetingType debriefing = new MeetingType("De-Briefing");

        DatabaseLists.meetingTypes.add(consultation);
        DatabaseLists.meetingTypes.add(salesMeeting);
        DatabaseLists.meetingTypes.add(marketingPromotion);
        DatabaseLists.meetingTypes.add(planningSession);
        DatabaseLists.meetingTypes.add(debriefing);

        DatabaseLists.reports.add("appts by type & month");
        DatabaseLists.reports.add("contact schedules");
        DatabaseLists.reports.add("customers by country");

        launch(args);
    }
}
