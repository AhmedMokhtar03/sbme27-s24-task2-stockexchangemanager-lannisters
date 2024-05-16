package frontendmalak.ViewControl;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;
import java.io.IOException;
import static frontendmalak.HelloApplication.primaryStage;

public class Aftersubscribtion {
    @FXML
    private JFXButton continu;

    @FXML
    public void Continue(ActionEvent event) throws IOException {
        JFXButton clickedButton = (JFXButton) event.getSource();

        // === Animation 1: Fade Out ===
        FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), clickedButton); // Increased duration
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            try {
                // Load the selectcompany.fxml scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/selectcompany.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // === Animation 2: Rotate ===
        rotateAnimation(clickedButton);

        // === Animation 3: Scale ===
        scaleAnimation(clickedButton);

        // === Delay and Start Animations ===
        PauseTransition delay = new PauseTransition(Duration.millis(200));
        delay.setOnFinished(e -> fadeOut.play()); // Start fade out after delay
        delay.play();
    }

    private void rotateAnimation(JFXButton button) {
        RotateTransition rotate = new RotateTransition(Duration.millis(2000), button); // Increased duration
        rotate.setByAngle(720); // Rotate two full turns
        rotate.setCycleCount(2); // Rotate twice
        rotate.setAutoReverse(true);
        rotate.play();
    }

    private void scaleAnimation(JFXButton button) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(1000), button); // Increased duration
        scale.setToX(1.5); // Scale to 150% width
        scale.setToY(1.5); // Scale to 150% height
        scale.setCycleCount(3); // Scale three times
        scale.setAutoReverse(true);
        scale.play();
    }
}

