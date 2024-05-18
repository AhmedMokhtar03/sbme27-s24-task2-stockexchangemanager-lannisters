package frontendmalak.ViewControl;

import backend.DataManager;
import com.jfoenix.controls.JFXButton;
import frontendmalak.ViewControl.LogIn;
import io.github.palexdev.materialfx.controls.MFXButton;
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

import static frontendmalak.HelloApplication.primaryStage;

public class PremiumSubscribtion {
    @FXML
    private Label Balance1;
    @FXML
    private Label messagee;
    @FXML
    private MFXButton subscribeButton;
    @FXML
    private MFXButton selectcompany;

    private double balance;
    private static final String CSV_FILE = "Stockexcahnge/src/frontendmalak/users.csv";
    private static final double PREMIUM_COST = 50.0;

    public void initialize() throws IOException {
        balance = LogIn.currentUser.getCashBalance();
        updateBalanceLabel();
        messagee.setText("You can now subscribe for 50 $, validate for 1 month");
        if (LogIn.currentUser.isPremium()) {
            subscribeButton.setDisable(true);
            subscribeButton.setVisible(false);
            selectcompany.setVisible(true);
            Balance1.setText(Balance1.getText());
            messagee.setText("You are a Premium member!");
        }
       else selectcompany.setVisible(false);
    }

    private void updateBalanceLabel() {
        Balance1.setText("Your balance= " + String.format("%.2f", balance));
    }

    public void Back2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void select(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/Selectcompany.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void handleSubscribe(ActionEvent event) {
        if (!LogIn.currentUser.isPremium() && balance >= PREMIUM_COST) {
            balance -= PREMIUM_COST;
            LogIn.currentUser.setCashBalance(balance);
            LogIn.currentUser.setPremium(true);
            LogIn.currentUser.setFirstDateOfPremium(LocalDate.now());
            DataManager.saveUsersToCSV();
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
                    Scene currentScene = primaryStage.getScene();
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
                        primaryStage.setScene(newScene);
                    });
                    timeline.play();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            pause.play();
        } else if (LogIn.currentUser.isPremium()) {
            messagee.setText("You are already a Premium member!");
            Balance1.setText(Balance1.getText());
        } else {
            Balance1.setText(Balance1.getText());
            messagee.setText("Insufficient balance for Premium subscription.");
        }
    }
}