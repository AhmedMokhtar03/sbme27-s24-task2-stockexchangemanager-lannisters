package frontendmalak.ViewControl;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static frontendmalak.HelloApplication.primaryStage;

public class Charts implements Initializable {


     @FXML
  private LineChart<String, Double> lineChart;

     @FXML

     private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;



    @FXML





     public void initialize(URL url, ResourceBundle resourceBundle) {




          XYChart.Series<String, Double> series1 = new XYChart.Series<>();

               series1.setName("High");
          series1.getData().add(new XYChart.Data("Jan",100.0));
          series1.getData().add(new XYChart.Data("Feb",40.0));
          series1.getData().add(new XYChart.Data("Mar",70.0));
          series1.getData().add(new XYChart.Data("Aprl",60.0));
          series1.getData().add(new XYChart.Data("May",50.0));

        XYChart.Series<String, Double> series2 = new XYChart.Series<>();


        series2.getData().add(new XYChart.Data("Jan",10.0));
        series2.getData().add(new XYChart.Data("Feb",60.0));
        series2.getData().add(new XYChart.Data("Mar",30.0));
        series2.getData().add(new XYChart.Data("Aprl",120.0));
        series2.getData().add(new XYChart.Data("May",100.0));






          lineChart.getData().addAll(series1,series2);
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
