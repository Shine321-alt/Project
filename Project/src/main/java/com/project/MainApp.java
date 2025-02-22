package com.project;

import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.FXMLLoader;

public class MainApp extends Application{
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage){
        try{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Restaurant");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);

        primaryStage.setScene(scene);
        primaryStage.show();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
    