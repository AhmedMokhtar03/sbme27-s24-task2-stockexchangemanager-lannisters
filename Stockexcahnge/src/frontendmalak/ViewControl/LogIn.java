package frontendmalak.ViewControl;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;

import static frontendmalak.HelloApplication.stg;

public class LogIn {
    @FXML
    private Label myLabel;

    @FXML
    private Button button;

    @FXML
    public Label wronglogin;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private ChoiceBox<String> choicebox;

    private static final String CSV_FILE = "C:\\Users\\lenovo\\Documents\\GitHub\\sbme27-s24-task2-stockexchangemanager-lannisters\\Stockexcahnge\\src\\frontendmalak\\userdata.csv";

    @FXML
    public void initialize() {
        ObservableList<String> userTypes = FXCollections.observableArrayList("Admin", "User");
        choicebox.setItems(userTypes);
        choicebox.setValue("User");
    }

    @FXML
    public void getSelectStatus(ActionEvent event) {
        String label = choicebox.getValue();
        myLabel.setText(label);
    }

    @FXML
    public void userLogIn(ActionEvent event) throws IOException {
        handleLogin();
    }

    @FXML
    public void handleSignUp(ActionEvent event) {
        try {
            String username = this.username.getText();
            String password = this.password.getText();
            String userType = choicebox.getValue();
if (userType.equals("Admin")) {
    wronglogin.setText("Admin cannot sign up");
    return;
}
            if (!isValidInput(username, password, userType)) {
                wronglogin.setText("Please enter a valid username and password.");
                return;
            }

            if (isExistingUser(username)) {
                wronglogin.setText("Username already exists.");
                return;
            }

            // Append new user data to CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
                writer.write(username + "," + password + "," + userType);
                writer.newLine();
                wronglogin.setText("Sign up successful.");
            } catch (IOException e) {
                wronglogin.setText("Error writing to " + CSV_FILE);
                e.printStackTrace();
                throw e; // rethrowing the exception
            }
        } catch (IOException e) {
            wronglogin.setText("Error during sign up.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin() {
        String username = this.username.getText();
        String password = this.password.getText();
        String userType = choicebox.getValue();

        if (!isValidInput(username, password, userType)) {
            wronglogin.setText("Please enter a valid username and password.");
            return;
        }

        if (isAuthenticated(username, password, userType)) {
            wronglogin.setText("Login successful.");
            try {
                FXMLLoader loader;
                if ("Admin".equals(userType)) {
                    loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/AdminHomePage.fxml"));
                } else {
                    loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
                }

                Parent root = loader.load();
                Scene scene = new Scene(root);
                stg.setScene(scene);
                stg.show();
            } catch (IOException e) {
                wronglogin.setText("Error loading the next scene.");
                e.printStackTrace();
            }
        } else {
            wronglogin.setText("Invalid username, password, or user type.");
        }
    }

    private boolean isValidInput(String username, String password, String userType) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private boolean isExistingUser(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LogIn.CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            wronglogin.setText("Error reading " + LogIn.CSV_FILE);
            e.printStackTrace();
        }
        return false;
    }

    private boolean isAuthenticated(String username, String password, String userType) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LogIn.CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(username) && parts[1].equals(password) && parts[2].equals(userType)) {
                    return true;
                }
            }
            //iojnjnlm
        } catch (IOException e) {
            wronglogin.setText("Error reading " + LogIn.CSV_FILE);
            e.printStackTrace();
        }
        return false;
    }
}

