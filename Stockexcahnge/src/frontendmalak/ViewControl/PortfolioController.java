package frontendmalak.ViewControl;

import backend.Company;
import backend.DataManager;
import backend.Transactions;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static frontendmalak.HelloApplication.primaryStage;
import static frontendmalak.ViewControl.AdminManageUsersController.userList;
import static frontendmalak.ViewControl.UserTransactions.TransactionsList;

import java.io.IOException;
import java.time.LocalDate;

public class PortfolioController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private TableView<Company> holdingsTable;

    @FXML
    private TableColumn<Company, String> holdingsStockNameColumn;

    @FXML
    private TableColumn<Company, Double> holdingsQuantityColumn;

    @FXML
    private TableColumn<Company, Double> holdingsPriceColumn;

    @FXML
    private TableColumn<Company, Double> holdingsValueColumn;

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






    }

void lsl(){
        for(Company i:DataManager.companyList) {

            if(LogIn.currentUser.ownedStocks.get(i.getLabel())!=null)
           System.out.println(i.getLabel() +""+LogIn.currentUser.ownedStocks.get(i.getLabel()));


        }
}



    @FXML
    void goBack(ActionEvent event) throws IOException {
        lsl();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
