package frontendmalak.ViewControl;

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
import static frontendmalak.HelloApplication.stg; // Assuming you have a HelloApplication class with a static stage variable

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

    public  static ObservableList<Transactions> TransactionsList = FXCollections.observableArrayList();

    private Button back1;
    private double balance;
    private double depositValue;
    private double withdrawValue;
    private static final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";
    private static final String CSV_FILE_PATH = "Stockexcahnge/src/frontendmalak/transactions.csv";

    @FXML
    public void initialize() throws IOException {
        loadTransactionsFromCSV();
        check();
        // Assuming you have a way to get the current user and initial values
        depositValue = 0; // Get initial deposit value
        withdrawValue = 0; // Get initial withdrawal value
        balance = LogIn.currentUser.getCashBalance();
        updateBalanceLabel();
        depositButton.setOnAction(this::handleDeposit);
        withdrawButton.setOnAction(this::handleWithdrawal);

    }



    public void updateBalance() throws IOException {
        RandomAccessFile file = new RandomAccessFile(CSV_FILE, "rw");
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
        if (amount > balance) {
            messageLabel.setText("Withdraw Fails!");
            throw new RuntimeException("Withdrawal amount exceeds withdraw limit.");

        }
        Transactions transaction = new Transactions(LogIn.currentUser.getUserName(), "withdrawal", LocalDate.now(), amount, LogIn.currentUser.getCashBalance(), LogIn.currentUser.getCashBalance() - amount);
        TransactionsList.add(transaction);
    }


    public void deposit(double amount) throws IOException {
        if (amount <= 0) {
            messageLabel.setText("Deposit amount must be positive.");
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        Transactions transaction = new Transactions(LogIn.currentUser.getUserName(), "deposit", LocalDate.now(), amount, LogIn.currentUser.getCashBalance(), LogIn.currentUser.getCashBalance() + amount);
        TransactionsList.add(transaction);

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
        balanceLabel.setText(String.valueOf("Balance=" +balance));
    }



    private void check() throws IOException {
        for (Transactions i : TransactionsList) {

            if (i.getDecision() == true) {
                if(i.getTypeOfTransaction().equals("deposit")){
                    //currentUser.set(balance) += i.getAmount();
                LogIn.currentUser.setCashBalance(LogIn.currentUser.getCashBalance()+i.getAmount());
                updateBalance();
                messageLabel.setText("Deposit successful!");
                updateBalanceLabel();
                }
                else if(i.getTypeOfTransaction().equals("withdrawal")){

                   // currentUser.get(balance) -= i.getAmount();
                    LogIn.currentUser.setCashBalance(LogIn.currentUser.getCashBalance()-i.getAmount());
                    updateBalance();
                    System.out.println("Withdrawal successful. New balance: " + LogIn.currentUser.getCashBalance());
                    messageLabel.setText("Withdrawal successful!");
                    updateBalanceLabel();

                }

            }

        }



    }

    public void Back1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();
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




}
