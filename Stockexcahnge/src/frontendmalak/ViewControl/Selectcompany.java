package frontendmalak.ViewControl;
import backend.Company;
import java.util.Observable;
import java.util.Observer;
import backend.DataManager;
import backend.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import org.reactfx.collection.LiveList;

public class Selectcompany {
    private User currentUser = LogIn.currentUser;
    @FXML
    private ChoiceBox<String> aapl;
    @FXML
    private ChoiceBox<String> amzn;
    public void initialize() {

        ObservableList<String> select = FXCollections.observableArrayList("subscribe", "unsubscribe");
        aapl.setItems(select);
        //aapl.setValue("subscribe");
        if(aapl.getValue().equals("subscribe")) {
            for(Company company : DataManager.companyList){
                if(company.getLabel().equalsIgnoreCase("aap")){
                    company.addObserver(currentUser);
                }
            }}else if(aapl.getValue().equals("unsubscribe")) {
                for(Company company : DataManager.companyList){
                    if(company.getLabel().equalsIgnoreCase("app")){
                        company.deleteObserver(currentUser);
                    }
                }
            }
        ObservableList<String> select1 = FXCollections.observableArrayList("subscribe", "unsubscribe");
        amzn.setItems(select);
        if(amzn.getValue().equals("subscribe")) {
            for(Company company : DataManager.companyList){
                if(company.getLabel().equalsIgnoreCase("amzn")){
                    company.addObserver(currentUser);
                }
            }}else if(amzn.getValue().equals("unsubscribe")) {
            for(Company company : DataManager.companyList){
                if(company.getLabel().equalsIgnoreCase("amzn")){
                    company.deleteObserver(currentUser);
                }
            }
        }
}
}
