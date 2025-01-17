package frontendmalak.ViewControl;

import backend.Company;
import backend.DataManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Charts implements Initializable {
    private List<XYChart.Series<String, Double>> series = new ArrayList<>();

    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private Button back3;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeChart();
    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
    }

    private void initializeChart() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM"); // Date format

        for (Company company : DataManager.companyList) {
            XYChart.Series<String, Double> series1 = new XYChart.Series<>();
            series1.setName(company.getName());
            Tooltip tooltip = new Tooltip(company.getName());
            tooltip.setShowDelay(Duration.ZERO);
            tooltip.setAutoHide(true);
            tooltip.setShowDuration(Duration.INDEFINITE);

            for (int i = 0; i < company.graphList.size(); i++) {
                // Generate fake dates for x-axis
                LocalDate fakeDate = LocalDate.of(2024, 5, 7).plusDays(i); // Starting from 15th May 2022
                String xValue = fakeDate.format(dateFormatter);

                XYChart.Data<String, Double> data = new XYChart.Data<>(xValue, company.graphList.get(i));
                data.setNode(new CustomNode(tooltip));
                series1.getData().add(data);
            }

            series.add(series1);
        }
        lineChart.getData().addAll(series);
    }

    public void Gopremium(ActionEvent event) throws IOException{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/premiumsubscribtion.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public void Back3(ActionEvent event) throws IOException {FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private class CustomNode extends StackPane {
        private final Tooltip tooltip;

        public CustomNode(Tooltip tooltip) {
            this.tooltip = tooltip;
            Tooltip.install(this, tooltip);
            setOnMouseEntered(this::showTooltip);
            setOnMouseExited(this::hideTooltip);
        }

        private void showTooltip(MouseEvent event) {
            tooltip.setAutoHide(false);
            tooltip.show(this, event.getScreenX(), event.getScreenY());
        }

        private void hideTooltip(MouseEvent event) {
            tooltip.hide();
            tooltip.setAutoHide(true);
        }

    }


}