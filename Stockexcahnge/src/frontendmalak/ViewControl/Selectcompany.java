package frontendmalak.ViewControl;

import backend.Company;
import backend.DataManager;
import backend.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static frontendmalak.HelloApplication.primaryStage;

public class Selectcompany {
    private User currentUser = LogIn.currentUser;
    private Map<String, List<String>> subscriptions;

    @FXML
    private ListView<Company> companyListView;
@FXML
private Button back;
    @FXML
    private Text titleLabel; // Change Label to Text

    public void initialize() {
        subscriptions = DataManager.loadSubscriptions();

        ObservableList<Company> companies = FXCollections.observableArrayList(DataManager.companyList);
        companyListView.setItems(companies);

        companyListView.getStyleClass().add("list-view");

        companyListView.setCellFactory(new Callback<ListView<Company>, ListCell<Company>>() {
            @Override
            public ListCell<Company> call(ListView<Company> listView) {
                return new CompanyCell();
            }
        });

        // Apply neon CSS style to the title label
        titleLabel.getStyleClass().add("neon-label");

        loadSubscriptions();
    }

    public void Backk(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    private class CompanyCell extends ListCell<Company> {
        private HBox hbox;
        private Text companyNameText;
        private ChoiceBox<String> choiceBox;

        public CompanyCell() {
            hbox = new HBox();
            hbox.setSpacing(10);
            hbox.setStyle("-fx-padding: 10;");

            companyNameText = new Text();
            companyNameText.getStyleClass().addAll("text-company-name","shining-label"); // Add neon-label style class

            choiceBox = new ChoiceBox<>();
            choiceBox.getStyleClass().add("choice-box-subscribe");
            ObservableList<String> options = FXCollections.observableArrayList("subscribe", "unsubscribe");
            choiceBox.setItems(options);

            choiceBox.setValue(null);
            choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && getItem() != null) {
                    handleSubscription(getItem().getLabel(), newValue);
                }
            });

            HBox.setHgrow(companyNameText, Priority.ALWAYS);
            hbox.getChildren().addAll(companyNameText, choiceBox);
        }

        @Override
        protected void updateItem(Company company, boolean empty) {
            super.updateItem(company, empty);
            if (empty || company == null) {
                setText(null);
                setGraphic(null);
            } else {
                companyNameText.setText(company.getName());
                choiceBox.setValue(getSubscriptionStatus(company.getLabel()));
                setGraphic(hbox);
            }
        }

        private String getSubscriptionStatus(String companyLabel) {
            List<String> subscribedCompanyLabels = subscriptions.get(currentUser.getUserName());
            if (subscribedCompanyLabels != null && subscribedCompanyLabels.contains(companyLabel)) {
                return "subscribe";
            } else {
                return "unsubscribe";
            }
        }
    }

    private void handleSubscription(String companyLabel, String action) {
        List<String> subscribedCompanyLabels = subscriptions.getOrDefault(currentUser.getUserName(), new java.util.ArrayList<>());

        if (action.equals("subscribe") && !subscribedCompanyLabels.contains(companyLabel)) {
            subscribedCompanyLabels.add(companyLabel);
            for (Company company : DataManager.companyList) {
                if (company.getLabel().equalsIgnoreCase(companyLabel)) {
                    company.addObserver(currentUser);
                    currentUser.isSubscribed = true;
                }
            }
        } else if (action.equals("unsubscribe") && subscribedCompanyLabels.contains(companyLabel)) {
            subscribedCompanyLabels.remove(companyLabel);
            for (Company company : DataManager.companyList) {
                if (company.getLabel().equalsIgnoreCase(companyLabel)) {
                    company.deleteObserver(currentUser);
                    currentUser.isSubscribed = false;
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
                        currentUser.isSubscribed = true;
                    }
                }
            }
        }
    }
}
