package frontendosama.Control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ControlTitleBar {

    @FXML
    private Button closeButton;

    @FXML
    private Pane closePane;

    @FXML
    private Button maximizeButton;

    @FXML
    private Pane maximizePane;

    @FXML
    private Button minimizeButton;

    @FXML
    private Pane minimizePane;

    @FXML
    void closeButtonColored(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            closePane.setOpacity(0.6);
        } else if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
            closePane.setOpacity(0);
        }


    }

    @FXML
    void closeProgram(ActionEvent event) {

    }

    @FXML
    void maximizeButtonColored(MouseDragEvent event) {

    }

    @FXML
    void maximizeProgram(ActionEvent event) {

    }

    @FXML
    void minimizeButtonColored(MouseDragEvent event) {

    }

    @FXML
    void minimizeProgram(ActionEvent event) {

    }

}
