package Reports;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** The controller for the report1.fxml screen.
 *
 */
public class Report1Controller implements Initializable {

    // Declare fields**************************************************************************

    @FXML
    private Button report1BackBtn;

    @FXML
    private TableView<NumApptsByType> apptsByTypeTbl;

    @FXML
    private TableColumn<Appointment, MeetingType> apptsByTypeTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> apptsByTypeNumCol;

    @FXML
    private TableView<NumApptsByMonth> apptsByMonthTbl;

    @FXML
    private TableColumn<Month, String> apptsByMonthMonthCol;

    @FXML
    private TableColumn<Appointment, Integer> apptsbyMonthNumCol;

    Stage stage;
    int consultation, sales, marketing, planning, debriefing, notype;
    int jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec;

    // Declare methods**************************************************************************

    /**
     * onActionBack method - closes the screen and returns focus to the main screen.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * The overidden initialize method - increments counters for number of appointments per
     * type as well as number of appointments per month. sets both tables with that information.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // clear the ObservableLists
        DatabaseLists.numApptsByType.clear();
        DatabaseLists.numApptsByMonth.clear();

        // iterate through the appointments ObservableList - get number of appointments per type
        for (Appointment appointment : DatabaseLists.appointments) {
            if (appointment.getApptType().getTypeName().equals("Consultation"))
                consultation += 1;
            else if (appointment.getApptType().getTypeName().equals("Sales Meeting"))
                sales += 1;
            else if (appointment.getApptType().getTypeName().equals("Marketing/Promotion"))
                marketing += 1;
            else if (appointment.getApptType().getTypeName().equals("Planning Session"))
                planning += 1;
            else if (appointment.getApptType().getTypeName().equals("De-Briefing"))
                debriefing +=1;
            else notype +=1;
        }

        // create NumApptsByType objects and insert into the numApptsByType datalist
        NumApptsByType conType = new NumApptsByType("Consultation", consultation);
        NumApptsByType saleType = new NumApptsByType("Sales Meeting", sales);
        NumApptsByType markType = new NumApptsByType("Marketing/Promotion", marketing);
        NumApptsByType planType = new NumApptsByType("Planning Session", planning);
        NumApptsByType deType = new NumApptsByType("De-Briefing", debriefing);
        NumApptsByType none = new NumApptsByType("No Type Chosen", notype);

        DatabaseLists.numApptsByType.add(conType);
        DatabaseLists.numApptsByType.add(saleType);
        DatabaseLists.numApptsByType.add(markType);
        DatabaseLists.numApptsByType.add(planType);
        DatabaseLists.numApptsByType.add(deType);
        DatabaseLists.numApptsByType.add(none);

        // set the table
        apptsByTypeTbl.setItems(DatabaseLists.numApptsByType);
        apptsByTypeTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptsByTypeNumCol.setCellValueFactory(new PropertyValueFactory<>("num"));

        //************************************************************************************************

        // iterate through appointments ObservableList and get the total number of appointments per month
        for (Appointment appointment : DatabaseLists.appointments) {
            if (appointment.getApptStart().toLocalDate().getMonth() == java.time.Month.JANUARY)
                jan += 1;
            if (appointment.getApptStart().toLocalDate().getMonth() == java.time.Month.FEBRUARY)
                feb += 1;
            if (appointment.getApptStart().toLocalDate().getMonth() == java.time.Month.MARCH)
                mar += 1;
            if (appointment.getApptStart().toLocalDate().getMonth() == java.time.Month.APRIL)
                apr += 1;
            if (appointment.getApptStart().toLocalDate().getMonth() == java.time.Month.MAY)
                may += 1;
            if (appointment.getApptStart().toLocalDate().getMonth() == java.time.Month.JUNE)
                jun += 1;
            if (appointment.getApptStart().toLocalDate().getMonth() == java.time.Month.JULY)
                jul += 1;
            if (appointment.getApptStart().toLocalDate().getMonth() == java.time.Month.AUGUST)
                aug += 1;
            if (appointment.getApptStart().toLocalDate().getMonth() == java.time.Month.SEPTEMBER)
                sep += 1;
            if (appointment.getApptStart().toLocalDate().getMonth() == java.time.Month.OCTOBER)
                oct += 1;
            if (appointment.getApptStart().toLocalDate().getMonth() == java.time.Month.NOVEMBER)
                nov += 1;
            if (appointment.getApptStart().toLocalDate().getMonth() == java.time.Month.DECEMBER)
                dec += 1;
        }
        // create NumApptsByMonth objects and insert into the numApptsByType datalist
        NumApptsByMonth january = new NumApptsByMonth(Month.JANUARY, jan);
        NumApptsByMonth february = new NumApptsByMonth(Month.FEBRUARY, feb);
        NumApptsByMonth march = new NumApptsByMonth(Month.MARCH, mar);
        NumApptsByMonth april = new NumApptsByMonth(Month.APRIL, apr);
        NumApptsByMonth ma = new NumApptsByMonth(Month.MAY, may);
        NumApptsByMonth june = new NumApptsByMonth(Month.JUNE, jun);
        NumApptsByMonth july = new NumApptsByMonth(Month.JULY, jul);
        NumApptsByMonth august = new NumApptsByMonth(Month.AUGUST, aug);
        NumApptsByMonth september = new NumApptsByMonth(Month.SEPTEMBER, sep);
        NumApptsByMonth october = new NumApptsByMonth(Month.OCTOBER, oct);
        NumApptsByMonth november = new NumApptsByMonth(Month.NOVEMBER, nov);
        NumApptsByMonth december = new NumApptsByMonth(Month.DECEMBER, dec);

        DatabaseLists.numApptsByMonth.add(january);
        DatabaseLists.numApptsByMonth.add(february);
        DatabaseLists.numApptsByMonth.add(march);
        DatabaseLists.numApptsByMonth.add(april);
        DatabaseLists.numApptsByMonth.add(ma);
        DatabaseLists.numApptsByMonth.add(june);
        DatabaseLists.numApptsByMonth.add(july);
        DatabaseLists.numApptsByMonth.add(august);
        DatabaseLists.numApptsByMonth.add(september);
        DatabaseLists.numApptsByMonth.add(october);
        DatabaseLists.numApptsByMonth.add(november);
        DatabaseLists.numApptsByMonth.add(december);

        //set the table
        apptsByMonthTbl.setItems(DatabaseLists.numApptsByMonth);
        apptsByMonthMonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        apptsbyMonthNumCol.setCellValueFactory(new PropertyValueFactory<>("number"));
    }
}
