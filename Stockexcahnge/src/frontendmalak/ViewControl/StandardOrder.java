package frontendmalak.ViewControl;
import backend.Company;
import backend.CompanyController;
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
    @FXML private MenuButton orderTypeMenuButton;
    @FXML public MenuItem buyMenuItem;
    @FXML public MenuItem sellMenuItem;
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
    private void handleBuyMenuItemAction(ActionEvent event) throws IOException {
        orderTypeMenuButton.setText("Buy");
        updateEstimates();
        currentUser.addOrder(selectedStock, quantity, "BUY", currentPrice);
        DataManager.saveUsersToCSV();
    }

    @FXML
    private void handleSellMenuItemAction(ActionEvent event) throws IOException {
        orderTypeMenuButton.setText("Sell");
        updateEstimates();
        currentUser.addOrder(selectedStock, quantity, "SELL", currentPrice);
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
        stg.setScene(scene);
        stg.show();


    }
}
