package frontendmalak.ViewControl;

import backend.Bonds;
import backend.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class AdminManageBondsController {

    @FXML
    private TableView<Bonds> bondTableView;
    @FXML
    private TableColumn<Bonds, String> companyColumn;
    @FXML
    private TableColumn<Bonds, Double> bondPriceColumn;
    @FXML
    private TableColumn<Bonds, Double> bondInterestColumn;
    @FXML
    private TableColumn<Bonds, Period> durationColumn;
    @FXML
    private TableColumn<Bonds, LocalDate> dateColumn;

    @FXML
    private TextField companyTextField;
    @FXML
    private TextField bondPriceTextField;
    @FXML
    private TextField bondInterestTextField;
    @FXML
    private TextField durationTextField;

    private ObservableList<Bonds> BondList = FXCollections.observableArrayList();
    private static final String bondsCSV = "Stockexcahnge/src/frontendmalak/bonds.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void initialize() {
        loadBondsListFromCSV();
        bondTableView.setItems(BondList);
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        bondPriceColumn.setCellValueFactory(new PropertyValueFactory<>("bondPrice"));
        bondInterestColumn.setCellValueFactory(new PropertyValueFactory<>("bondInterest"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    public void addBond() {
        try {
            //there is a certain format to type durtion
            //PaYbMcD  where a is numer of years and b is number of months and c is numer of days
            String company = companyTextField.getText();
            double bondPrice = Double.parseDouble(bondPriceTextField.getText());
            double bondInterest = Double.parseDouble(bondInterestTextField.getText());
            Period duration = Period.parse(durationTextField.getText());

            Bonds bond = new Bonds(company, bondPrice, bondInterest, duration);
            bond.setDate(LocalDate.now().plus(duration)); // Set the date based on the current date plus the duration
            BondList.add(bond);

            // Clear the text fields after adding the bond
            companyTextField.clear();
            bondPriceTextField.clear();
            bondInterestTextField.clear();
            durationTextField.clear();

            saveBondsListToCSV();
        } catch (Exception e) {
            // Handle parsing errors or invalid inputs
            e.printStackTrace();
            // Optionally, show an error message to the user
        }
    }

    public void saveBondsListToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bondsCSV))) {
            for (Bonds bond : BondList) {
                writer.write(bond.getCompany() + "," +
                        bond.getBondPrice() + "," +
                        bond.getBondInterest() + "," +
                        bond.getDuration() + "," +
                        bond.getDate().format(DATE_FORMATTER));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBondsListFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(bondsCSV))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String company = fields[0];
                double bondPrice = Double.parseDouble(fields[1]);
                double bondInterest = Double.parseDouble(fields[2]);
                Period duration = Period.parse(fields[3]);
                LocalDate date = LocalDate.parse(fields[4], DATE_FORMATTER);

                Bonds bond = new Bonds(company, bondPrice, bondInterest, duration);
                bond.setDate(date);
                BondList.add(bond);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
