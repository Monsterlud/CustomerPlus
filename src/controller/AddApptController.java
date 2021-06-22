package controller;

import DAO.AppointmentDao;
import DAO.CustomerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.CustomerManager;
import model.*;
import utilities.TimeConversion;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Supplier;

/** This is the controller for the addAppointments.fxml screen.
 *
 */
public class AddApptController implements Initializable {

    //Declare fields****************************************************************

    @FXML
    private Label apptTimeMsgLbl;

    @FXML
    private TextField apptIDTxt;

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private TextArea apptDescriptionTxt;

    @FXML
    private TextField apptLocationTxt;

    @FXML
    private ComboBox<MeetingType> apptTypeCbo;

    @FXML
    private ComboBox<LocalTime> apptStartTimeCbo;

    @FXML
    private DatePicker apptStartDateDpk;

    @FXML
    private ComboBox<LocalTime> apptEndTimeCbo;

    @FXML
    private DatePicker apptEndDateDpk;

    @FXML
    private ComboBox<Customer> apptCustomerCbo;

    @FXML
    private ComboBox<User> apptUserCbo;

    @FXML
    private TextField apptUserTxt;

    @FXML
    private ComboBox<Contact> apptContactCbo;

    @FXML
    private Button apptCancelBtn;

    @FXML
    private Button apptAddtBtn;

    Stage stage;
    boolean value = true;

    //Declare methods**************************************************************

    /** This lambda expression implements the Supplier interface to supply the local zone of the user.
     * localZone is defined in the CustomerManager class. The lambda is implemented in line 110 below.
     *
     */
    Supplier<ZoneId> lZone = () -> CustomerManager.localZone;

    /** the initialize method - sets the combo box lists.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptTypeCbo.setItems(DatabaseLists.getTypes());
        apptTypeCbo.getSelectionModel().select(1);
        apptCustomerCbo.setItems(DatabaseLists.getCustomers());
        apptCustomerCbo.setPromptText("Choose a customer");
        apptContactCbo.setItems(DatabaseLists.getContacts());
        apptContactCbo.setPromptText("Choose a contact");
        apptUserCbo.setItems(DatabaseLists.getUsers());
        apptUserCbo.setValue(LoginController.validUser);

        /** this is an implementation of the lambda expression written in line 85 above
         * the initialize method.
         */
        apptTimeMsgLbl.setText("Local time zone: " + lZone.get());

        for (User user : DatabaseLists.users) {
            if (user.getUserID() == DatabaseLists.userIDNumber) {
                apptUserCbo.setValue(user);
            }
        }

        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(22, 59);


        while (start.isBefore(end.minusMinutes(30))) {
            apptStartTimeCbo.getItems().add(start);
            apptEndTimeCbo.getItems().add(start.plusMinutes(25));
            start = start.plusMinutes(30);
        }

        apptStartTimeCbo.getItems().add(start);
        apptEndTimeCbo.getItems().add(start.plusMinutes(25));

        apptStartTimeCbo.getSelectionModel().selectFirst();
        apptEndTimeCbo.getSelectionModel().selectFirst();
    }

    /** onActionAddAppt method - contains logic to prevent scheduling overlapping
     * appointments for customers and validating that appointments are during
     * EST business hours (8am-10pm).
     *
     * @param event the action event that adds the appointmnet
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionAddAppt(ActionEvent event) throws IOException, SQLException {
        value = true;
        LocalTime est759 = LocalTime.of(7, 59);
        LocalTime est1000 = LocalTime.of(22, 0);

        try {
            for (Appointment appointment : DatabaseLists.getAppointments()) {
                if (appointment.getCustomerID() == apptCustomerCbo.getSelectionModel().getSelectedItem().getCustomerID()) {
                    LocalDateTime start = TimeConversion.ldtToUTC(LocalDateTime.of(apptStartDateDpk.getValue(), apptStartTimeCbo.getValue()));
                    LocalDateTime end = TimeConversion.ldtToUTC(LocalDateTime.of(apptEndDateDpk.getValue(), apptEndTimeCbo.getValue()));

                    if ((start.isAfter(appointment.getApptStart()) && start.isBefore(appointment.getApptEnd()))
                            || ((end.isAfter(appointment.getApptStart()) && end.isBefore(appointment.getApptEnd())))
                            || (start.isEqual(appointment.getApptStart()))
                            || ((start.isBefore(appointment.getApptStart()) && end.isAfter(appointment.getApptEnd())))
                            || ((end.isEqual(appointment.getApptEnd())))) {

                        value = false;
                        Alert a = new Alert(Alert.AlertType.WARNING);
                        a.setTitle("Appointment Scheduling Issue");
                        a.setContentText("This appointment conflicts with an existing appointment for this customer.");
                        a.showAndWait();
                    }
                }
            }

            if (LocalDateTime.of((apptStartDateDpk.getValue()), (apptStartTimeCbo.getSelectionModel().getSelectedItem())).toLocalTime().isAfter(
                    LocalDateTime.of((apptEndDateDpk.getValue()), (apptEndTimeCbo.getSelectionModel().getSelectedItem())).toLocalTime())) {
                value = false;
                Alert b = new Alert(Alert.AlertType.WARNING);
                b.setTitle("Scheduling Error");
                b.setContentText("You cannot have an ending time earlier than your starting time.");
                b.showAndWait();
            }

            if (value) {

                if (((TimeConversion.ldtToEST(LocalDateTime.of((apptStartDateDpk.getValue()), (apptStartTimeCbo.getSelectionModel().getSelectedItem())))).toLocalTime().isAfter(est759))
                        && ((TimeConversion.ldtToEST(LocalDateTime.of((apptStartDateDpk.getValue()), (apptStartTimeCbo.getSelectionModel().getSelectedItem()))).toLocalTime().isBefore(est1000)))) {

                    AppointmentDao.insertAppointment(apptTitleTxt.getText(),
                            apptDescriptionTxt.getText(), apptLocationTxt.getText(), apptTypeCbo.getSelectionModel().getSelectedItem(),
                            TimeConversion.ldtToUTC(LocalDateTime.of(apptStartDateDpk.getValue(), apptStartTimeCbo.getSelectionModel().getSelectedItem())),
                            TimeConversion.ldtToUTC(LocalDateTime.of(apptEndDateDpk.getValue(), apptEndTimeCbo.getSelectionModel().getSelectedItem())),
                            apptCustomerCbo.getSelectionModel().getSelectedItem(), apptUserCbo.getSelectionModel().getSelectedItem(),
                            apptContactCbo.getSelectionModel().getSelectedItem());

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                } else {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Scheduling Error");
                    a.setContentText("Appointment times must be between 8am-10pm EST");
                    a.showAndWait();
                }
            }
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please make sure you have values chosen for all drop-down menus.");
            alert.showAndWait();
        }
    }


    /** onActionCancel - sends the user back to the main screen without adding the appointment.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}


