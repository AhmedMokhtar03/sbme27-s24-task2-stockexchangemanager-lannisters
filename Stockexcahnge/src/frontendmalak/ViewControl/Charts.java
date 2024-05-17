package frontendmalak.ViewControl;

import backend.Company;
import backend.DataManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static frontendmalak.HelloApplication.primaryStage;

public class Charts implements Initializable {

    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private Map<Company, XYChart.Series<String, Double>> companycharts = new HashMap<>();
    private ScheduledExecutorService scheduledExecutorService;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Flag to track whether initialization has been done
    private boolean isInitialized = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Ensure initialization is done only once
        if (!isInitialized) {
            initializeChart();
            isInitialized = true;
        }
    }

    private void initializeChart() {
        for (Company company : DataManager.companyList) {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(company.getName());
            companycharts.put(company, series);
            lineChart.getData().add(series);
            System.out.println("Initialized series for company: " + company.getName());
        }

        // Schedule a periodic task to update the chart
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::updateChart, 0, 10, TimeUnit.SECONDS);
    }

    private void updateChart() {
        Platform.runLater(() -> {
            for (Company company : DataManager.companyList) {
                XYChart.Series<String, Double> series = companycharts.get(company);
                // Ensure data point count does not exceed a certain limit (e.g., 100) to avoid excessive memory usage
                if (series.getData().size() > 100) {
                    series.getData().remove(0);
                }
                String timeStamp = LocalDateTime.now().format(dateTimeFormatter);
                series.getData().add(new XYChart.Data<>(timeStamp, company.getStockPrice()));
            }
        });
    }

    public void Gopremium(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/premiumsubscribtion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Ensure to shutdown the executor service when the application stops
    public void stop() {
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.shutdown();
        }
    }
}
