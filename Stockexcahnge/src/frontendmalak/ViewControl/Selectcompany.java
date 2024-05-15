package frontendmalak.ViewControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class Selectcompany {
    @FXML
    private ChoiceBox<String> aapl;
    @FXML
    private ChoiceBox<String> amzn;
    public void initialize() {

        ObservableList<String> select = FXCollections.observableArrayList("subscribe", "unsubscribe");
        aapl.setItems(select);
        aapl.setValue("subscribe");
        ObservableList<String> select1 = FXCollections.observableArrayList("subscribe", "unsubscribe");
        amzn.setItems(select);
        amzn.setValue("subscribe");
}}
