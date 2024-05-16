package frontendmalak.ViewControl;

import backend.Company;
import backend.DataManager;
import backend.Securities;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import static backend.DataManager.companyList;

public class AdminManageSecurtiesController {
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

    //public static ObservableList<Company> companyList = FXCollections.observableArrayList();

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
    public void createCompany() {
        String companyName = companyNameField.getText();
        String companyLabel = companyLabelField.getText();
        double stockPrice = Double.parseDouble(stockPriceField.getText());
        double dividends = Double.parseDouble(dividendsField.getText());
        int numOfAvailableStocks = Integer.parseInt(numOfAvailableStocksField.getText());

        Company newCompany = new Company(companyName, companyLabel,5, stockPrice, dividends, numOfAvailableStocks);
        companyList.add(newCompany);
        DataManager.saveCompanies(newCompany);
        System.out.println("New Company created: " + newCompany.getName());

    }


}