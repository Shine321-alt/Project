package com.project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainApp extends Application{
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "pass123";
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("APP");

        Label userNameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password: ");

        TextField userNameField = new TextField();
        PasswordField passwordField = new PasswordField();

        Label resultLabel = new Label();

        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String enterUserName = userNameField.getText();
            String enterpassword = passwordField.getText();

            resultLabel.setText((enterUserName.equals(ADMIN_USERNAME) && enterpassword.equals(ADMIN_PASSWORD)) ? "Success" : "Login fail");
            }
        );

        HBox userNameBox = new HBox(userNameLabel,userNameField);
        userNameBox.setSpacing(5);
        userNameBox.setAlignment(Pos.CENTER);
            
        HBox passwordBox = new HBox(passwordLabel,passwordField);
        passwordBox.setSpacing(5);
        passwordBox.setAlignment(Pos.CENTER);

        HBox loginButtonBox = new HBox(loginButton);
        loginButtonBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(20);
        root.getChildren().addAll(resultLabel,userNameBox,passwordBox,loginButtonBox);
        Scene scene = new Scene(root,600,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
    