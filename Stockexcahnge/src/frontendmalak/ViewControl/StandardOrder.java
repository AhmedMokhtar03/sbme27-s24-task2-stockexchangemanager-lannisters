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

public class StandardOrder {
    @FXML private ChoiceBox<String> stockchoicebox;
    double currentPrice;
    @FXML
    public Label currentPriceLabel;
    @FXML public Label EstimatedTotalCostLabel;
    @FXML public Label EstimatedTotalProceedsLabel;
    @FXML private MenuButton orderTypeMenuButton;
    @FXML public MenuItem buyMenuItem;
    @FXML public MenuItem sellMenuItem;
    @FXML
    public Label stockLabelField;
    @FXML
    public Label quantityField;
    private User currentUser;
    @FXML
    public void initialize() {
        ObservableList<String> stocklabel = FXCollections.observableArrayList("AAPL", "MSFT", "AMZN");
        stockchoicebox.setItems(stocklabel);
        stockchoicebox.setValue("AAPL");
        currentPriceLabel.setText("Current Price:"+ currentPrice );
        EstimatedTotalCostLabel.setText("Estimated Cost: 0 ");
        EstimatedTotalProceedsLabel.setText("Estimated Proceeds: 0");
        stockchoicebox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateEstimates()
        );
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> updateEstimates());

    }
    @FXML
    private void handleBuyMenuItemAction(ActionEvent event) throws IOException {
        orderTypeMenuButton.setText("Buy");
        updateEstimates();
    }

    @FXML
    private void handleSellMenuItemAction(ActionEvent event) throws IOException {
        orderTypeMenuButton.setText("Sell");
        updateEstimates();
    }

    private void updateEstimates() {
        String selectedStock = stockchoicebox.getValue();
        String quantityText = quantityField.getText();

        if (selectedStock != null && !quantityText.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityText);
                currentPrice = getCurrentPrice(selectedStock);
                currentPriceLabel.setText(String.format("Current Price: %.2f", currentPrice));

                if (orderTypeMenuButton.getText().equals("Buy")) {
                    double estimatedCost = quantity * currentPrice;
                    EstimatedTotalCostLabel.setText(String.format("Estimated Cost: %.2f", estimatedCost));
                    EstimatedTotalProceedsLabel.setText("Estimated Proceeds: N/A");
                } else if (orderTypeMenuButton.getText().equals("Sell")) {
                    double estimatedProceeds = quantity * currentPrice;
                    EstimatedTotalCostLabel.setText(String.format("Estimated Proceeds: %.2f", estimatedProceeds));
                    EstimatedTotalProceedsLabel.setText("Estimated Cost: N/A");
                }
            } catch (NumberFormatException ex) {
                // Handle invalid input (e.g., display error message)
                System.err.println("Invalid quantity: " + quantityText);
            }
        }
    }


    private double getCurrentPrice(String stockSymbol) {
        // Replace this with your actual implementation to fetch price
        // You might use a financial data API, web scraping, etc.
        return 100.0; // Placeholder value
    }
    @FXML
    private void handleBuyButtonAction() {

        orderTypeMenuButton.setText("Buy");
        updateEstimates();

        //   private void buy(String label, int quantity, int id) {
    }
    public void back2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/manageOrder.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stg.setScene(scene);
        stg.show();


    }
}
