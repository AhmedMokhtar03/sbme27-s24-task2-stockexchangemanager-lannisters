package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;
import static javafx.fxml.FXMLLoader.load;

public class HelloApplication extends Application {
    public static Stage stg;






    @Override
    public void start(Stage stage) throws IOException {
        stg =  stage;

        stage.setResizable(false);

        Parent root = load(getClass().getResource("/org/example/main/hello-view.fxml"));

        stage.setTitle("Hello!");
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }
    public static void changeScene( Stage stg) throws IOException {


        Parent pane =FXMLLoader.load(HelloApplication.class.getResource("/org/example/main/afterLogin.fxml"));


        HelloApplication.stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch(args);
    }




}
