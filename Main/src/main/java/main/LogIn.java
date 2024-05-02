package main;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.java.HelloApplication;
import javafx.scene.control.ChoiceBox;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogIn implements Initializable {


private Label myLabel;
    @FXML
    private Button button;
    @FXML
    private Label wronglogin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    private ChoiceBox<String> choicebox;
    private  String[] SelectStatus = {"Admin","User","PremiumUser"};


    public void initialize(URL arg0, ResourceBundle arg1) {

        choicebox.getItems().addAll(SelectStatus);
        choicebox.setOnAction(this::getSelectStatus);

    }



    public void getSelectStatus(ActionEvent event) {

        String label = choicebox.getValue();
        myLabel.setText(label);
    }

    public void userLogIn(ActionEvent event)throws IOException{

        checkLogin();
    }
private void checkLogin() throws IOException{
    HelloApplication m = new HelloApplication();
    if(username.getText().equals("java")&&
    password.getText().equals("123")){
        wronglogin.setText("Success!");
    m.changeScene();}
    else if (username.getText().isEmpty()&&password.getText().isEmpty()) {
        wronglogin.setText("Please enter your data");
    }
    else{wronglogin.setText("Wrong Login or Password");

}

}}
