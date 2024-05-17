package frontendmalak.ViewControl;

import backend.Company;
import backend.DataManager;
import backend.Transactions;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static frontendmalak.HelloApplication.primaryStage;
import static frontendmalak.ViewControl.AdminManageUsersController.userList;
import static frontendmalak.ViewControl.UserTransactions.TransactionsList;

import java.io.IOException;
import java.time.LocalDate;

public class PortfolioController {
    /////////////////////
    @FXML
    private JFXButton closeAppBTN;

    @FXML
    private JFXButton closeHistoryBTN;
    @FXML
    private JFXButton goBackBTN;

    @FXML
    private AnchorPane historyPane;

    @FXML
    private AnchorPane holdingsPane;

    @FXML
    private JFXButton openHistoryBTN;

    @FXML
    private Label profitLabel;
    ////////////////////
    @FXML
    private Label nameLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private TableView<Company> holdingsTable;

    @FXML
    private TableColumn<Company, String> holdingsStockNameColumn;

    @FXML
    private TableColumn<Company, Integer> holdingsQuantityColumn;

    @FXML
    private TableColumn<Company, Double> holdingsPriceColumn;

    @FXML
    private TableColumn<Company, Double> holdingsValueColumn;
    @FXML
    private TableColumn<Company,Double> dividendsColumn;

    @FXML
    private TableView<Transactions> transactionTable;

    @FXML
    private TableColumn<Transactions, String> transactionTypeColumn;

    @FXML
    private TableColumn<Transactions, Double> transactionAmountColumn;

    @FXML
    private TableColumn<Transactions, LocalDate> transactionDateColumn;

    @FXML
    private TableColumn<Transactions, String> statusColumn;

    private ObservableList<Company> holdingsList = FXCollections.observableArrayList();
    Company tempholding;
    public void initialize() {

        DataManager.loadTransactionsFromCSV();
        nameLabel.setText(LogIn.currentUser.getUserName());


        Double text = LogIn.currentUser.getCashBalance();
        balanceLabel.setText(String.valueOf(text));

        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("typeOfTransaction"));
        transactionAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
//fill the table with filterlist (important note that fliterlist cant do the usual opretions on it like we do on arraylist or obsevablelist)
        FilteredList<Transactions> filteredTransactions = new FilteredList<>(TransactionsList, transaction ->
                transaction.getUsername().equals(LogIn.currentUser.getUserName())
        );
        transactionTable.setItems(filteredTransactions);
        //========================================
//still need to fill the table with the data of the owned stocks
        for(Company i:DataManager.companyList) {

            if(LogIn.currentUser.ownedStocks.get(i.getLabel())!=null) {
                String label= i.getLabel();
                int quantity=LogIn.currentUser.ownedStocks.get(i.getLabel());
                double price=i.getStockPrice();
                double dividends=i.getDividends();
                double totalPrice=quantity * price;
                tempholding=new Company(label,quantity ,price,totalPrice,dividends);
                holdingsList.add(tempholding);
            }

        }
loadTable();
    }


    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }


    @FXML
    void openHistory(ActionEvent event) {
        TranslateTransition slide1 = new TranslateTransition(Duration.millis(500), historyPane);
        slide1.setToX(0);
        slide1.play();

        TranslateTransition slide2 = new TranslateTransition(Duration.millis(500), holdingsPane);
        slide2.setToX(620);
        slide2.play();

        slide1.setOnFinished((ActionEvent e) -> {
            openHistoryBTN.setVisible(false);
            closeHistoryBTN.setVisible(true);
        });

    }

    @FXML
    void closeHistory(ActionEvent event) {
        TranslateTransition slide1 = new TranslateTransition(Duration.millis(500), historyPane);
        slide1.setToX(-620);
        slide1.play();

        TranslateTransition slide2 = new TranslateTransition(Duration.millis(500), holdingsPane);
        slide2.setToX(0);
        slide2.play();


        slide1.setOnFinished((ActionEvent e) -> {
            openHistoryBTN.setVisible(true);
            closeHistoryBTN.setVisible(false);
        });

    }

    void loadTable(){
    holdingsStockNameColumn.setCellValueFactory(new PropertyValueFactory<>("label"));
    holdingsQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    holdingsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("stockPrice"));
    holdingsValueColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    dividendsColumn.setCellValueFactory(new PropertyValueFactory<>("dividends"));

    holdingsTable.setItems(holdingsList);

}

}
