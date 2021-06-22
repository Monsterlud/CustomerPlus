package controller;

import DAO.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Appointment;
import model.DatabaseLists;
import model.User;
import utilities.DBConnection;
import utilities.TimeConversion;

/** This is the controller for the login.fxml screen.
 *
 */
public class LoginController implements Initializable {

    // Declare fields***************************************************************

    @FXML
    private Label titleLbl;

    @FXML
    private Label directionsLbl;

    @FXML
    private Label usernameLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private Label languageLbl;

    @FXML
    private Label errorMsgLbl;

    @FXML
    private TextField loginUsernameTxt;

    @FXML
    private TextField loginPasswordTxt;

    @FXML
    private Button loginSubmitBtn;

    @FXML
    private Button loginCancelBtn;

    ResourceBundle rb;
    Stage stage;
    int userID;
    boolean value = false;
    public static User validUser;

    // Declare methods******************************************************************

    /** the overriden initialize method - sets up the language resource bundles.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;

        titleLbl.setText(rb.getString("Title"));
        directionsLbl.setText(rb.getString("Description"));
        usernameLbl.setText(rb.getString("Username"));
        passwordLbl.setText(rb.getString("Password"));
        languageLbl.setText(rb.getString("Language"));
        loginCancelBtn.setText(rb.getString("Cancel"));
        loginSubmitBtn.setText(rb.getString("Submit"));

        try {
            UserDao.selectAllUsers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** onActionCancel method - an action handler that closes the application without loggin in.
     *
     * @param event
     */
    @FXML
    void onActionCancel(ActionEvent event) {
        DBConnection.closeConnection();
        System.exit(0);
    }

    /** onActionSubmit method - an action handler that submits the username and
     * password for validation. Also checks for any upcoming appointments for
     * the user that are within 15 minutes. Otherwise, a message is displayed
     * that there are no upcoming appointments for that user.
     *
     * Displays error message for invalid credentials.
     *
     * If the user is valid, sends the user to the main screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSubmit(ActionEvent event) throws IOException {

        boolean found = false;
        boolean valid = false;
        for (User user : DatabaseLists.users) {
            if (loginUsernameTxt.getText().equals(user.getUserName())) {
                found = true;
                if (loginPasswordTxt.getText().equals(user.getUserPassword())) {
                    valid = true;
                    validUser = user;
                }
            }
        }

        if ((found) && (valid)) {

            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm MM/dd/yyyy");

            for (User user : DatabaseLists.users) {
                if (user.getUserName().equals(loginUsernameTxt.getText())) {
                    userID = user.getUserID();
                }
            }

            for (Appointment appointment : DatabaseLists.getAppointments()) {
                if (appointment.getUserID() == userID) {
                    long timeDifference = ChronoUnit.MINUTES.between(currentTime, (TimeConversion.utcToLDT(appointment.getApptStart())));
                    if (timeDifference <= 15 && timeDifference > 0) {
                        value = true;
                        Alert a = new Alert(Alert.AlertType.WARNING);
                        a.setTitle("Upcoming Appointment");
                        a.setContentText("You have an appointment (ID: " + appointment.getApptID() + " | " + dtf.format(TimeConversion.utcToLDT(appointment.getApptStart())) + ") with " + appointment.getApptCustomer() + " in approximately " + timeDifference + " minutes.");
                        a.showAndWait();
                    }
                }
            }

            if (!value) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Upcoming Appointment");
                a.setContentText("There are no upcoming appointments");
                a.showAndWait();
            }
            String filename = "validation log";
            FileWriter fw = new FileWriter(filename, true);
            PrintWriter outputFile = new PrintWriter(fw);
            System.out.println("successful login: username " + loginUsernameTxt.getText() + " | time " + LocalDateTime.now());
            outputFile.println("successful login: username " + loginUsernameTxt.getText() + " | time " + LocalDateTime.now());
            outputFile.close();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        else {
            errorMsgLbl.setText(rb.getString("Error"));
            //System.out.println("Error");
            System.out.println(Locale.getDefault());
            String filename = "validation log";
            FileWriter fw = new FileWriter(filename, true);
            PrintWriter outputFile = new PrintWriter(fw);
            System.out.println("unsuccessful login attempt: username " + loginUsernameTxt.getText() + " | time " + LocalDateTime.now());
            outputFile.println("unsuccessful login attempt: username " + loginUsernameTxt.getText() + " | time " + LocalDateTime.now());
            outputFile.close();
        }
    }
}
