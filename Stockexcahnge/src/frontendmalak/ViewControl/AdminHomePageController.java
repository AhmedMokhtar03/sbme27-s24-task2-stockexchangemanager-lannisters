package frontendmalak.ViewControl;

import backend.Calendar;
import backend.Company;
import backend.DataManager;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AdminHomePageController {
    public static boolean started = false;
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
    private Label numOfRequestsLabel;


    @FXML
    private AnchorPane slider;
    @FXML
    private Pane adminPane;

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
for(Company company : DataManager.companyList){
    company.setOpenningPrice(company.getStockPrice());
}
        showAlert("Done", "Session has been started");
started = true;
    }

    @FXML
    void endSession(ActionEvent event) {
for(Company company: DataManager.companyList){
    company.setClosingPrice(company.getStockPrice());

}
        showAlert("Done", "Session has been ended");
started = false;
Calendar.advanceDay();
    }

    public void initialize() {
        String numOfRequests = String.valueOf(DataManager.countRowsInCSV());
        numOfRequestsLabel.setText(numOfRequests);
    }

    @FXML
    void requests(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/adminManageRequests.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void mangeUsers(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/adminManageUsers.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void mangeSecurities(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/adminManageSecurities.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    @FXML
    public void switchToAdminManageBondsScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/Bonds.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();





    }

}
