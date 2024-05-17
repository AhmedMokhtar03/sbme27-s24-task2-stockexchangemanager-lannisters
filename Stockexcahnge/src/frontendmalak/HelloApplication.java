package frontendmalak;

import frontendmalak.ViewControl.NotificationController;
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
            initMovableWindow(newStage);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    public static void initMovableWindow(Stage stage) {
        // Track the offset position when the window is dragged
        Delta dragDelta = new Delta();

        stage.getScene().setOnMousePressed(mouseEvent -> {
            dragDelta.x = stage.getX() - mouseEvent.getScreenX();
            dragDelta.y = stage.getY() - mouseEvent.getScreenY();
        });

        stage.getScene().setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + dragDelta.x);
            stage.setY(mouseEvent.getScreenY() + dragDelta.y);
        });
    }

    // Helper class to store the offset position
    private static class Delta {
        double x, y;
    }

    public static void main(String[] args) {
        launch(args);
    }
}