package frontendmalak.ViewControl;

import backend.Company;
import backend.DataManager;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Premium implements Initializable {
    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private AnchorPane sideBar;

    @FXML
    private JFXButton aapl;

    @FXML
    private JFXButton tss;

    @FXML
    private JFXButton tar;

    @FXML
    private JFXButton sta;

    @FXML
    private JFXButton o;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
    }

    private void initializeButtons() {
        aapl.setOnAction(this::handleButtonClick);
        tss.setOnAction(this::handleButtonClick);
        tar.setOnAction(this::handleButtonClick);
        sta.setOnAction(this::handleButtonClick);
        o.setOnAction(this::handleButtonClick);
    }

    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() instanceof JFXButton) {
            JFXButton clickedButton = (JFXButton) event.getSource();
            String companyName = clickedButton.getText();
            Company company = getCompanyByName(companyName);
            if (company != null) {
                populateLineChart(company);
            }
        }
    }

    private Company getCompanyByName(String name) {
        for (Company company : DataManager.companyList) {
            if (company.getName().equals(name)) {
                return company;
            }
        }
        return null;
    }

    private void populateLineChart(Company company) {
        lineChart.getData().clear(); // Clear existing data
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName(company.getName());
        for (int i = 0; i < company.graphList.size(); i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i), company.graphList.get(i)));
        }
        lineChart.getData().add(series);
}
}
