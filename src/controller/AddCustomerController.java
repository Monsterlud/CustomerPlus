package controller;

import DAO.CustomerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.DatabaseLists;
import model.Division;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** This is the controller for the addCustomer.fxml screen.
 *
 */
public class AddCustomerController implements Initializable {

    // Declare fields**************************************************************
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
    private Button customerCancelBtn;

    @FXML
    private Button customerSubmitBtn;

    @FXML
    private TextField customerIDTxt;

    Stage stage;

    // Declare methods********************************************************************

    /** onActionCancel method - sends the user back to the main screen without adding
     * the customer.
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

    /** onActionSubmit method - adds the customer to the database. calls the insertCustomer
     * method then sends the user back to the main screen.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSubmit(ActionEvent event) throws SQLException, IOException {
        for (Division division : DatabaseLists.divisions) {
            if (division.getDivisionID() == customerStateCbo.getSelectionModel().getSelectedItem().getDivisionID()) {
                CustomerDao.insertCustomer(customerNameTxt.getText(),
                        customerAddressTxt.getText(), customerZipTxt.getText(), customerPhoneTxt.getText(), division);
            }
        }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
    }

    /** onActionSelectCountry method - an action handler that connects the countries and
     * divisions lists so that only specific divisions are available for specific countries.
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
        customerStateCbo.setItems(DatabaseLists.getFilteredDivisions());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerCountryCbo.setItems(DatabaseLists.getCountries());
        customerCountryCbo.getSelectionModel().select(0);
        customerStateCbo.setItems(DatabaseLists.getDivisions());

        DatabaseLists.filteredDivisions.clear();
        for (Division division : DatabaseLists.divisions) {
            if (division.getCountryID() == customerCountryCbo.getSelectionModel().getSelectedItem().getCountryID()) {
                DatabaseLists.filteredDivisions.add(division);
            }
        }
        customerStateCbo.setItems(DatabaseLists.getFilteredDivisions());
        customerStateCbo.getSelectionModel().select(0);
    }
}
