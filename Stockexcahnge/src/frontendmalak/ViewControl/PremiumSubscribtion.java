package frontendmalak.ViewControl;

import backend.Transactions;
import frontendmalak.ViewControl.LogIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static frontendmalak.HelloApplication.stg;
import static frontendmalak.ViewControl.UserTransactions.CSV_FILE_PATH;
import static frontendmalak.ViewControl.UserTransactions.TransactionsList;

public class PremiumSubscribtion {
    @FXML
    private Label Balance1;
    double newBalance;
    @FXML
    private Button subscribeButton;

    private double balance;
    private static final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";
    private static final double PREMIUM_COST = 50.0;

    public void initialize() throws IOException {
        balance = LogIn.currentUser.getCashBalance();
        updateBalanceLabel();

        // Set up the button click handler
        subscribeButton.setOnAction(this::handleSubscribe);
    }

    private void updateBalanceLabel() {
        Balance1.setText("Balance= " + balance);
    }

    public void Back2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();
    }

    @FXML
    private void handleSubscribe(ActionEvent event) {
        if (balance >= PREMIUM_COST) {
            // Deduct the premium cost from the balance
            balance -= PREMIUM_COST;

            // Update the user's balance in the CSV file (You'll need to implement this method)
            updateUserBalanceInCSV(LogIn.currentUser.getUserName(), balance);

            // Update the UI
            updateBalanceLabel();
            subscribeButton.setDisable(true); // Prevent multiple subscriptions
            // Optionally display a success message
            Balance1.setText(Balance1.getText() + " - Successfully subscribed to Premium!");

            balance= newBalance;
        } else {
            // Not enough balance
            // You can display an error message here
            Balance1.setText("Insufficient balance for Premium subscription.");
        }
    }

    // You'll need to implement the CSV file handling methods
    private void updateUserBalanceInCSV(String username, double newBalance) {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(username)) {
                    parts[2] = String.valueOf(newBalance); // Update the balance
                    line = String.join(",", parts); // Reconstruct the line
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }

        // Write back to the CSV
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (String updatedLine : updatedLines) {
                writer.println(updatedLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }

}