package frontendmalak.ViewControl;

import backend.DataManager;
import backend.Transactions;
import backend.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import static backend.Calendar.currentDate;
import static frontendmalak.ViewControl.AdminMangeUsersController.userList;
import static frontendmalak.ViewControl.UserTransactions.TransactionsList;

import java.io.*;
import java.time.LocalDate;
import java.util.concurrent.Semaphore;

public class AdminManageTransactions {
    private static final String CSV_FILE_PATH = "Stockexcahnge/src/frontendmalak/transactions.csv";
    private static final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";
private User currentUser;

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
    private Button acceptButton;

    @FXML
    private Button rejectButton;

    public boolean decision;


    public void initialize() {
        loadTransactionsFromCSV();
        DataManager.loadUsersFromCSV();
       loadTable();
    }

    private void loadTransactionsFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                String typeOfTransaction = parts[1];
                LocalDate date = LocalDate.parse(parts[2]);
                double amount = Double.parseDouble(parts[3]);
                double currentBalance = Double.parseDouble(parts[4]);
                double newBalance = Double.parseDouble(parts[5]);

                Transactions transaction = new Transactions(username, typeOfTransaction, date, amount, currentBalance, newBalance);
                TransactionsList.add(transaction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAccept(ActionEvent event) throws IOException {
        Transactions selectedTransactions= tableView.getSelectionModel().getSelectedItem();
        for(User u : userList) {
            if (u.getUserName().equals(selectedTransactions.getUsername())) {
                currentUser = u;
                break;

            }
        }
            if(selectedTransactions.getTypeOfTransaction().equals("deposit")){
                currentUser.setCashBalance(currentUser.getCashBalance()+selectedTransactions.getAmount());
                updateBalance();
                TransactionsList.remove(selectedTransactions);
                tableView.getItems().remove(selectedTransactions);
                updateCurrentBalanceColumn();

            }

            else if(selectedTransactions.getTypeOfTransaction().equals("withdrawal")){
                currentUser.setCashBalance(currentUser.getCashBalance()-selectedTransactions.getAmount());
                updateBalance();
                TransactionsList.remove(selectedTransactions);
                tableView.getItems().remove(selectedTransactions);
                updateCurrentBalanceColumn();
            }
    }

    @FXML
    private void handleReject(ActionEvent event) {
        Transactions selectedTransactions= tableView.getSelectionModel().getSelectedItem();
        TransactionsList.remove(selectedTransactions);
        tableView.getItems().remove(selectedTransactions);

    }
    public  void updateBalance() throws IOException {
        RandomAccessFile file = new RandomAccessFile(CSV_FILE, "rw");
        String line; StringBuffer modifiedContent = new StringBuffer();
        boolean userFound = false;
        while ((line = file.readLine()) != null){
            String[] parts = line.split(",");
            if(parts[0].equals(String.valueOf(currentUser.getID()))){
                parts[3] = String.valueOf(currentUser.getCashBalance());
                line = String.join(",", parts);
                System.out.println(parts[3]);
                userFound = true;
            }
            modifiedContent.append(line).append(System.lineSeparator());}
        if(userFound) {
            file.seek(0);
            file.writeBytes(modifiedContent.toString());
            file.setLength(modifiedContent.length());
            file.close();
        }else System.out.println("couldn't find user");
    }


    @FXML
    private void handleSave(ActionEvent event) {
        saveTransactionsToCSV();
    }

    private void saveTransactionsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            for (Transactions transaction : TransactionsList) {
                writer.write(transaction.getUsername() + "," +
                        transaction.getTypeOfTransaction() + "," +
                        transaction.getDate() + "," +
                        transaction.getAmount() + "," +
                        transaction.getCurrentBalance() + "," +
                        transaction.getNewBalance() );
                writer.newLine();
            }
            System.out.println("Transactions saved successfully to CSV file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public void loadTable(){

    tableView.setItems(TransactionsList);

    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    typeOfTransactionColumn.setCellValueFactory(new PropertyValueFactory<>("typeOfTransaction"));
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    currentBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("currentBalance"));
    newBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("newBalance"));



}
public void updateCurrentBalanceColumn(){
    for(Transactions i : TransactionsList) {
        if (i.getUsername().equals(currentUser.getUserName())) {
            i.setCurrentBalance(currentUser.getCashBalance());
        }
    }



}



}
