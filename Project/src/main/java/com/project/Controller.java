package com.project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;


public class Controller {
    String userAdmin = "Shine";
    String passwordAdmin = "123";

    @FXML
    private Hyperlink HyperlinkForgotPassword;

    @FXML
    private TextField answer;

    @FXML
    private Button buttonAlready;

    @FXML
    private Button buttonLogin;

    @FXML
    private AnchorPane createForm;

    @FXML
    private Button createNewAccount;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private ComboBox<?> question;

    @FXML
    private AnchorPane registerForm;

    @FXML
    private TextField registerPassword;

    @FXML
    private TextField registerUser;

    @FXML
    private Button signUpBotton;

    @FXML
    private TextField textFieldUsername;

    private Alert alert;
    
    public void loginBotton(){
        if(textFieldUsername.getText().isEmpty() || passwordfield.getText().isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Username or Password is empty!!");
            alert.showAndWait();
        }else{
            if(textFieldUsername.getText().equals(userAdmin) && passwordfield.getText().equals(passwordAdmin)){
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Login success!!");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Login incorrect!!");
                alert.showAndWait();
            }
        }
    }

    public void switchForm(ActionEvent event){
        
        TranslateTransition slider = new TranslateTransition();

        if(event.getSource() == createNewAccount){
            slider.setNode(createForm); 
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                buttonAlready.setVisible(true);
                createNewAccount.setVisible(false);
            });
            
            slider.play();
        }else if(event.getSource() == buttonAlready ){
            slider.setNode(createForm); 
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                buttonAlready.setVisible(false);
                createNewAccount.setVisible(true);
            });
            
            slider.play();
        }
    }
}
