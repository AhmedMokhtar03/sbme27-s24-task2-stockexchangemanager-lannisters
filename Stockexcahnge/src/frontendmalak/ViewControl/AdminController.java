package frontendmalak.ViewControl;

import com.jfoenix.controls.JFXButton;
import frontendmalak.HelloApplication;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import static frontendmalak.HelloApplication.stg;

public class AdminController {

    @FXML
    private JFXButton closeAppBTN;

    @FXML
    private JFXButton signOutBTN;

    @FXML
    private JFXButton menuBTN;

    @FXML
    private JFXButton closeMenuBTN;

    @FXML
    private JFXButton startSessionBTN;


    @FXML
    private JFXButton endSessionBTN;

    @FXML
    private JFXButton requestsBTN;

    @FXML
    private JFXButton mangeUsersBTN;

    @FXML
    private JFXButton mangeSecuritiesBTN;

    @FXML
    private Pane adminPane;

    @FXML
    private AnchorPane slider;

    //=======================================================
    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();

    }

    @FXML
    void signOut(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/helloView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void openMenu(ActionEvent event) {

        TranslateTransition slide1 = new TranslateTransition(Duration.millis(500), slider);
        slide1.setToX(0);
        slide1.play();

        TranslateTransition slide2 = new TranslateTransition(Duration.millis(500), adminPane);
        slide2.setToX(100);
        slide2.play();

        slide1.setOnFinished((ActionEvent e) -> {
            menuBTN.setVisible(false);
            closeMenuBTN.setVisible(true);
        });
    }

    @FXML
    void closeMenu(ActionEvent event) {

        TranslateTransition slide1 = new TranslateTransition(Duration.millis(500), slider);
        slide1.setToX(-220);
        slide1.play();

        TranslateTransition slide2 = new TranslateTransition(Duration.millis(500), adminPane);
        slide2.setToX(0);
        slide2.play();

        slide1.setOnFinished((ActionEvent e) -> {
            menuBTN.setVisible(true);
            closeMenuBTN.setVisible(false);
        });

    }

    @FXML
    void startSession(ActionEvent event) {

    }

    @FXML
    void endSession(ActionEvent event) {

    }

    @FXML
    void requests(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/requests.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();



    }

    @FXML
    void mangeUsers(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserManagement.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void mangeSecurities(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/mangeSecurities.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

}
