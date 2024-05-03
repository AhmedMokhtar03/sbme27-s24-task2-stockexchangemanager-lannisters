import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;


public class CompanyController {

    @FXML
    private TextField companyNameField;

    @FXML
    private TextField companyLabelField;

    @FXML
    private TextField companyIDField;

    @FXML
    private TextField stockPriceField;

    @FXML
    private TextField dividendsField;

    @FXML
    private TextField numOfAvailableStocksField;

    public static List<Company> companyList = new ArrayList<>();
    public static List<Securities> Stocks = new ArrayList<>();

    public void createCompany() {
        String companyName = companyNameField.getText();
        String companyLabel = companyLabelField.getText();
        int companyID = Integer.parseInt(companyIDField.getText());
        double stockPrice = Double.parseDouble(stockPriceField.getText());
        double dividends = Double.parseDouble(dividendsField.getText());
        int numOfAvailableStocks = Integer.parseInt(numOfAvailableStocksField.getText());

        Company newCompany = new Company(companyName, companyLabel, companyID, stockPrice, dividends, numOfAvailableStocks);
        Stocks.add(SecurityFactory.createSecurity("stock", newCompany.getLabel(), newCompany.getNumOfAvailableStocks(), "new"));
        companyList.add(newCompany);

        System.out.println("New Company created: " + newCompany.getName() );
        printAllCompanies();
        printAllStocks();
    }

    private void printAllCompanies() {
        System.out.println("All Companies:");
        for (Company company : companyList) {
            System.out.println(companyList);
        }
    }
    private void printAllStocks() {
        System.out.println("All Stocks:");
        for (Securities stock : Stocks) {
            System.out.println(stock);
        }
    }
}