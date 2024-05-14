package frontendmalak.ViewControl;

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
import static frontendmalak.ViewControl.UserTransactions.TransactionsList;

import java.io.*;
import java.time.LocalDate;
import java.util.concurrent.Semaphore;

public class AdminManageTransactions {
    private static final String CSV_FILE_PATH = "Stockexcahnge/src/frontendmalak/transactions.csv";


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
//        Transactions transaction = new Transactions("ahmed", "deposit", currentDate, 20, 200, 220);
//        TransactionsList.add(transaction);
//         transaction = new Transactions("ahmed", "deposit", currentDate, 20, 200, 220);
//        TransactionsList.add(transaction);

        tableView.setItems(TransactionsList);

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        typeOfTransactionColumn.setCellValueFactory(new PropertyValueFactory<>("typeOfTransaction"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        currentBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("currentBalance"));
        newBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("newBalance"));
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
    private void handleAccept(ActionEvent event) {
        Transactions selectedTransactions= tableView.getSelectionModel().getSelectedItem();
        selectedTransactions.setDecision(true);
    }

    @FXML
    private void handleReject(ActionEvent event) {
        Transactions selectedTransactions= tableView.getSelectionModel().getSelectedItem();
        selectedTransactions.setDecision(true);

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
                        transaction.getNewBalance() + "," +
                        transaction.getDecision());
                writer.newLine();
            }
            System.out.println("Transactions saved successfully to CSV file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
