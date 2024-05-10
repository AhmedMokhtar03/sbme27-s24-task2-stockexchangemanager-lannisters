package frontendmalak.ViewControl;

import backend.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

import static frontendmalak.ViewControl.UserManagementController.userList;


public class LogIn {
    @FXML
    private Label myLabel;

    @FXML
    private Button button;

    @FXML
    public Label wronglogin;

    @FXML
    public TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private ChoiceBox<String> choicebox;

    private static final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";

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
        //handleLogin();
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
                    loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/adminHomePage.fxml"));
                } else {
                    //currentUser = new User(username);
                    loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
                }

//                Parent root = loader.load();
//                UserView userViewController = loader.getController();
//                userViewController.setCurrentUser(currentUser);
//                Scene scene = new Scene(root);
//                stg.setScene(scene);
//                stg.show();
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                wronglogin.setText("Error loading the next scene.");
                e.printStackTrace();
            }
        } else {
            wronglogin.setText("Invalid username, password, or user type.");
        }
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

            User user = new User(username, password);
            userList.add(user);
            try (PrintWriter writer = new PrintWriter(new FileWriter(new File(CSV_FILE), true))) {
                for (User tempuser : userList) {
                    writer.println(user.toCSV());
                }
                wronglogin.setText("Sign up successful.");
            } catch (FileNotFoundException e) {
                wronglogin.setText("Error writing to " + CSV_FILE);
                e.printStackTrace();
            }


            // Append new user data to CSV file
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
//                writer.write(username + "," + password + "," + userType);
//                writer.newLine();
//                wronglogin.setText("Sign up successful.");
//                //UserManager.loadUsersFromCSV("Stockexcahnge/src/frontendmalak/userdata.csv");
//            } catch (IOException e) {
//                wronglogin.setText("Error writing to " + CSV_FILE);
//                e.printStackTrace();
//                throw e; // rethrowing the exception
//            }

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
//       catch (IOException e) {
//            wronglogin.setText("Error during sign up.");
//            e.printStackTrace();
//        }
//

    }

    @FXML
   /* private void handleLogin() {
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
                User currentUser = null;
                if ("Admin".equals(userType)) {
                    loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/adminHomePage.fxml"));
                } else {
                    currentUser = new User(username);
                    loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
                }

//                Parent root = loader.load();
//                UserView userViewController = loader.getController();
//                userViewController.setCurrentUser(currentUser);
//                Scene scene = new Scene(root);
//                stg.setScene(scene);
//                stg.show();
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                wronglogin.setText("Error loading the next scene.");
                e.printStackTrace();
            }
        } else {
            wronglogin.setText("Invalid username, password, or user type.");
        }
    }
*/
    private boolean isValidInput(String username, String password, String userType) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private boolean isExistingUser(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LogIn.CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[1].equals(username)) {
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
        //now admin login checking is from the code itself not the csv file
        if (userType.equals("Admin") ){
            if (username.equals("admin") && password.equals("admin")) {
                return true;
            }
        }


        try (BufferedReader reader = new BufferedReader(new FileReader(LogIn.CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[1].equals(username) && parts[2].equals(password) ) {
                    return true;
                }
            }

        } catch (IOException e) {
            wronglogin.setText("Error reading " + LogIn.CSV_FILE);
            e.printStackTrace();
        }
        return false;
    }
//    public String getUsername() {
//        return this.username;
//    }
}

