package frontendmalak.ViewControl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class General {
@FXML
    private Text Started;

    @FXML
public void switchToBond(ActionEvent event) throws IOException {
        if(AdminHomePageController.started) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserBonds.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();
        }
        else{
            showAlert("Error","Session in not started yet");

        }
}

    @FXML
    private void handleStandardOrderButtonAction(ActionEvent event) throws IOException {
        if(AdminHomePageController.started){
            //this.event = event;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/standardOrder.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
        }
        else{
            //Started.setVisible(true);
            showAlert("Error","Session in not started yet");

        }
    }@FXML
    public void handleLimitOrderButtonAction(ActionEvent event) throws IOException {
        if(AdminHomePageController.started){
            //this.event = event;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/limitOrder.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
        }
        else{
           // Started.setVisible(true);
            showAlert("Error","Session in not started yet");

        }
    }

    public void back2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();


    }

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
}
