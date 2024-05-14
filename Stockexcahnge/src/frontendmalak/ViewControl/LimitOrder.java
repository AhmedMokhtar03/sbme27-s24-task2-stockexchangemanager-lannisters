package frontendmalak.ViewControl;

import backend.Company;
import backend.DataManager;
import backend.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;

import static frontendmalak.HelloApplication.stg;

public class LimitOrder {
    private User currentUser = LogIn.currentUser;
    private Company company;
    private String selectedStock;
    private int quantity;
    AdminManageRequestsController admin = new AdminManageRequestsController(currentUser);
    @FXML private ChoiceBox<String> stockChoiceBox;
    @FXML
    private Label stockLabelField;
    @FXML
    private Label quantityField;
    @FXML
    private Label limitPriceField;
    @FXML private MenuButton orderTypeMenuButton;
    @FXML public MenuItem buyMenuItem;
    @FXML public MenuItem sellMenuItem;
    @FXML
    private Label orderStatusLabel;
    @FXML
    private TextField Quantity;
    @FXML
    private TextField Price;
    private boolean executed;
    @FXML
    public void initialize() {
        ObservableList<String> stocklabel;
        if (!DataManager.companyList.isEmpty()) {
            stocklabel = FXCollections.observableArrayList(DataManager.companyList.stream().map(Company::getLabel).toList());
            stockChoiceBox.setItems(stocklabel);
        } else {
            stockChoiceBox.setItems(FXCollections.observableArrayList("No companies available"));
        }
    }
    @FXML
    private void handleBuyMenuItemAction(ActionEvent event) throws IOException {
        String stockLabel = stockChoiceBox.getValue();
        int quantity = Integer.parseInt(Quantity.getText());
        double limitPrice = Double.parseDouble(Price.getText());
        currentUser.addOrder(stockLabel, quantity, "LimitBuy", limitPrice);
        if (currentUser.order.executed) {
            orderStatusLabel.setText("Limit buy order executed!");
            admin.updateBalance();
        } else {
            orderStatusLabel.setText("Limit buy order pending.");
        }
    }
    @FXML
    private void handleSellMenuItemAction(ActionEvent event) throws IOException {
        String stockLabel = stockChoiceBox.getValue();
        int quantity = Integer.parseInt(quantityField.getText());
        double limitPrice = Double.parseDouble(limitPriceField.getText());

        currentUser.addOrder(stockLabel, quantity, "LimitSell", limitPrice);
        if (currentUser.order.executed) {
            orderStatusLabel.setText("Limit buy order executed!");
            admin.updateBalance();
        } else {
            orderStatusLabel.setText("Limit buy order pending.");
        }
    }
    public void back2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/manageOrder.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();


    }
}


