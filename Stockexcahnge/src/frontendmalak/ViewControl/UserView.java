package frontendmalak.ViewControl;

import backend.User;
import com.jfoenix.controls.JFXButton;
import frontendmalak.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLOutput;

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
   @FXML
   private JFXButton gopremium;
 public void text() {

 }
public void initialize() {
    currentUser =LogIn.currentUser;
updatewelcome();
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
        this.event = event;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/premiumsubscribtion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/manageOrder.fxml"));
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
    public void chartss(ActionEvent event) throws IOException {
        if (currentUser.isPremium()) {
            this.event = event;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/premium.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stg.setScene(scene);
            stg.show();
            System.out.println("you are premium uer");

        }
       else{
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/charts.fxml"));
           Parent root = loader.load();
           Scene scene = new Scene(root);
           stg.setScene(scene);
           stg.show();
            System.out.println("you are not premium uer");

    }}



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
