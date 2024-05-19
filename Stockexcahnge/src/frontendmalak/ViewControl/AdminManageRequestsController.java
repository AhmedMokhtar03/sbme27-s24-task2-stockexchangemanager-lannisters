package frontendmalak.ViewControl;

import backend.DataManager;
import backend.Transactions;
import backend.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import com.jfoenix.controls.JFXButton;
import javafx.stage.Stage;

import static frontendmalak.ViewControl.AdminManageUsersController.userList;
import static frontendmalak.ViewControl.UserTransactions.TransactionsList;
//import static frontendmalak.ViewControl.PortfolioController.confirmedTransactionsList;

import java.io.*;
import java.time.LocalDate;

public class AdminManageRequestsController {
    public User currentUser;

    public AdminManageRequestsController() {}
    public AdminManageRequestsController(User currentUser){
        this.currentUser = currentUser;
    }
    @FXML
    private TableView<Transactions> tableView;

    @FXML
    private TableColumn<Transactions, String> usernameColumn;

    @FXML
    private TableColumn<Transactions, String> typeOfTransactionColumn;

    @FXML
    private TableColumn<Transactions, LocalDate> dateColumn;

    @FXML
    private TableColumn<Transactions, Double> amountColumn;

    @FXML
    private TableColumn<Transactions, Double> currentBalanceColumn;

    @FXML
    private TableColumn<Transactions, Double> newBalanceColumn;

    @FXML
    private JFXButton acceptButton;

    @FXML
    private JFXButton rejectButton;

    private ObservableList<Transactions> pendingTransactionsList = FXCollections.observableArrayList();
    public Transactions confirmTransaction;

    public void initialize() {
        DataManager.loadTransactionsFromCSV();
        loadPendingTransactions();
        DataManager.loadUsersFromCSV();
        loadTable();
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
    private void handleAccept(ActionEvent event) throws IOException {
        Transactions selectedTransactions = tableView.getSelectionModel().getSelectedItem();
        for (User u : userList) {
            if (u.getUserName().equals(selectedTransactions.getUsername())) {
                currentUser = u;
                break;

            }
        }
        if (selectedTransactions.getTypeOfTransaction().equals("deposit")) {
            currentUser.setCashBalance(currentUser.getCashBalance() + selectedTransactions.getAmount());
//            updateBalance();
            DataManager.saveUsersToCSV();
            //here i take copy of the slected and change status and then add to the list
            confirmTransaction=selectedTransactions;
            confirmTransaction.setStatus("Confirmed");
            TransactionsList.add(confirmTransaction);
//i remove the old one and value of the pendinglist(table ones)
            TransactionsList.remove(selectedTransactions);
            tableView.getItems().remove(selectedTransactions);
            updateCurrentBalanceColumn();
        } else if (selectedTransactions.getTypeOfTransaction().equals("withdrawal")) {
            currentUser.setCashBalance(currentUser.getCashBalance() - selectedTransactions.getAmount());
            //updateBalance();
            DataManager.saveUsersToCSV();
            confirmTransaction=selectedTransactions;
            confirmTransaction.setStatus("Confirmed");
            TransactionsList.add(confirmTransaction);
            TransactionsList.remove(selectedTransactions);
            tableView.getItems().remove(selectedTransactions);
            updateCurrentBalanceColumn();
        }
        DataManager.saveTransactionsToCSV();

    }

    @FXML
    private void handleReject(ActionEvent event) {
        Transactions selectedTransactions = tableView.getSelectionModel().getSelectedItem();
        confirmTransaction=selectedTransactions;
        confirmTransaction.setStatus("Rejected");
        TransactionsList.add(confirmTransaction);
        TransactionsList.remove(selectedTransactions);
        tableView.getItems().remove(selectedTransactions);
        DataManager.saveTransactionsToCSV();

    }

//    public void updateBalance() throws IOException {
//        RandomAccessFile file = new RandomAccessFile("Stockexcahnge/src/frontendmalak/users.csv", "rw");
//        String line;
//        StringBuffer modifiedContent = new StringBuffer();
//        boolean userFound = false;
//        while ((line = file.readLine()) != null) {
//            String[] parts = line.split(",");
//            if (parts[0].equals(String.valueOf(currentUser.getID()))) {
//                parts[3] = String.valueOf(currentUser.getCashBalance());
//                line = String.join(",", parts);
//                System.out.println(parts[3]);
//                userFound = true;
//            }
//            modifiedContent.append(line).append(System.lineSeparator());
//        }
//        if (userFound) {
//            file.seek(0);
//            file.writeBytes(modifiedContent.toString());
//            file.setLength(modifiedContent.length());
//            file.close();
//        } else System.out.println("couldn't find user");
//    }

    private void loadPendingTransactions(){
        for(Transactions j : TransactionsList){
            if("Pending".equals(j.getStatus())){
                pendingTransactionsList.add(j);
            }

        }




    }

    public void loadTable() {


        tableView.setItems(pendingTransactionsList);

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        typeOfTransactionColumn.setCellValueFactory(new PropertyValueFactory<>("typeOfTransaction"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        currentBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("currentBalance"));
        newBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("newBalance"));

    }

    public void updateCurrentBalanceColumn() {
        for (Transactions i : pendingTransactionsList) {
            if (i.getUsername().equals(currentUser.getUserName())) {
                i.setCurrentBalance(currentUser.getCashBalance());
                if (i.getTypeOfTransaction().equals("deposit")) {
                    i.setNewBalance(i.getCurrentBalance() + i.getAmount());
                } else {
                    i.setNewBalance(i.getCurrentBalance() - i.getAmount());

                }

            }
        }


    }

    public int numOfRequests(){


        int k =tableView.getItems().size();
        System.out.println(k);
        return k;
    }


}
