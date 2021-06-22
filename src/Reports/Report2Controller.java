package Reports;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.DatabaseLists;
import model.MeetingType;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/** This is the controller for the report1.fxml screen.
 *
 */
public class Report2Controller implements Initializable {

    // Declare fields*********************************************************************
    @FXML
    private Button report2BackBtn;

    @FXML
    private TableView<Appointment> report2TableTbl;

    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;

    @FXML
    private TableColumn<Appointment, String> apptTitleCol;

    @FXML
    private TableColumn<Appointment, MeetingType> apptTypeCol;

    @FXML
    private TableColumn<Appointment, String> apptDescriptionCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apptStartCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apptEndCol;

    @FXML
    private TableColumn<Appointment, Integer> apptCustIDCol;

    @FXML
    private ComboBox<Contact> contactsCbo;

    @FXML
    private Label contactDisplayLbl;

    Stage stage;

    // Declare methods************************************************************************

    /** onActionBack method - closes the report and returns focus to the main screen.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /** onActionChooseContact method - chooses the contact on which to run the report.
     * The method finds the contact chosen, then pulls all appointments for that contact
     * to the table.
     * @param event
     */
    @FXML
    void onActionChooseContact(ActionEvent event) {
        DatabaseLists.filteredAppointments.clear();
        for (Contact contact : DatabaseLists.contacts) {
            if (contactsCbo.getSelectionModel().getSelectedItem().getContactID() == contact.getContactID()) {
                for (Appointment appointment : DatabaseLists.appointments) {
                    if (appointment.getApptContact().getContactID() == contactsCbo.getSelectionModel().getSelectedItem().getContactID()) {
                        DatabaseLists.filteredAppointments.add(appointment);
                    }
                }
            }
            contactDisplayLbl.setText(contactsCbo.getSelectionModel().getSelectedItem() + "'s upcoming appointments");
            report2TableTbl.setItems(DatabaseLists.filteredAppointments);

            apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
            apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
            apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
            apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
            apptStartCol.setCellValueFactory(new PropertyValueFactory<>("apptStartLDT"));
            apptEndCol.setCellValueFactory(new PropertyValueFactory<>("apptEndLDT"));
            apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }
    }

    /** The overridden initialize method. Sets the contacts combo box with the list of
     * available contacts.
     * @param url
     * @param resourceBundle
     */
        @Override
        public void initialize (URL url, ResourceBundle resourceBundle) {
            contactsCbo.setItems(DatabaseLists.contacts);
        }
}


