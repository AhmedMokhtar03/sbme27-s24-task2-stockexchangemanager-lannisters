package frontendosama;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class frontendMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/LoginForm.fxml"));
        stage.setTitle("Lannister Exchange");
        //stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();



    }

    public static void main(String[] args) {


        launch(args);



    }
}