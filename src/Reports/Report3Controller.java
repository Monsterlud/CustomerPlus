package Reports;

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
import java.util.ResourceBundle;

/** This is the controller for the report3.fxml screen.
 *
 */
public class Report3Controller implements Initializable {

    // Declare fields******************************************************************
    @FXML
    private Label customerDisplayLbl;

    @FXML
    private Button report3BackBtn;

    @FXML
    private TableView<Customer> report3TableTbl;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private ComboBox<Country> countryCbo;

    Stage stage;

    // Declare methods******************************************************************

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

    /** onActionChooseCountry method - chooses the country on which customers will populate
     * the table. Upon choosing the country, only customers from that country will be
     * visible in the table.
     * @param event
     */
    @FXML
    void onActionChooseCountry(ActionEvent event) {
        DatabaseLists.filteredCustomers.clear();
        for (Country country : DatabaseLists.countries) {
            if (countryCbo.getSelectionModel().getSelectedItem().getCountryID() == country.getCountryID()) {
                for (Customer customer : DatabaseLists.customers) {
                    if (customer.getCustomerCountry().equals(countryCbo.getSelectionModel().getSelectedItem().getCountry())) {
                        DatabaseLists.filteredCustomers.add(customer);
                    }
                }
            }
            report3TableTbl.setItems(DatabaseLists.filteredCustomers);
            customerDisplayLbl.setText("Customers from " + countryCbo.getSelectionModel().getSelectedItem().getCountry());
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        }
    }

    /** The overridden initialize method - loads the countries into the combo box list.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCbo.setItems(DatabaseLists.countries);
    }
}

