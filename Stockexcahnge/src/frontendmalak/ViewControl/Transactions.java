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

import java.io.*;

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
    private double balance;
    private double depositValue;
    private double withdrawValue;

    @FXML
    public void initialize() {
        // Assuming you have a way to get the current user and initial values
        depositValue = 0; // Get initial deposit value
        withdrawValue = 0; // Get initial withdrawal value
        balance = LogIn.currentUser.getCashBalance();
        updateBalanceLabel();
        depositButton.setOnAction(this::handleDeposit);
        withdrawButton.setOnAction(this::handleWithdrawal);
    }
    //User user=new User("ahmed","ahmed");


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
    public void updateBalance() throws IOException {
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\chanm\\OneDrive\\Documents\\GitHub\\sbme27-s24-task2-stockexchangemanager-lannisters\\Stockexcahnge\\src\\frontendmalak\\users.csv", "rw");
       String line; StringBuffer modifiedContent = new StringBuffer();
       boolean userFound = false;
        while ((line = file.readLine()) != null){
            String[] parts = line.split(",");
            if(parts[0].equals(String.valueOf(LogIn.currentUser.getID()))){
                parts[3] = String.valueOf(LogIn.currentUser.getCashBalance());
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

    public void withdraw(double amount) throws IOException {
        if (amount <= 0) {
            messageLabel.setText("Withdrawal amount must be positive.");
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance){
            messageLabel.setText("Withdraw Fails!");
            throw new RuntimeException("Withdrawal amount exceeds withdraw limit.");

        }
        balance -= amount;
        LogIn.currentUser.setCashBalance(balance);
        updateBalance();
        System.out.println("Withdrawal successful. New balance: " + LogIn.currentUser.getCashBalance());
        messageLabel.setText("Withdrawal successful!");
        updateBalanceLabel();
    }

    public void deposit(double amount) throws IOException {
        if (amount <= 0) {
            messageLabel.setText("Deposit amount must be positive.");
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
        LogIn.currentUser.setCashBalance(balance);
        updateBalance();
        messageLabel.setText("Deposit successful!");
        updateBalanceLabel();
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

    @FXML
//    private void handleProfitCalculation(ActionEvent event) {
//        double initialValue = 0;
//        setProfitPercentage(initialValue);
//    }

    private void updateBalanceLabel() {
        balanceLabel.setText(String.valueOf("Balance=" +balance));
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
