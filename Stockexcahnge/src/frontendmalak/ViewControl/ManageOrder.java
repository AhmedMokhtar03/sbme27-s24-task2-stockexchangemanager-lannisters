package frontendmalak.ViewControl;

// Assuming your FXML files are in the frontend package

import backend.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static frontendmalak.HelloApplication.primaryStage;

// ... other imports as needed

public class ManageOrder {
    private User currentUser = LogIn.currentUser;
    @FXML
    private Text Started;
    @FXML
    private TableView<ManageOrder> orderTable;
    @FXML
    private TableColumn<ManageOrder, Integer> idColumn;
    @FXML
    private TableColumn<ManageOrder, String> labelColumn;
    // ... other table columns as needed

    @FXML
    private TextField stockLabelField;
    @FXML
    private TextField quantityField;
    @FXML
    private Button buyButton;
    @FXML
    private Button sellButton;
    private ObservableList<ManageOrder> orderData;
    @FXML
    private MFXButton standardOrderButton;
    @FXML
    private MFXButton limitOrderButton;
    private ActionEvent event;

    @FXML
    public void initialize() {
        // ... (Initialize table columns and cell factories as shown in previous examples)

        // Initialize order data (replace with your actual logic to get orders)
        // orderData = FXCollections.observableArrayList(currentUser.getOrders());
        // orderTable.setItems(orderData);
    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();

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
           Started.setVisible(true);
       }
    }

    @FXML
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
            Started.setVisible(true);
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

}




