package frontendmalak.ViewControl;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXButton;
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
    private MFXButton continu;

    @FXML
    public void Continue(ActionEvent event) throws IOException {



        // Load the selectcompany.fxml scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/selectcompany.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
