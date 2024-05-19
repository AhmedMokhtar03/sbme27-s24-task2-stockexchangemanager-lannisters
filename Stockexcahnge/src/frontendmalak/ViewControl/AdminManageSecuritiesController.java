package frontendmalak.ViewControl;

import backend.Company;
import backend.DataManager;
import backend.Securities;
import backend.User;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import static backend.DataManager.companyList;
import static frontendmalak.ViewControl.AdminManageUsersController.userList;

public class AdminManageSecuritiesController {
    @FXML
    private TextField companyNameField;
    @FXML
    private TextField companyLabelField;
    @FXML
    private TextField stockPriceField;
    @FXML
    private TextField dividendsField;
    @FXML
    private TextField numOfAvailableStocksField;

    @FXML
    private TableView<Company> tableView;
    @FXML
    private TableColumn<Securities, String> companyNameColumn;
    @FXML
    private TableColumn<Securities, String> companyLabelColumn;
    @FXML
    private TableColumn<Securities, Double> stockPriceColumn;
    @FXML
    private TableColumn<Securities, Double> dividendsColumn;
    @FXML
    private TableColumn<Securities, Integer> numOfAvailableStocksColumn;


    @FXML
    private JFXButton closeAppBTN;
    @FXML
    private JFXButton goBackBTN;
    @FXML
    private JFXButton openMenuBTN;
    @FXML
    private JFXButton closeMenuBTN;

    @FXML
    private AnchorPane menuPane;
    @FXML
    private AnchorPane tablePane;


    @FXML
    public void initialize() {
        companyList.clear();
        DataManager.loadCompanies();
        tableView.setItems(companyList);

        // Initialize all TableColumn cell value factories
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        companyLabelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));
        stockPriceColumn.setCellValueFactory(new PropertyValueFactory<>("stockPrice"));
        dividendsColumn.setCellValueFactory(new PropertyValueFactory<>("dividends"));
        numOfAvailableStocksColumn.setCellValueFactory(new PropertyValueFactory<>("numOfAvailableStocks"));
    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/AdminHomePage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void openMenu(ActionEvent event) {
        menuOpen(menuPane, tablePane, openMenuBTN, closeMenuBTN);
    }

    static void menuOpen(AnchorPane menuPane, AnchorPane tablePane, JFXButton openMenuBTN, JFXButton closeMenuBTN) {
        TranslateTransition slide1 = new TranslateTransition(Duration.millis(500), menuPane);
        slide1.setToX(0);
        slide1.play();

        TranslateTransition slide2 = new TranslateTransition(Duration.millis(500), tablePane);
        slide2.setToX(200);
        slide2.play();

        slide1.setOnFinished((ActionEvent e) -> {
            openMenuBTN.setVisible(false);
            closeMenuBTN.setVisible(true);
        });
    }

    @FXML
    void closeMenu(ActionEvent event) {
        menuClose(menuPane, tablePane, openMenuBTN, closeMenuBTN);
    }

    static void menuClose(AnchorPane menuPane, AnchorPane tablePane, JFXButton openMenuBTN, JFXButton closeMenuBTN) {
        TranslateTransition slide1 = new TranslateTransition(Duration.millis(500), menuPane);
        slide1.setToX(-200);
        slide1.play();
        TranslateTransition slide2 = new TranslateTransition(Duration.millis(500), tablePane);
        slide2.setToX(0);
        slide2.play();

        slide1.setOnFinished((ActionEvent e) -> {
            openMenuBTN.setVisible(true);
            closeMenuBTN.setVisible(false);
        });
    }


    private void clearFields() {
        companyNameField.clear();
        companyLabelField.clear();
        stockPriceField.clear();
        dividendsField.clear();
        numOfAvailableStocksField.clear();
    }


    @FXML
    public void createCompany() {
        String companyName = companyNameField.getText();
        String companyLabel = companyLabelField.getText();
        double stockPrice = Double.parseDouble(stockPriceField.getText());
        double dividends = Double.parseDouble(dividendsField.getText());
        int numOfAvailableStocks = Integer.parseInt(numOfAvailableStocksField.getText());
        DataManager.loadUsersFromCSV();
        Company newCompany = new Company(companyName, companyLabel, 5, stockPrice, dividends, numOfAvailableStocks);
        companyList.add(newCompany);
        DataManager.saveCompanies(newCompany);
        DataManager.saveUsersToCSV();
        System.out.println("New Company created: " + newCompany.getName());

        clearFields();
    }


}