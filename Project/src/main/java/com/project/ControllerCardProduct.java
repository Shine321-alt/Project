package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ControllerCardProduct {
    @FXML
    private Button addBtn;

    @FXML
    private AnchorPane cardForm;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    private Spinner<?> productSpinner;

    private ProductData productData;
    private Image image;
    
    public void setData(ProductData productData){
        this.productData = productData;

        productName.setText(productData.getProductName());
        productPrice.setText(productData.getProductPrice().toString());
        String path = "File:" + productData.getImage();
        image = new Image(path,150,150,false,true);
        productImage.setImage(image);
    }
    

}
