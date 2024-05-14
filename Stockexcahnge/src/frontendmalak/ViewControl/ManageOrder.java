package frontendmalak.ViewControl;

// Assuming your FXML files are in the frontend package

import backend.Company;
import backend.CompanyController;
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

import java.io.IOException;

import static frontendmalak.HelloApplication.stg;

// ... other imports as needed

public class ManageOrder {
    private User currentUser = LogIn.currentUser;
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
    private void handleBuyButtonAction() {
        String label = stockLabelField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        for(Company company : CompanyController.companyList){
            if(label.equals(company.getLabel())){
                currentUser.addOrder(label, quantity, "BUY", company.getStockPrice());
            }
        }
    }

    private void buy(String label, int quantity, int id) {
    }

    @FXML
    private void handleStandardOrderButtonAction(ActionEvent event) throws IOException {
        this.event = event;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/standardOrder.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();
    }

    @FXML
    public void handleLimitOrderButtonAction(ActionEvent event) throws IOException {
        this.event = event;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/limitOrder.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();
    }

    public void back2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();


    }

}




