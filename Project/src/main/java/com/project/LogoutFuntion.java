package com.project;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LogoutFuntion {
    @FXML
    private Button logoutButton;

    @FXML
    private void handleLogout(ActionEvent event){

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage loginStage = new Stage();
            loginStage.setScene(scene);
            loginStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
