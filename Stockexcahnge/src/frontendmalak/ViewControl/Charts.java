package frontendmalak.ViewControl;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import static frontendmalak.HelloApplication.stg;

public class Charts {
     private ActionEvent event;

     public void Back3(ActionEvent event) throws IOException {
          this.event = event;
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontendmalak/View/UserView.fxml"));
          Parent root = loader.load();
          Scene scene = new Scene(root);
          stg.setScene(scene);
          stg.show();

     }
}
