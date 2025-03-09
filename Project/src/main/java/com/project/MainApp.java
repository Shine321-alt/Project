package com.project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.FXMLLoader;

public class MainApp extends Application{
    public static void main(String[] args){ 

        try(Connection con = DatabaseConection.gC()){
        if (con != null) {
            System.out.println("Database connected successfully!");
            DatabaseForMenu.createTable(con);
            DatabaseForMenu.getInformation(con);
        } else {
            System.out.println("Failed to connect to database.");
        }
    }catch(SQLException e){e.printStackTrace();}
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
    