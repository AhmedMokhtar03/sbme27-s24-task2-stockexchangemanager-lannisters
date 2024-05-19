package frontendmalak.ViewControl;

import backend.Bonds;
import backend.DataManager;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Period;

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
    private TableColumn<Bonds, Integer> quantityColumn;

    @FXML
    private TextField companyTextField;
    @FXML
    private TextField bondPriceTextField;
    @FXML
    private TextField bondInterestTextField;
    @FXML
    private TextField durationTextField;
    @FXML
    private TextField quantityTextField;

    @FXML
    private JFXButton openMenuBTN;
    @FXML
    private JFXButton closeMenuBTN;

    @FXML
    private AnchorPane menuPane;
    @FXML
    private AnchorPane tablePane;

    public static ObservableList<Bonds> BondList = FXCollections.observableArrayList();
    public static final String BONDS_CSV = "Stockexcahnge/src/frontendmalak/bonds.csv";
    public void initialize() {
        BondList.clear();
        loadBondsListFromCSV();
        bondTableView.setItems(BondList);
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        bondPriceColumn.setCellValueFactory(new PropertyValueFactory<>("bondPrice"));
        bondInterestColumn.setCellValueFactory(new PropertyValueFactory<>("bondInterest"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
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


    public void addBond() {
        try {
            //there is a certain format to type durtion
            //PaYbMcD  where a is numer of years and b is number of months and c is numer of days
            String company = companyTextField.getText();
            double bondPrice = Double.parseDouble(bondPriceTextField.getText());
            double bondInterest = Double.parseDouble(bondInterestTextField.getText());
            Period duration = Period.parse(durationTextField.getText());
            int quantity = Integer.parseInt(quantityTextField.getText());

            Bonds bond = new Bonds(company, bondPrice, bondInterest, duration, quantity);
            BondList.add(bond);

            // Clear the text fields after adding the bond
            companyTextField.clear();
            bondPriceTextField.clear();
            bondInterestTextField.clear();
            durationTextField.clear();
            quantityTextField.clear();

            saveBondsListToCSV();
        } catch (Exception e) {
            // Handle parsing errors or invalid inputs
            e.printStackTrace();
            // Optionally, show an error message to the user
        }
    }

    public static void saveBondsListToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BONDS_CSV))) {
            for (Bonds bond : BondList) {
                writer.write(bond.getCompany() + "," +
                        bond.getBondPrice() + "," +
                        bond.getBondInterest() + "," +
                        bond.getDuration() + "," +
                        bond.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadBondsListFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BONDS_CSV))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String company = fields[0];
                double bondPrice = Double.parseDouble(fields[1]);
                double bondInterest = Double.parseDouble(fields[2]);
                Period duration = Period.parse(fields[3]);
                int quantity = Integer.parseInt(fields[4]);

                Bonds bond = new Bonds(company, bondPrice, bondInterest, duration, quantity);
                BondList.add(bond);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
