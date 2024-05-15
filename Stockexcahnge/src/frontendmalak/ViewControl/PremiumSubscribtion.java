package frontendmalak.ViewControl;

import backend.DataManager;
import com.jfoenix.controls.JFXButton;
import frontendmalak.ViewControl.LogIn;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static frontendmalak.HelloApplication.stg;

public class PremiumSubscribtion {
    @FXML
    private Label Balance1;
    @FXML
    private Label messagee;
    @FXML
    private JFXButton subscribeButton;
    private double balance;
    private static final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";
    private static final double PREMIUM_COST = 50.0;

    public void initialize() throws IOException {
        balance = LogIn.currentUser.getCashBalance();
        updateBalanceLabel();
        messagee.setText("You can now subscribe for 50 $, validate for 1 month");
        if (LogIn.currentUser.isPremium()) {
            subscribeButton.setDisable(true);
            Balance1.setText(Balance1.getText());
            messagee.setText("You are a Premium member!");
        }
    }

    private void updateBalanceLabel() {
        Balance1.setText("Your balance= " + balance);
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
        if (!LogIn.currentUser.isPremium() && balance >= PREMIUM_COST) {
            balance -= PREMIUM_COST;
            LogIn.currentUser.setCashBalance(balance);
            LogIn.currentUser.setPremium(true);
            LogIn.currentUser.setFirstDateOfPremium(LocalDate.now());
            DataManager.saveUsersToCSV();
            try {
                updateUserInCSV(LogIn.currentUser.getUserName(), balance, true);
                updateBalanceLabel();
                subscribeButton.setDisable(true);
                Balance1.setText(Balance1.getText());
                messagee.setText("Successfully subscribed to Premium!");

                // Pause for 0.5 seconds
                PauseTransition pause = new PauseTransition(Duration.millis(500));
                pause.setOnFinished(e -> {
                    try {
                        // Load the new FXML scene
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/aftersubscribtion.fxml"));
                        Parent root = loader.load();
                        Scene newScene = new Scene(root);

                        // Get the current scene and its root node
                        Scene currentScene = stg.getScene();
                        AnchorPane currentRoot = (AnchorPane) currentScene.getRoot();

                        // Set the new scene's dimensions to match the current scene
                        newScene.getRoot().prefWidth(currentScene.getWidth());
                        newScene.getRoot().prefHeight(currentScene.getHeight());

                        // Add the new scene's root to the current scene
                        currentRoot.getChildren().add(newScene.getRoot());

                        // Set initial position off-screen to the left
                        newScene.getRoot().setTranslateX(-currentScene.getWidth());

                        // Create the sliding animation
                        Timeline timeline = new Timeline();
                        KeyValue kv = new KeyValue(newScene.getRoot().translateXProperty(), 0);
                        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv); // 1-second animation duration
                        timeline.getKeyFrames().add(kf);
                        timeline.setOnFinished(event1 -> {
                            // Remove the old scene after animation is complete
                            currentRoot.getChildren().remove(currentScene.getRoot());
                            stg.setScene(newScene);
                        });
                        timeline.play();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                pause.play();
            } catch (IOException e) {
                messagee.setText("Error subscribing to Premium. Please try again later.");
                Balance1.setText(Balance1.getText());
                e.printStackTrace();
            }
        } else if (LogIn.currentUser.isPremium()) {
            messagee.setText("You are already a Premium member!");
            Balance1.setText(Balance1.getText());
        } else {
            Balance1.setText(Balance1.getText());
            messagee.setText("Insufficient balance for Premium subscription.");
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