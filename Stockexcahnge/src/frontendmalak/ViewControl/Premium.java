package frontendmalak.ViewControl;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static frontendmalak.HelloApplication.primaryStage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static frontendmalak.HelloApplication.primaryStage;

    public class Premium implements Initializable {
        private double x = 0,y = 0;

        @FXML
        private LineChart<?, ?> lineChart;

        @FXML
        private AnchorPane sideBar;
        private JFXButton aapl;
        private JFXButton tss;
        private JFXButton tar;
        private JFXButton sta;
        private JFXButton o;


        private Stage stage;



        public void initialize() {
            sideBar.setOnMousePressed(mouseEvent -> {
                x = mouseEvent.getSceneX();
                y = mouseEvent.getSceneY();
            });

            sideBar.setOnMouseDragged(mouseEvent -> {
                stage.setX(mouseEvent.getScreenX() - x);
                stage.setY(mouseEvent.getScreenY() - y);
            });

            lineChart.getXAxis().setLabel("XAxis");
            lineChart.getYAxis().setLabel("YAxis");

            XYChart.Series series1 = new XYChart.Series();

            series1.getData().add(new XYChart.Data("1",5));
            series1.getData().add(new XYChart.Data("2",4));
            series1.getData().add(new XYChart.Data("3",6));
            series1.getData().add(new XYChart.Data("5",3));
            series1.getData().add(new XYChart.Data("9",10));

            XYChart.Series series2 = new XYChart.Series();

            series2.getData().add(new XYChart.Data("1",2));
            series2.getData().add(new XYChart.Data("3",2));
            series2.getData().add(new XYChart.Data("4",5));

            XYChart.Series series3 = new XYChart.Series();

            series3.getData().add(new XYChart.Data("1",1));
            series3.getData().add(new XYChart.Data("2",4));
            series3.getData().add(new XYChart.Data("4",9));

            lineChart.getData().addAll(series1,series2,series3);
        }

        public void setStage(Stage stage){
            this.stage = stage;
        }

        @FXML
        void closeProgram(ActionEvent event) {
            stage.close();
        }

        private ActionEvent event;

        public void Back3(ActionEvent event) throws IOException {
            this.event = event;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
    }


