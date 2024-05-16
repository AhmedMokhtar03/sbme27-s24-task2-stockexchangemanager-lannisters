package frontendmalak.ViewControl;

import backend.Company;
import backend.DataManager;
import backend.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.List;
import java.util.Map;

public class Selectcompany {
    private User currentUser = LogIn.currentUser;
    private Map<String, List<String>> subscriptions;

    @FXML
    private ChoiceBox<String> aapl;
    @FXML
    private ChoiceBox<String> amzn;

    public void initialize() {
        subscriptions = DataManager.loadSubscriptions();
        ObservableList<String> select = FXCollections.observableArrayList("subscribe", "unsubscribe");
        aapl.setItems(select);
        aapl.setValue(null);
        aapl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleSubscription("app", newValue);
        });

        amzn.setItems(select);
        amzn.setValue("subscribe");
        amzn.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleSubscription("amzn", newValue);
        });

        loadSubscriptions();
    }

    private void handleSubscription(String companyLabel, String action) {
        List<String> subscribedCompanyLabels = subscriptions.getOrDefault(currentUser.getUserName(), null);
        if (subscribedCompanyLabels == null) {
            subscribedCompanyLabels = new java.util.ArrayList<>();
        }
        if (action.equals("subscribe")) {
            subscribedCompanyLabels.add(companyLabel);
            for (Company company : DataManager.companyList) {
                if (company.getLabel().equalsIgnoreCase(companyLabel)) {
                    company.addObserver(currentUser);
                }
            }
        } else if (action.equals("unsubscribe")) {
            subscribedCompanyLabels.remove(companyLabel);
            for (Company company : DataManager.companyList) {
                if (company.getLabel().equalsIgnoreCase(companyLabel)) {
                    company.deleteObserver(currentUser);
                }
            }
        }
        subscriptions.put(currentUser.getUserName(), subscribedCompanyLabels);
        DataManager.saveSubscriptions(subscriptions);
    }

    private void loadSubscriptions() {
        List<String> subscribedCompanyLabels = subscriptions.get(currentUser.getUserName());
        if (subscribedCompanyLabels != null) {
            for (String companyLabel : subscribedCompanyLabels) {
                for (Company company : DataManager.companyList) {
                    if (company.getLabel().equalsIgnoreCase(companyLabel)) {
                        company.addObserver(currentUser);
                    }
                }
            }
        }
    }
}
