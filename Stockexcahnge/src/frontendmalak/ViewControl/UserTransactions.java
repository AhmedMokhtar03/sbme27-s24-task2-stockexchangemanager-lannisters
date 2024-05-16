package frontendmalak.ViewControl;

import backend.DataManager;
import backend.Transactions;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import backend.User; // Assuming you have a User class in the backend package

import java.io.*;
import java.time.LocalDate;

import static backend.Calendar.currentDate;
import static frontendmalak.HelloApplication.primaryStage; // Assuming you have a HelloApplication class with a static stage variable

public class UserTransactions {
    @FXML
    private TextField depositAmountField;
    @FXML
    private TextField withdrawalAmountField;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private MFXButton depositButton;
    @FXML
    private MFXButton withdrawButton;
    @FXML

    public static ObservableList<Transactions> TransactionsList = FXCollections.observableArrayList();

    @FXML
    private Button back1;
    private double balance;

    @FXML
    public void initialize() throws IOException {
        DataManager.loadTransactionsFromCSV();
        balance = LogIn.currentUser.getCashBalance();
        updateBalanceLabel();
        depositButton.setOnAction(this::handleDeposit);
        withdrawButton.setOnAction(this::handleWithdrawal);
    }

    public void withdraw(double amount) throws IOException {
        if (amount <= 0) {
            messageLabel.setText("Withdrawal amount must be positive.");
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            messageLabel.setText("Withdraw Fails!");
            throw new RuntimeException("Withdrawal amount exceeds withdraw limit.");

        }
        Transactions transaction = new Transactions(LogIn.currentUser.getUserName(), "withdrawal", LocalDate.now(), amount, LogIn.currentUser.getCashBalance(), LogIn.currentUser.getCashBalance() - amount);
       TransactionsList.add(transaction);
        DataManager.saveTransactionsToCSV();
        messageLabel.setText("Request pending");

    }


    public void deposit(double amount) throws IOException {
        if (amount <= 0) {
            messageLabel.setText("Deposit amount must be positive.");
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        Transactions transaction = new Transactions(LogIn.currentUser.getUserName(), "deposit", LocalDate.now(), amount, LogIn.currentUser.getCashBalance(), LogIn.currentUser.getCashBalance() + amount);
        TransactionsList.add(transaction);
        DataManager.saveTransactionsToCSV();
        messageLabel.setText("Request pending");
    }

    @FXML
    private void handleDeposit(ActionEvent event) {
        try {
            double amount = Double.parseDouble(depositAmountField.getText());
            deposit(amount);
        } catch (NumberFormatException | IOException e) {
            messageLabel.setText("Invalid input for deposit amount.");
        }
    }

    @FXML
    private void handleWithdrawal(ActionEvent event) {
        try {
            double amount = Double.parseDouble(withdrawalAmountField.getText());
            withdraw(amount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for withdrawal amount.");
            messageLabel.setText("Invalid input for withdrawal amount.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void updateBalanceLabel() {
        balanceLabel.setText(String.valueOf("Balance=" + balance));
    }

    public void Back1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
