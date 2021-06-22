package controller;

import DAO.AppointmentDao;
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
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller for the modifyAppointment.fxml screen.
 *
 */
public class ModifyApptController implements Initializable {

    // Declare fields***************************************************************
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
    private ComboBox<Contact> apptContactCbo;

    @FXML
    private Button apptUpdateApptBtn;

    @FXML
    private Button apptCancelBtn;

    @FXML
    private Button deleteAppointmentBtn;

    Stage stage;
    boolean value;

    /** the overriden initialize method - sets the combo boxes with the appropriate lists.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptTypeCbo.setItems(DatabaseLists.getTypes());
        apptCustomerCbo.setItems(DatabaseLists.getCustomers());
        apptContactCbo.setItems(DatabaseLists.getContacts());
        apptUserCbo.setItems(DatabaseLists.getUsers());

        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 59);

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

    /** onActionCancel method - sends the user back to the main screen without
     * modifying the appointment.
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

    /** onActionDelete method - deletes the appointment from the database.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionDelete(ActionEvent event) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning Dialog");
        alert.setContentText("Appointment ID " + apptIDTxt.getText() + " / " + apptTypeCbo.getSelectionModel().getSelectedItem() +
                " will be deleted. Are you sure you want to delete this appointment?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            AppointmentDao.deleteAppointment((Integer.parseInt(apptIDTxt.getText())));

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
    }

    /** onActionUpdateAppt method - updates the appointment record on the database.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionUpdateAppt(ActionEvent event) throws IOException, SQLException {
        value = true;
        LocalTime est800 = LocalTime.of(7, 59);
        LocalTime est1030 = LocalTime.of(22, 0);

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
                if ((TimeConversion.ldtToEST(LocalDateTime.of((apptStartDateDpk.getValue()), (apptStartTimeCbo.getSelectionModel().getSelectedItem())))).toLocalTime().isAfter(est800)
                        && ((TimeConversion.ldtToEST(LocalDateTime.of((apptStartDateDpk.getValue()), (apptStartTimeCbo.getSelectionModel().getSelectedItem()))).toLocalTime().isBefore(est1030)))) {

                    AppointmentDao.updateAppointment(Integer.parseInt(apptIDTxt.getText()), apptTitleTxt.getText(),
                            apptDescriptionTxt.getText(), apptLocationTxt.getText(), apptTypeCbo.getSelectionModel().getSelectedItem(),
                            TimeConversion.ldtToUTC(LocalDateTime.of((apptStartDateDpk.getValue()), (apptStartTimeCbo.getSelectionModel().getSelectedItem()))),
                            TimeConversion.ldtToUTC(LocalDateTime.of((apptEndDateDpk.getValue()), (apptEndTimeCbo.getSelectionModel().getSelectedItem()))),
                            (apptCustomerCbo.getSelectionModel().getSelectedItem()), (apptContactCbo.getSelectionModel().getSelectedItem()),
                            (apptUserCbo.getSelectionModel().getSelectedItem()));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                } else {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Warning Dialog");
                    a.setContentText("Appointment times must be between 8am-10pm EST");
                    a.showAndWait();
                }
            }
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please make sure that you have values chosen for all drop-down menus");
            alert.showAndWait();
        }
    }

    /** apptToModify method - sets the fields and combo boxes when modifying an
     * existing appointment.
     * @param selectedItem
     */
    public void apptToModify(Appointment selectedItem) {
        apptIDTxt.setText(String.valueOf(selectedItem.getApptID()));
        apptTitleTxt.setText(selectedItem.getApptTitle());
        apptDescriptionTxt.setText(selectedItem.getApptDescription());
        apptLocationTxt.setText(selectedItem.getApptLocation());
        apptTypeCbo.setValue(selectedItem.getApptType());
        apptStartTimeCbo.setValue(TimeConversion.utcToLDT(selectedItem.getApptStart()).toLocalTime());
        apptStartDateDpk.setValue(TimeConversion.utcToLDT(selectedItem.getApptStart()).toLocalDate());
        apptEndTimeCbo.setValue(TimeConversion.utcToLDT(selectedItem.getApptEnd()).toLocalTime());
        apptEndDateDpk.setValue(TimeConversion.utcToLDT(selectedItem.getApptEnd()).toLocalDate());
        apptCustomerCbo.setValue(selectedItem.getApptCustomer());
        apptUserCbo.setValue(selectedItem.getApptUser());
        apptContactCbo.setValue(selectedItem.getApptContact());
        apptTimeMsgLbl.setText("Local time zone: " + CustomerManager.localZone);
    }
}
