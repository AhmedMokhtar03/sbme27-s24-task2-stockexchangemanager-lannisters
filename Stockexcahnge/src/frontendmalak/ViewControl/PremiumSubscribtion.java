package frontendmalak.ViewControl;

import backend.DataManager;
import frontendmalak.ViewControl.LogIn; // Assuming LogIn class is in the same package
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

import static frontendmalak.HelloApplication.stg; // Assuming HelloApplication is your main class
import static frontendmalak.ViewControl.LogIn.currentUser; // Assuming currentUser is in LogIn

public class PremiumSubscribtion {
    @FXML
    private Label Balance1;
    @FXML
    private Button subscribeButton;

    private double balance;
    private static final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv"; // Update with your CSV path
    private static final double PREMIUM_COST = 50.0;

    public void initialize() throws IOException {
        balance = currentUser.getCashBalance();
        updateBalanceLabel();

        // Disable subscribe button if user is already premium
        if (currentUser.isPremium()) {
            subscribeButton.setDisable(true);
            Balance1.setText("You are already a Premium member!");
        }
    }

    private void updateBalanceLabel() {
        Balance1.setText("Balance= " + balance);
    }

    public void Back2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml")); // Update path if needed
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();
    }

    @FXML
    private void handleSubscribe(ActionEvent event) {
        if (!currentUser.isPremium() && balance >= PREMIUM_COST) {
            balance -= PREMIUM_COST;
            currentUser.setCashBalance(balance); // Update user's balance
            currentUser.setPremium(true);
            DataManager.saveUsersToCSV();
            // Set user to premium

            try {
                updateUserInCSV(currentUser.getUserName(), balance, true); // Update CSV
                updateBalanceLabel();
                subscribeButton.setDisable(true); // Disable button after successful subscription
                Balance1.setText(Balance1.getText() + " - Successfully subscribed to Premium!");
            } catch (IOException e) {
                Balance1.setText("Error subscribing to Premium. Please try again later.");
                e.printStackTrace();
            }
        } else if (currentUser.isPremium()) {
            Balance1.setText("You are already a Premium member!");
        } else {
            Balance1.setText("Insufficient balance for Premium subscription.");
        }
    }

    // Method to update the user's balance and premium status in the CSV file
    private void updateUserInCSV(String username, double newBalance, boolean isPremium) throws IOException {
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(username)) {
                    parts[2] = String.valueOf(newBalance);
                    parts[3] = String.valueOf(isPremium);
                    line = String.join(",", parts);
                }
                updatedLines.add(line);
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (String updatedLine : updatedLines) {
                writer.println(updatedLine);
            }
        }
    }
}