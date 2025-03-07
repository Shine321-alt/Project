package com.project;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CashierLogin {

    @FXML
    private Button Drink1;

    @FXML
    private Button Drink2;

    @FXML
    private Button Drink3;

    @FXML
    private Button LogutButtton;

    @FXML
    private Button Order1;

    @FXML
    private Button Order2;

    @FXML
    private Button Order3;

    @FXML
    private Button confirmButton;

    @FXML
    private VBox menu1;

    @FXML
    private Label totalPriceLable;
    
    private double total = 0.00;


    //ราคาสินค้า
    private final double drink1Price = 25.00;
    private final double drink2Price = 20.00;
    private final double drink3Price = 15.00;
    private final double food1Price = 40.00;
    private final double food2Price = 60.00;
    private final double food3Price = 120.00;

    @FXML
    void addDrink1(ActionEvent event) {
        total += drink1Price;
        updateTotalPrice();

    }

    @FXML
    void addDrink2(ActionEvent event) {
        total += drink2Price;
        updateTotalPrice();

    }

    @FXML
    void addDrink3(ActionEvent event) {
        total += drink3Price;
        updateTotalPrice();

    }

    @FXML
    void addFood1(ActionEvent event) {
        total += food1Price;
        updateTotalPrice();

    }

    @FXML
    void addFood2(ActionEvent event) {
        total += food2Price;
        updateTotalPrice();

    }

    @FXML
    void addFood3(ActionEvent event) {
        total += food3Price;
        updateTotalPrice();

    }
    
    private void updateTotalPrice(){
        totalPriceLable.setText(String.format("Total: %.2f THB", total));
    }

    @FXML
    void confirmOrder(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Confirmed");
        alert.setHeaderText("Your order has been placed!");
        alert.setContentText("Total amount: " + String.format("%.2f THB", total));
        alert.showAndWait();

        total = 0;
        updateTotalPrice();


    }
    @FXML
    void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) LogutButtton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
