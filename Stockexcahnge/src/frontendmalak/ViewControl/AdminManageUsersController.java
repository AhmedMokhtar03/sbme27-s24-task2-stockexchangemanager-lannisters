package frontendmalak.ViewControl;

import backend.DataManager;
import backend.User;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.time.LocalDate;

public class AdminManageUsersController {
    @FXML
    private JFXButton openMenuBTN;
    @FXML
    private JFXButton closeMenuBTN;
    @FXML
    private JFXButton closeAppBTN;
    @FXML
    private JFXButton goBackBTN;

    @FXML
    private Label deleteLabel;

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, Double> cashBalanceColumn;
    @FXML
    private TableColumn<User, String> premiumColumn;
    @FXML
    private TableColumn<User, String> premiumDateColumn;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    @FXML
    private AnchorPane menuPane;
    @FXML
    private AnchorPane tablePane;

    public  static ObservableList<User> userList = FXCollections.observableArrayList();
    private static final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";

    //============================================================
    @FXML
    public void initialize() {
        DataManager.loadUsersFromCSV();
        tableView.setItems(userList);

        // Initialize all TableColumn cell value factories
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        cashBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("cashBalance"));

        // Initialize premium column cell value factory
        premiumColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            return new SimpleStringProperty(user.isPremium() ? "true" : "false");
        });

        // Initialize premium date column cell value factory
        premiumDateColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            return new SimpleStringProperty(user.isPremium() ? user.getFirstDateOfPremium().toString() : "Not Premium");
        });
       // System.out.println(userList.get(0).getFirstDateOfPremium().toString());
        //System.out.println(userList.get(1).getFirstDateOfPremium().toString());
        //System.out.println(userList.get(2).getFirstDateOfPremium().toString());


    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/AdminHomePage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void openMenu(ActionEvent event) {

        TranslateTransition slide1 = new TranslateTransition(Duration.millis(500), menuPane);
        slide1.setToX(0);
        slide1.play();

        TranslateTransition slide2 = new TranslateTransition(Duration.millis(500), tablePane);
        slide2.setToX(200);
        slide2.play();

        slide1.setOnFinished((ActionEvent e) -> {
            openMenuBTN.setVisible(false);
            closeMenuBTN.setVisible(true);
        });
    }

    @FXML
    void closeMenu(ActionEvent event) {

        TranslateTransition slide1 = new TranslateTransition(Duration.millis(500), menuPane);
        slide1.setToX(-200);
        slide1.play();

        TranslateTransition slide2 = new TranslateTransition(Duration.millis(500), tablePane);
        slide2.setToX(0);
        slide2.play();


        slide1.setOnFinished((ActionEvent e) -> {
            openMenuBTN.setVisible(true);
            closeMenuBTN.setVisible(false);
        });
    }

    @FXML
    public void addUser() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            if(!isExistingUser(username)){
            User user = new User(username, password);

             //i was testing if premium columns work
             user.setPremium(true);
            user.setFirstDateOfPremium(LocalDate.now());
            userList.add(user);
            clearFields();
            }
            else{
                showAlert("Error", "User is already exist.");

            }
        } else {
            showAlert("Error", "Username and password cannot be empty.");
        }
    }

    @FXML
    public void deleteUser() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userList.remove(selectedUser);
        } else {
            showAlert("Error", "Please select a user to delete.");
        }
    }

    @FXML
    public void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new File(CSV_FILE))) {
            for (User user : userList) {
                writer.println(user.toCSV());
            }
            showAlert("Success", "Users saved successfully.");
        } catch (FileNotFoundException e) {
            showAlert("Error", "Failed to save users.");
            e.printStackTrace();
        }
    }


    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    private void clearFields() {
        usernameTextField.clear();
        passwordTextField.clear();
    }

    private boolean isExistingUser(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[1].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        return false;
    }
}
