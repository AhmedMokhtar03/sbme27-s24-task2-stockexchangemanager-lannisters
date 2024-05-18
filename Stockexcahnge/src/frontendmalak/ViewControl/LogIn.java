package frontendmalak.ViewControl;

import backend.DataManager;
import backend.User;
import frontendmalak.HelloApplication;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

import static frontendmalak.ViewControl.AdminManageUsersController.userList;


public class LogIn {
    public static User currentUser;
    @FXML
    private MFXButton window;
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
    @FXML
    private Text title;



    private static final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";

    @FXML
    public void initialize() {
        DataManager.loadCompanies();
        DataManager.loadUsersFromCSV();
        DataManager.loadPriceHistoryFromCSV();
        ObservableList<String> userTypes = FXCollections.observableArrayList("Admin", "User");
        choicebox.setItems(userTypes);
        choicebox.setValue("User");

    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();

    }


    @FXML
    public void windowAction(ActionEvent event) {
        HelloApplication.openNewWindow();
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
                DataManager.loadUsersFromCSV();
                if ("Admin".equals(userType)) {
                    loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/adminHomePage.fxml"));

                } else {
                    //currentUser = new User(username);
                    loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
                    for (User u : userList) {
                        if (u.getUserName().equals(username)) {
                            currentUser = u;
                            break;
                        }
                    }
                    DataManager.loadSubscriptions();
                    if (currentUser == null) {
                        wronglogin.setText("User not found.");
                        return;
                    }
                }
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
            }            User user = new User(username, password);
            userList.add(user);
            try (PrintWriter writer = new PrintWriter(new FileWriter(new File(CSV_FILE)))) {
                for (User tempuser : userList) {
                    writer.println(tempuser.toCSV());
                }
                wronglogin.setText("Sign up successful.");
            } catch (FileNotFoundException e) {
                wronglogin.setText("Error writing to " + CSV_FILE);
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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
                if (parts.length >= 7 && parts[1].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            wronglogin.setText("Error reading " + LogIn.CSV_FILE);
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private boolean isAuthenticated(String username, String password, String userType) {
        //now admin login checking is from the code itself not the csv file
        if (userType.equals("Admin")) {
            if (username.equals("admin") && password.equals("admin")) {
                return true;
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(LogIn.CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7 && parts[1].equals(username) && parts[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            wronglogin.setText("Error reading " + LogIn.CSV_FILE);
            e.printStackTrace();
        }
        return false;
}

}
