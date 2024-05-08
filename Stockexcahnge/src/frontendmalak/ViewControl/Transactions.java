package frontendmalak.ViewControl;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import backend.User; // Assuming you have a User class in the backend package

import java.io.IOException;

import static frontendmalak.HelloApplication.stg; // Assuming you have a HelloApplication class with a static stage variable

public class Transactions {

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


    private Button back1;

    private double depositValue;
    private double withdrawValue;

    private static User currentUser;

    @FXML
    public void initialize() {
        // Assuming you have a way to get the current user and initial values
        currentUser = getCurrentUser(); // Get the current user somehow
        depositValue = 0; // Get initial deposit value
        withdrawValue = 0; // Get initial withdrawal value

        updateBalanceLabel();

        depositButton.setOnAction(this::handleDeposit);
        withdrawButton.setOnAction(this::handleWithdrawal);


    }
    User user=new User(0,"ahmed","ahmed");

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    private User getCurrentUser() {
        return user;
    }

//    public void setProfitPercentage(double initialValue) {
//        if (initialValue <= 0) {
//            throw new IllegalArgumentException("Initial value must be positive for profit calculation.");
//
//        }
//
//        double profit = depositValue - withdrawValue - initialValue;
//        profitPercentage = (profit / initialValue) * 100;
//        System.out.println("Profit Percentage: " + profitPercentage + "%");
//    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            messageLabel.setText("Withdrawal amount must be positive.");
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > currentUser.getCashBalance()){
//
            messageLabel.setText("Withdraw Fails!");
            throw new RuntimeException("Withdrawal amount exceeds withdraw limit.");

        }
        withdrawValue -= amount;
        currentUser.setCashBalance(currentUser.getCashBalance() - amount);
        System.out.println("Withdrawal successful. New balance: " + currentUser.getCashBalance());
        messageLabel.setText("Withdrawal successful!");
        updateBalanceLabel();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            messageLabel.setText("Deposit amount must be positive.");
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        depositValue += amount;
        currentUser.setCashBalance(currentUser.getCashBalance() + amount);
        messageLabel.setText("Deposit successful!");

        updateBalanceLabel();
    }

    @FXML
    private void handleDeposit(ActionEvent event) {
        try {
            double amount = Double.parseDouble(depositAmountField.getText());
            deposit(amount);
        } catch (NumberFormatException e) {

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
        }
    }

    @FXML
//    private void handleProfitCalculation(ActionEvent event) {
//        double initialValue = 0;
//        setProfitPercentage(initialValue);
//    }

    private void updateBalanceLabel() {
        balanceLabel.setText(String.valueOf("Balance=" +currentUser.getCashBalance()));
    }


    public void Back1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();
    }
}
//package frontendmalak.ViewControl;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//
//import java.awt.*;
//import java.io.IOException;
//
//import static frontendmalak.HelloApplication.stg;
//
//public class Transactions {
//    private Button back1;
//    private ActionEvent event;
//
//    public void Back1(ActionEvent event) throws IOException {
//        this.event = event;
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        stg.setScene(scene);
//        stg.show();
//
//    }
//}
