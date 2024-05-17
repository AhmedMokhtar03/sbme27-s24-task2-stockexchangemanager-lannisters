
package frontendmalak.ViewControl;

import backend.Notification;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationController {
    @FXML
    private TableView<Notification> notificationTable;

    @FXML
    private TableColumn<Notification,String> contentColumn;

    public static ObservableList<Notification> notificationList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        notificationTable.setItems(notificationList);

    }

    @FXML
    void closeApp(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    void clearNotification(ActionEvent event) {
        notificationList.clear();
    }



}
