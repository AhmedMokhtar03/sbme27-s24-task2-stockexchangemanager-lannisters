package frontendmalak.ViewControl;
import static frontendmalak.ViewControl.NotificationController.notificationList;

import backend.User;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import frontendmalak.HelloApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.management.Notification;
import javax.management.StringValueExp;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;

import static frontendmalak.HelloApplication.primaryStage;

public class UserView {
    ///////////////////////////////////////
    @FXML
    private FontAwesomeIconView notificationBell;
    @FXML
    private Circle notificationCircle;
    @FXML
    private Label numOfNotifications;
    ////////////////////////////////////////

    @FXML
    private JFXButton manageAccount;
    @FXML
    private JFXButton manageOrders;
    @FXML
    private JFXButton transactions;
    @FXML
    private JFXButton premium;
    private ActionEvent event;
    private User currentUser;
    @FXML
    private Label welcome;
    @FXML
    private JFXButton gopremium;

    public void text() {

    }

    public void initialize() {
        currentUser = LogIn.currentUser;
        updatewelcome();


        numOfNotifications.setText(String.valueOf(notificationList.size()));


        //System.out.println(String.valueOf(notificationList.size()));
    }
    ///////////////////////////////////////////////
    @FXML
    public void mouseIN(javafx.scene.input.MouseEvent mouseEvent) {
        notificationBell.setOpacity(1);
    }
    @FXML
    public void mouseOUT(javafx.scene.input.MouseEvent mouseEvent) {
    notificationBell.setOpacity(.5);
    }

    @FXML
    public void doAction(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/notifications.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Lannister Exchange");
        stage.setScene(new Scene(root));
        stage.show();
        numOfNotifications.setText(String.valueOf(0));
    }


    /////////////////////////////////////////////////



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

    public void Gopremium(ActionEvent event) throws IOException {
        //this.event = event;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/premiumsubscribtion.fxml"));
        Parent root = loader.load();

//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(new Scene(root));
//        stage.show();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void ManageAccountt(ActionEvent event) throws IOException {
        //this.event = event;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/portfolio.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public void ManageOrderr(ActionEvent event) throws IOException {
        //this.event = event;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/general.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public void Transactionss(ActionEvent event) throws IOException {
       // this.event = event;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/transactions.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();

    }

    public void chartss(ActionEvent event) throws IOException {
        if (currentUser.isPremium()) {
            //this.event = event;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/premium.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
//            Scene scene = new Scene(root);
//            primaryStage.setScene(scene);
//            primaryStage.show();
            System.out.println("you are premium uer");

        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/charts.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

//            Scene scene = new Scene(root);
//            primaryStage.setScene(scene);
//            primaryStage.show();
            System.out.println("you are not premium uer");

        }
    }

    private void updatewelcome() {
        welcome.setText("Welcome, " + currentUser.getUserName());
    }



}
