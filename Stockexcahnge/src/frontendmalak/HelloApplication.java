package frontendmalak;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        HelloApplication.primaryStage = primaryStage;
        openNewWindow();
    }

    public static void openNewWindow() {
        try {
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("/frontendmalak/View/helloView.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("Lannister Exchange");
            newStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}