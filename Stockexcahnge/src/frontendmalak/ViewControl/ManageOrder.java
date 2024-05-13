package frontendmalak.ViewControl;

// Assuming your FXML files are in the frontend package

import backend.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.imageio.IIOParam;
import java.io.IOException;

import static frontendmalak.HelloApplication.stg;

// ... other imports as needed

public class ManageOrder {
//    @FXML public ChoiceBox<String> stockChoiceBox;
//    @FXML
//    public Label currentPriceLabel;
//    @FXML public Label EstimatedTotalCostLabel;
//    @FXML public Label EstimatedTotalProceedsLabel;
//    @FXML private MenuButton orderTypeMenuButton;
//    @FXML public MenuItem buyMenuItem;
//    @FXML public MenuItem sellMenuItem;
//    @FXML
//    public TextField stockLabelField;
//    @FXML
//    public TextField quantityField;
//    private User currentUser; // Assuming you have a way to access the current user
    @FXML
    private MFXButton standardOrderButton;
    @FXML
    private MFXButton limitOrderButton;
    @FXML
    private ActionEvent event;

    @FXML
    private void handleStandardOrderButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/standardOrder.fxml"));
        Parent root = loader.load();
        StandardOrder standardOrderController = loader.getController();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();

    }

    // Method to open Limit Order screen
    @FXML
    private void handleLimitOrderButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/limitOrder.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();
    }

    // Method to return to User View
    @FXML
    public void back2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();
    }

//   @FXML
//    private void openOrderScreen(String fxmlPath) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        stg.setScene(scene);
//        stg.show();
//    }
}




