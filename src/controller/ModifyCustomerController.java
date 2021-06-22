package controller;

import DAO.CustomerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller for the modifyCustomer.fxml screen.
 *
 */
public class ModifyCustomerController implements Initializable {

    // Declare fields****************************************************************
    @FXML
    private TextField customerIDTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextArea customerAddressTxt;

    @FXML
    private ComboBox<Division> customerStateCbo;

    @FXML
    private ComboBox<Country> customerCountryCbo;

    @FXML
    private TextField customerZipTxt;

    @FXML
    private TextField customerPhoneTxt;

    @FXML
    private Button customerAddApptBtn;

    @FXML
    private Button customerDeleteBtn;

    @FXML
    private Button customerCancelBtn;

    @FXML
    private Button customerUpdateBtn;

    @FXML
    private TableView<Appointment> modCustTable;

    @FXML
    private TableColumn<Customer, String> modCustTitleCol;

    @FXML
    private TableColumn<Customer, String> modCustDescriptionCol;

    @FXML
    private TableColumn<Customer, String> modCustLocationCol;

    @FXML
    private TableColumn<Customer, String> modCustTypeCol;

    @FXML
    private TableColumn<Customer, LocalDateTime> modCustStartCol;

    @FXML
    private TableColumn<Customer, LocalDateTime> modCustEndCol;

    @FXML
    private TableColumn<Customer, String> modCustContactCol;

    @FXML
    private TableColumn<Customer, String> modCustUserCol;

    Stage stage;
    Customer customerToDelete;

    // Declare methods***************************************************************


    @FXML
    void onActionAddAppt(ActionEvent event) {
    }

    /** onActionCancel method - sends the user back to the main screen without
     * modifying the customer.
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

    /** onActionDelete method - deletes the customer from the database as well as all of the
     * appointments associated with that customer.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionDelete(ActionEvent event) throws SQLException, IOException {
        for (Customer customer : DatabaseLists.customers) {
            if (Integer.parseInt(customerIDTxt.getText()) == (customer.getCustomerID())) {
                customerToDelete = customer;
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning Dialog");
        alert.setContentText("Deleting this customer will also delete all " +
                "associated appointments.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            CustomerDao.selectAllCustomerAppointments(customerToDelete);
            for (Appointment appointment : customerToDelete.customerAppointments) {
                CustomerDao.removeCustomerAppointment(appointment);
                System.out.println(appointment);
            }
            CustomerDao.removeCustomerFromDB(customerToDelete);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
    }

    /** onActionUpdate method - updates the customer on the database with the new information.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionUpdate(ActionEvent event) throws SQLException, IOException {
        CustomerDao.updateCustomer(Integer.parseInt(customerIDTxt.getText()), customerNameTxt.getText(), customerAddressTxt.getText(),
                customerZipTxt.getText(), customerPhoneTxt.getText(), customerStateCbo.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /** onActionSelectCountry method - sets the divisions list to a specific list that matches the
     * specific countries in the list.
     * @param event
     */
    @FXML
    void onActionSelectCountry(ActionEvent event) {
        System.out.println(customerCountryCbo.getSelectionModel().getSelectedItem().getCountry());
        DatabaseLists.filteredDivisions.clear();
        for (Division division : DatabaseLists.divisions) {
            if (division.getCountryID() == customerCountryCbo.getSelectionModel().getSelectedItem().getCountryID()) {
                DatabaseLists.filteredDivisions.add(division);
            }
        }
        customerStateCbo.setValue(null);
        customerStateCbo.setItems(DatabaseLists.getFilteredDivisions());
    }

    /** customerToModify method - sets the fields and combo boxes when modifying an
     * existing customers.
     * @param selectedItem
     * @throws SQLException
     */
    public void customerToModify(Customer selectedItem) throws SQLException {
        customerIDTxt.setText(String.valueOf(selectedItem.getCustomerID()));
        customerNameTxt.setText(selectedItem.getCustomerName());
        customerAddressTxt.setText(selectedItem.getCustomerAddress());
        customerZipTxt.setText(selectedItem.getCustomerPostCode());
        customerPhoneTxt.setText(selectedItem.getCustomerPhone());
        customerStateCbo.setValue(selectedItem.getDivision());
        customerCountryCbo.setValue(selectedItem.getCountry());

        CustomerDao.selectAllCustomerAppointments(selectedItem);
        modCustTable.setItems(selectedItem.getCustomerAppointments());

        modCustTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        modCustLocationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        modCustTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        modCustStartCol.setCellValueFactory(new PropertyValueFactory<>("apptStartLDT"));
        modCustEndCol.setCellValueFactory(new PropertyValueFactory<>("apptEndLDT"));
        modCustContactCol.setCellValueFactory(new PropertyValueFactory<>("apptContact"));
    }

    /** the overridden initialize method. sets the country and state combo boxes.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerCountryCbo.setItems(DatabaseLists.getCountries());
        customerStateCbo.setItems((DatabaseLists.getDivisions()));
    }
}