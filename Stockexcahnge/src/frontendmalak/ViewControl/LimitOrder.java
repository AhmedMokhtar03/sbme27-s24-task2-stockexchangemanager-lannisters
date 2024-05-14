package frontendmalak.ViewControl;

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

    // You'll likely need a reference to the current user
    private User currentUser;
    private boolean executed;
    @FXML
    public void initialize() {
        ObservableList<String> stocklabel = FXCollections.observableArrayList("AAPL", "MSFT", "AMZN");
        stockChoiceBox.setItems(stocklabel);
        stockChoiceBox.setValue("AAPL");}

    @FXML
    private void handleBuyMenuItemAction(ActionEvent event) {
        String stockLabel = stockChoiceBox.getValue();
        int quantity = Integer.parseInt(quantityField.getText());
        double limitPrice = Double.parseDouble(limitPriceField.getText());

        LimitOrder limitOrder = new LimitOrder();
        limitOrder.buy(stockLabel, quantity, currentUser.getID());

        if (limitOrder.executed) {
            orderStatusLabel.setText("Limit buy order executed!");
        } else {
            orderStatusLabel.setText("Limit buy order pending.");
        }
    }

    private void buy(String stockLabel, int quantity, int id) {
    }

    @FXML
    private void handleSellMenuItemAction(ActionEvent event) {
        String stockLabel = stockChoiceBox.getValue();
        int quantity = Integer.parseInt(quantityField.getText());
        double limitPrice = Double.parseDouble(limitPriceField.getText());

        LimitOrder limitOrder = new LimitOrder();
        limitOrder.sell(stockLabel, quantity, currentUser.getID());

        if (limitOrder.executed) {
            orderStatusLabel.setText("Limit sell order executed!");
        } else {
            orderStatusLabel.setText("Limit sell order pending.");
        }
    }

    private void sell(String stockLabel, int quantity, int id) {
    }

    // Method to set the current user
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    public void back2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/manageOrder.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();


    }
}


