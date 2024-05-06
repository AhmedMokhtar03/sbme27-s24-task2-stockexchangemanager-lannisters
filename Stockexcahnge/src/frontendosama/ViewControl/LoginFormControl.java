package frontendosama.ViewControl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

public class LoginFormControl {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    void initialize() {
        choiceBox.getItems().addAll("User", "Admin");
        choiceBox.setValue("User");
    }
    String csvFile = "src/storedData/userdata.csv";

    @FXML
    void handleSignIn(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {            // Check if username or password is empty
            errorLabel.setText("Please enter username and password.");
            return;
        }

        if (checkCredentialsForSignIn(username, password)) {       // Check if the entered username and password exist in the CSV file
            // Sign in successful
            errorLabel.setText("Sign in successfully.");
            errorLabel.setTextFill(Color.GREEN);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendosama/View/AdminHomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();



        } else {
            // Sign in failed
            if(choiceBox.getValue().equals("User")) {
                errorLabel.setText("This user does not exist.");
            }
            if(choiceBox.getValue().equals("Admin")) {
                errorLabel.setText("This admin does not exist.");
            }

            errorLabel.setTextFill(Color.RED);

        }

    }

    private boolean checkCredentialsForSignIn(String username, String password) {
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {


            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (choiceBox.getValue().equals("Admin") && data.length >= 4 && data[2].equals(username) && data[3].equals(password)) {
                    return true; // Username and password found
                }
                if (choiceBox.getValue().equals("User") && data.length >= 2 && data[0].equals(username) && data[1].equals(password)) {
                    return true; // Username and password found
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Username and/or password not found
    }

    @FXML
    void handleSignUp(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();


        if (username.isEmpty() || password.isEmpty()) {           // Check if username or password is empty
            errorLabel.setText("Please enter username and password.");
            return;
        }

        if (checkCredentialsForSignUp(username)) {               // Check if username already used
            if(choiceBox.getValue().equals("User")) {
                errorLabel.setText("This user already exists.");
            }
            if(choiceBox.getValue().equals("Admin")) {
                errorLabel.setText("This admin already exists.");
            }
            errorLabel.setTextFill(Color.RED);
            return;
        }
            writeUserData(username, password);      // Write user data to CSV file
            usernameField.clear();                 // Clear the text fields after sign up
            passwordField.clear();
            errorLabel.setText("Sign up successfully.");
            errorLabel.setTextFill(Color.GREEN);
    }

    private boolean checkCredentialsForSignUp(String username) {

        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (choiceBox.getValue().equals("Admin") && data.length >= 4 && data[2].equals(username)) {
                    return true; // Admin name is taken
                }
                if (choiceBox.getValue().equals("User") && data.length >= 2 && data[0].equals(username)) {
                    return true; // Username is taken
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Username and/or password not found
    }

    private void writeUserData(String username, String password) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile, true))) {
           if (choiceBox.getValue().equals("User")) {writer.println(username + "," + password);}
           if (choiceBox.getValue().equals("Admin")) {writer.println("," + "," + username + "," + password);}
        } catch (IOException e) {
            // Handle IO exception
            e.printStackTrace();
        }
    }


}
