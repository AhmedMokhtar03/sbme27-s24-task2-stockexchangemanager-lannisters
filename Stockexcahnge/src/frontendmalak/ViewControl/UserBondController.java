package frontendmalak.ViewControl;

import backend.Bonds;
import backend.DataManager;
import backend.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import static frontendmalak.ViewControl.AdminManageUsersController.userList;
import static frontendmalak.ViewControl.AdminManageBondsController.BondList;
import static frontendmalak.ViewControl.AdminManageBondsController.loadBondsListFromCSV;
public class UserBondController {

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
    private TableColumn<Bonds, Integer> ownedquantityColumn;
    @FXML
    private TextField quantityTextField;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Label maturityDateLabel;
    @FXML
    private Label returnedCashLabel;

    double totalCost;
    LocalDate maturityDate;
    double returnedCash;
    boolean canBuy  ;

    public static ObservableList<Bonds> OwnedBonds = FXCollections.observableArrayList();
    public static final String OwnedBondsCSV = "Stockexcahnge/src/frontendmalak/OwnedBonds.csv";

    @FXML
    private void initialize() {
        AdminManageBondsController.loadBondsListFromCSV();
        loadOwnedBondsFromCSV();
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        bondPriceColumn.setCellValueFactory(new PropertyValueFactory<>("bondPrice"));
        bondInterestColumn.setCellValueFactory(new PropertyValueFactory<>("bondInterest"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ownedquantityColumn.setCellValueFactory(cellData -> {
            Bonds bond = cellData.getValue();
            int ownedQuantity = calculateOwnedQuantity(bond.getCompany());
            return new SimpleIntegerProperty(ownedQuantity).asObject();
        });
        bondTableView.setItems(BondList);
    }

    @FXML
    private void buyBonds() throws IOException {
        Bonds selectedBond = bondTableView.getSelectionModel().getSelectedItem();
        if (selectedBond != null) {
            int quantity = Integer.parseInt(quantityTextField.getText());

             totalCost = quantity * selectedBond.getBondPrice();
             maturityDate = LocalDate.now().plus(selectedBond.getDuration());
             returnedCash = totalCost + (totalCost * selectedBond.getBondInterest()/100);
             if (LogIn.currentUser.getCashBalance()>totalCost){
                 canBuy =true;}
            if (LogIn.currentUser.getCashBalance()<totalCost){
                     canBuy = false;

             }

            int newQuantity=selectedBond.getQuantity() - quantity;
            if (newQuantity>=0 && quantity>0 && canBuy){

                selectedBond.setQuantity(newQuantity);
                if(newQuantity==0){
                    BondList.remove(selectedBond);
                    bondTableView.getItems().remove(selectedBond);
                }
                showLabel();

                for (User temmpuser:userList){
                  if (temmpuser.getUserName()==LogIn.currentUser.getUserName())
                  {
                      temmpuser.setCashBalance(temmpuser.getCashBalance()-totalCost);
                      DataManager.saveUsersToCSV();
                      break;
                  }


                }

                Bonds userbond;
                userbond=new Bonds(LogIn.currentUser.getUserName(),selectedBond.getCompany(),quantity,maturityDate,returnedCash);
                OwnedBonds.add(userbond);


            }
            if (!canBuy&&newQuantity>=0){
                showAlert("Error", "not enough money.");
            }
            if (newQuantity<0){
                showAlert("Error", "not enough bonds in the market.");
            }
            if(quantity<=0){
                showAlert("Error", "quantity must be a positive number.");



            }

            bondTableView.refresh();
            AdminManageBondsController.saveBondsListToCSV();
            saveOwnedBondsToCSV();
        }
        else
            showAlert("Error", "Choose a bond first .");
    }


    public static void saveOwnedBondsToCSV() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OwnedBondsCSV))) {
            for (Bonds bond : OwnedBonds) {

                writer.write(bond.getUserName() + "," +
                        bond.getCompany() + "," +
                        bond.getNumberOfOwnedBonds() + "," +
                        bond.getMaturityDate() + "," +
                        bond.getTotalReturnedMoney());
                writer.newLine();


            }
        }
    }



    public static void loadOwnedBondsFromCSV() {
        OwnedBonds.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(OwnedBondsCSV))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 5) {
                    System.err.println("Invalid format in CSV line: " + line);
                    continue; // Skip this line and move to the next one
                }

                try {
                    Bonds bond = new Bonds(parts[0], parts[1], Integer.parseInt(parts[2]), LocalDate.parse(parts[3]), Double.parseDouble(parts[4]));
                    OwnedBonds.add(bond);
                } catch (NumberFormatException | DateTimeParseException e) {
                    System.err.println("Error parsing CSV line: " + line);
                    e.printStackTrace(); // Print the stack trace for debugging
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int calculateOwnedQuantity(String company) {
        int ownedQuantity = 0;
        for (Bonds bond : OwnedBonds) {
            if (bond.getUserName().equals(LogIn.currentUser.getUserName()) && bond.getCompany().equals(company)) {
                ownedQuantity += bond.getNumberOfOwnedBonds();
            }
        }
        return ownedQuantity;
    }
public void showLabel(){

    totalCostLabel.setText("Total Cost: " + totalCost);
    maturityDateLabel.setText("Maturity Date: " + maturityDate);
    returnedCashLabel.setText("Returned Cash: " + returnedCash);


}


    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
    public void back2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/general.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
