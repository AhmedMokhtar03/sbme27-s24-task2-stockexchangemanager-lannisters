package frontendmalak.ViewControl;

import backend.DataManager;
import backend.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.time.LocalDate;

public class UserManagementController {
    @FXML
    private TableView<User> tableView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

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

    public  static ObservableList<User> userList = FXCollections.observableArrayList();
    private static final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";

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
    public void addUser() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            User user = new User(username, password);

             //i was testing if premium columns work
             user.setPremium(true);
            user.setFirstDateOfPremium(LocalDate.now());
            userList.add(user);
            clearFields();
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
}
