package frontendmalak.ViewControl;

import backend.User;
import com.jfoenix.controls.JFXButton;
import frontendmalak.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javax.swing.*;
import java.io.IOException;

import static frontendmalak.HelloApplication.stg;

public class UserView {
    @FXML
private JFXButton manageAccount;;
@FXML
private JFXButton manageOrders;
@FXML
private JFXButton transactions;
@FXML
private JFXButton premium;
    private ActionEvent event;
    private User currentUser;
   @FXML
    private  Label welcome;
 public void text() {

 }
    public void ManageAccountt(ActionEvent event) throws IOException {
        this.event = event;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/manageAccount.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();





    }
    public void ManageOrderr(ActionEvent event) throws IOException {
        this.event = event;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/manageAccount.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();

    }
    public void Transactionss(ActionEvent event) throws IOException {
        this.event = event;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/transactions.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();

    }
    public void Premiumm(ActionEvent event) throws IOException {
        this.event = event;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/premium.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();

    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        updatewelcome();
    }

    private void updatewelcome() {
        welcome.setText("Welcome, " + currentUser.getUserName());
    }



//    public void ManageOrder(ActionEvent event) throws IOException {
//        HelloApplication.changeScene();
//    }
//    public void Transactions(ActionEvent event) throws IOException {
//        HelloApplication.changeScene();
//    }
//    public void Premium(ActionEvent event) throws IOException {
//        HelloApplication.changeScene();
//    }
// ... other imports



        // ... other fields and methods



}
