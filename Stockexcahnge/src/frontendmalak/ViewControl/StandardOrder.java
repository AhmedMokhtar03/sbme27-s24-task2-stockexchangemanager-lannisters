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

import static frontendmalak.HelloApplication.primaryStage;

public class StandardOrder {
    private User currentUser = LogIn.currentUser;
    private Company company;
    private String selectedStock;
    private int quantity;
    AdminManageRequestsController admin = new AdminManageRequestsController(currentUser);
    @FXML private ChoiceBox<String> stockchoicebox;
    double currentPrice;
    @FXML
    public Label currentPriceLabel;
    @FXML public Label EstimatedTotalCostLabel;
    @FXML public Label EstimatedTotalProceedsLabel;
    @FXML public Button BuyButton;
    @FXML public Button SellButton;
    @FXML
    public Label stockLabelField;
    @FXML
    public Label quantityField;
    @FXML
    public TextField quantity1;
    @FXML
    public void initialize() {
        ObservableList<String> stocklabel;
        if (!DataManager.companyList.isEmpty()) {
            stocklabel = FXCollections.observableArrayList(DataManager.companyList.stream().map(Company::getLabel).toList());
            stockchoicebox.setItems(stocklabel);
        } else {
            stockchoicebox.setItems(FXCollections.observableArrayList("No companies available"));
        }
        currentPriceLabel.setText("Current Price:"+ currentPrice );
        EstimatedTotalCostLabel.setText("Estimated Cost: 0 ");
        EstimatedTotalProceedsLabel.setText("Estimated Proceeds: 0");
        stockchoicebox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateEstimates()
        );
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> updateEstimates());
    }
    @FXML
    private void handleBuyButtonAction(ActionEvent event) throws IOException {
        updateEstimates();
        currentUser.addOrder(selectedStock, quantity, String.valueOf(BuyButton), currentPrice);
        DataManager.saveUsersToCSV();
    }

    @FXML
    private void handleSellButtonAction(ActionEvent event) throws IOException {
        updateEstimates();
        currentUser.addOrder(selectedStock, quantity, String.valueOf(SellButton), currentPrice);
        DataManager.saveUsersToCSV();
    }

    private void updateEstimates() {
        selectedStock = stockchoicebox.getValue();
        String quantityText = quantity1.getText();
        if (selectedStock != null && !quantityText.isEmpty()) {
            try {
                quantity = Integer.parseInt(quantityText);
                currentPrice = getCurrentPrice(selectedStock);
                currentPriceLabel.setText(String.format("Current Price: %.2f", currentPrice));
                    double estimatedCost = quantity * currentPrice;
                    EstimatedTotalCostLabel.setText(String.format("Estimated Cost: %.2f", estimatedCost));
                    EstimatedTotalProceedsLabel.setText("Estimated Proceeds: N/A");
                    double estimatedProceeds = quantity * currentPrice;
                    EstimatedTotalCostLabel.setText(String.format("Estimated Proceeds: %.2f", estimatedProceeds));
                    EstimatedTotalProceedsLabel.setText("Estimated Cost: N/A");

            } catch (NumberFormatException ex) {
                // Handle invalid input (e.g., display error message)
                System.err.println("Invalid quantity: " + quantityText);
            }
        }
    }
    private double getCurrentPrice(String stockSymbol) {
        for(Company com: DataManager.companyList){
            if(com.getLabel().equals(stockSymbol)){
                company = com;
            }
        }
        return company.getStockPrice();
    }

    public void back2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/manageOrder.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
