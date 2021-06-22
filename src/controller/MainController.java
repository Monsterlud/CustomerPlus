package controller;

import DAO.AppointmentDao;
import DAO.CustomerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.DatabaseLists;
import model.Division;
import utilities.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/** This is the controller for the main.fxml screen.
 *
 */
public class MainController implements Initializable {

    // Declare fields*********************************************

    @FXML
    private ToggleGroup appointmentView;

    @FXML
    private Label apptMsgLbl;

    @FXML
    private ComboBox<String> reportsCbo;

    @FXML
    private RadioButton weekRadioRdo;

    @FXML
    private RadioButton monthRadioRdo;

    @FXML
    private RadioButton allApptRdo;

    @FXML
    private Button addApptBtn;

    @FXML
    private Button modifyApptBtn;

    @FXML
    private TextField customerSearch;

    @FXML
    private Button addCustomerBtn;

    @FXML
    private Button modifyCustomerBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private TableView<Appointment> appointmentsTable;

    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;

    @FXML
    private TableColumn<Appointment, String> apptTitleCol;

    @FXML
    private TableColumn<Appointment, String> apptDescriptionCol;

    @FXML
    private TableColumn<Appointment, String> apptLocationCol;

    @FXML
    private TableColumn<Appointment, String> apptTypeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apptStartCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apptEndCol;

    @FXML
    private TableColumn<Appointment, Integer> apptCustIDCol;

    @FXML
    private TableColumn<Appointment, String> apptCustomerCol;

    @FXML
    private TableColumn<Appointment, String> apptContactCol;

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, Integer> customerCustIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerZipCol;

    @FXML
    private TableColumn<Customer, String> customerStateCol;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    Stage stage;


// Declare methods*************************************************

    /** this is a Predicate lambda expression that is used in line 334 of this controller.
     * it tests whether a string is comprised of only integers. it tests for a null
     * value first, then compares each character to the ASCII table to determine if
     * there are integer values (between '/' and ':').
     */
    Predicate<String> isAnInteger = a -> {
        if (a == null) {
            return false;
        }
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            if (c <= '/' || c >= ':') {
                return false;
            }
        }
        return true;
    };

    /** the overriden initialize method - sets up the toggle group for appointment
     * search options, loads the appointments and customers tables, and allows for
     * customers to be searched by id or name.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointmentView = new ToggleGroup();
        weekRadioRdo.setToggleGroup(appointmentView);
        monthRadioRdo.setToggleGroup(appointmentView);
        allApptRdo.setToggleGroup(appointmentView);

        reportsCbo.setItems(DatabaseLists.getReports());

        DatabaseLists.appointments.clear();
        DatabaseLists.customers.clear();

        try {
            AppointmentDao.selectAllAppointments();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            CustomerDao.selectAllCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        appointmentsTable.setItems(DatabaseLists.getAppointments());

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("apptStartLDT"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("apptEndLDT"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));

        customersTable.setItems(DatabaseLists.getCustomers());

        customerCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerZipCol.setCellValueFactory(new PropertyValueFactory<>("customerPostCode"));
        customerStateCol.setCellValueFactory(new PropertyValueFactory<>("customerState"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
    }


    @FXML
    void onActionReports(ActionEvent event) {

    }

    /** onActionWeek method - sets the table with appointments that are within
     * the next week.
     * @param event
     */
    @FXML
    void onActionWeek(ActionEvent event) {
        DatabaseLists.filteredAppointments.clear();
        for (Appointment appointment : DatabaseLists.appointments) {
            if (appointment.getApptStart().toLocalDate().isBefore(LocalDate.now().plusDays(7))) {
                DatabaseLists.filteredAppointments.add(appointment);
            }
        }
        appointmentsTable.setItems(DatabaseLists.getFilteredAppointments());

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("apptStartLDT"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("apptEndLDT"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
    }

    /** onActionMonth method - sets the table with appointments that are within
     * the next month.
     * @param event
     */
    @FXML
    void onActionMonth(ActionEvent event) {
        DatabaseLists.filteredAppointments.clear();
        for (Appointment appointment : DatabaseLists.appointments) {
            if (appointment.getApptStart().toLocalDate().isBefore(LocalDate.now().plusMonths(1))) {
                DatabaseLists.filteredAppointments.add(appointment);
            }
        }

        appointmentsTable.setItems(DatabaseLists.getFilteredAppointments());

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("apptStartLDT"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("apptEndLDT"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
    }

    /** onActionAll method - sets the table with all appointments.
     *
     * @param event
     */
    @FXML
    void onActionAll(ActionEvent event) {
        appointmentsTable.setItems(DatabaseLists.getAppointments());

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("apptStartLDT"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("apptEndLDT"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
    }

    /** onActionAddAppt method - sends the user to the addAppointment screen.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddAppt(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addAppointment.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /** onActionModifyAppt method - sends the user to the modify appointment screen for
     * the appointment that is selected in the table and pulls the information from that
     * appointment to pre-set the fields in the new screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyAppt(ActionEvent event) throws IOException {

        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyAppointment.fxml"));
            Scene scene = new Scene(loader.load());
            ModifyApptController mpController = loader.getController();
            mpController.apptToModify(appointmentsTable.getSelectionModel().getSelectedItem());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        catch (NullPointerException e) {
            apptMsgLbl.setText("You must select an appointment to modify.");
        }
    }

    /** onActionSearchCustomers method - the action handler for the search function
     * for the customer's table.
     * @param event
     */
    @FXML
    void onActionSearchCustomers(ActionEvent event) {
        DatabaseLists.filteredCustomers.clear();
        String input = customerSearch.getText();

        if (input.equals("")) customersTable.setItems(DatabaseLists.getCustomers());
        else if (isAnInteger.test(input)) {
            for (Customer customer : DatabaseLists.getCustomers()) {
                if (customer.getCustomerID() == Integer.parseInt(input)) {
                    DatabaseLists.filteredCustomers.add(customer);
                }
            }
            customersTable.setItems(DatabaseLists.filteredCustomers);
        }
        else {
            for (Customer customer : DatabaseLists.getCustomers()) {
                if (customer.getCustomerName().contains(input)) {
                    DatabaseLists.filteredCustomers.add(customer);
                }
            }
            customersTable.setItems(DatabaseLists.filteredCustomers);
        }
    }

    /** onActionAddCustomer method - sends the user to the addCustomer screen.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addCustomer.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /** onActionModifyCustomer method - sends the user to the modifyCustomer screen, pulling the
     * information from the selected customer in the table and pre-setting the fields in the new screen
     * with that information.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException, SQLException {

        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyCustomer.fxml"));
            Scene scene = new Scene(loader.load());
            ModifyCustomerController mpController = loader.getController();
            mpController.customerToModify(customersTable.getSelectionModel().getSelectedItem());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        catch (NullPointerException e) {
            apptMsgLbl.setText("You must choose a customer to modify.");
        }
    }

    /** onActionExit method - exits the application and closes the database connection.
     *
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {
        DBConnection.closeConnection();
        System.exit(0);
    }

    /** onActionSelectReport method - sets the function of choosing the report
     * that you want to see from the REPORTS combo box. Choosing a report sends
     * the user to the appropriate fxml screen and controller.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSelectReport(ActionEvent event) throws IOException {
        String path = "";

        if (reportsCbo.getSelectionModel().getSelectedIndex() == 0)
            path = "/view/report1.fxml";
        else if (reportsCbo.getSelectionModel().getSelectedIndex() == 1)
            path = "/view/report2.fxml";
        else
            path = "/view/report3.fxml";

        Stage stage = new Stage();

        Parent scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }
}
