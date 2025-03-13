package com.project;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CashierLogin implements Initializable{

 
    @FXML
    private TableColumn<?, ?> ColumnPrice;

    @FXML
    private TableColumn<?, ?> ColumnProductName;

    @FXML
    private TableColumn<?, ?> ColumnQuantity;

    @FXML
    private ScrollPane MenuScrollPane;

    @FXML
    private Button logoutBtn;

    @FXML
    private TextField menuAmount;

    @FXML
    private Label menuChange;

    @FXML
    private GridPane menuGridPane;

    @FXML
    private TableView<?> menuTableView;

    @FXML
    private Label menuTotal;

    @FXML
    private Button payBtn;

    @FXML
    private Button receiptBtn;

    @FXML
    private Button removeBtn;

    private ObservableList<ProductData> cardListData;

    public ObservableList<ProductData> menuGetdata(){
        return cardListData;
    } 
    
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
    }
}
