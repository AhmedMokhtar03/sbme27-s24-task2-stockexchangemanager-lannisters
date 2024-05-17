package frontendmalak.ViewControl;
import backend.Company;
import backend.DataManager;
import backend.User;
import frontendmalak.HelloApplication;
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
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static frontendmalak.HelloApplication.primaryStage;

public class Charts implements Initializable {
    private List<XYChart.Series<String, Double>> series = new ArrayList<>();
    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Check if the chart is not initialized already
        initializeChart();
    }

    private void initializeChart() {
        for(Company company: DataManager.companyList){
            XYChart.Series<String, Double> series1 = new XYChart.Series<>();
            series1.setName(company.getName());
            for(int i= 0; i<company.graphList.size(); i++){
                String xValue = String.valueOf(i);
                series1.getData().add(new XYChart.Data(xValue,company.graphList.get(i)));
            }
            series.add(series1);
            }
        lineChart.getData().addAll(series);
        }



    public void Back3(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void Gopremium(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/premiumsubscribtion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
