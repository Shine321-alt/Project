package com.project;

import java.net.URI;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerAdmin implements Initializable{
    @FXML
    private Button add;

    @FXML
    private Button crear;

    @FXML
    private Button customers;

    @FXML
    private AnchorPane dashBoandForm;

    @FXML
    private Button dashboand;

    @FXML
    private Button delete;

    @FXML
    private TextField idProduct;

    @FXML
    private Button inventory;

    @FXML
    private TableColumn<?, ?> inventoryCol_Date;

    @FXML
    private TableColumn<?, ?> inventoryCol_IdProduct;

    @FXML
    private TableColumn<?, ?> inventoryCol_Price;

    @FXML
    private TableColumn<?, ?> inventoryCol_ProductName;

    @FXML
    private TableColumn<?, ?> inventoryCol_Status;

    @FXML
    private TableColumn<?, ?> inventoryCol_Stock;

    @FXML
    private TableColumn<?, ?> inventoryCol_Type;

    @FXML
    private AnchorPane inventoryForm;

    @FXML
    private ImageView inventoryImage;

    @FXML
    private Button inventoryImport;

    @FXML
    private TableView<?> inventoryTable;

    @FXML
    private AnchorPane mainForm;

    @FXML
    private TextField priceProduct;

    @FXML
    private TextField productName;

    @FXML
    private Button sighOut;

    @FXML
    private ComboBox<String> statusProduct;

    @FXML
    private TextField stockProduct;

    @FXML
    private ComboBox<?> typeProduct;

    @FXML
    private Button update;


    private Alert alert;

    private String[] typeList = {"TYPE","Food","Drink"};

     public void inventoryTypeList(){
         ArrayList<String> arraytypeList = new ArrayList();

         for(String list : this.typeList){
             arraytypeList.add(list);
         }

         ObservableList listData = FXCollections.observableArrayList(arraytypeList);
         typeProduct.setItems(listData);

     }

    public void logout(){
        try{
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){
                
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Restaurant");
                stage.setMinHeight(400);
                stage.setMinWidth(600);

                stage.setScene(scene);
                stage.show();

                Stage myStage = (Stage) this.sighOut.getScene().getWindow();
                myStage.close();
            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources){
        inventoryTypeList();
    }
}
