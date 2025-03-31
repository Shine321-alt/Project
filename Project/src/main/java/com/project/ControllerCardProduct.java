package com.project;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ControllerCardProduct implements Initializable {
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
    private Spinner<Integer> productSpinner;

    private ProductData productData;
    private Image image;
    private SpinnerValueFactory<Integer> spin;
    private int quantity;
    private Alert alert;
    private int customerId;
    private double totalprice;
    private double price;
    private String productId;

    private CashierLogin cashierLogin;

    public void setCashierLogin(CashierLogin cashierLogin){
        this.cashierLogin = cashierLogin;
    }
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

//DatabaseForcustomer
    public void setData(ProductData productData){
        this.productData = productData;
        productId = productData.getProductId();
        productName.setText(productData.getProductName());
        productPrice.setText(productData.getProductPrice().toString());
        String path = "File:" + productData.getImage();
        image = new Image(path,150,150,false,true);
        productImage.setImage(image);
        price = productData.getProductPrice();
    }



    public void setQuantity(){
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1);
        productSpinner.setValueFactory(spin);
    }



    public void addBtn() {
        try {
            quantity = Math.abs(productSpinner.getValue());
            try (Connection con = DatabaseConection.gC()) {
                // ตรวจสอบสถานะและสต็อกสินค้า
                if (!DatabaseForMenu.checkStatus(con, productId)) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("Product is not available");
                    alert.showAndWait();
                    return;
                }

                if (quantity == 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select quantity");
                    alert.showAndWait();
                    return;
                }

                int currentStock = DatabaseForMenu.checkStock(con, productId);

                if(quantity > currentStock){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("Product is out of stock");
                    alert.showAndWait();
                    return;
                }

                if (price <= 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid product price");
                    alert.showAndWait();
                    return;
                }

                totalprice = price * quantity;
                String formattedTotal = String.format("%.2f", totalprice);

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Add to Cart");
                alert.setHeaderText(null);
                alert.setContentText(String.format("Add to cart?\nProduct: %s\nQuantity: %d\nTotal: %s", 
                    productData.getProductName(), quantity, formattedTotal));
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get() == ButtonType.OK) {
                    DatabaseForCustomer.insertData(con, 
                        customerId,  
                        productData.getProductId(),
                        productData.getProductName(),
                        quantity, 
                        Double.parseDouble(formattedTotal), 
                        Controller.username);  
                        currentStock -= quantity;
                    
                    if(cashierLogin != null) {
                        cashierLogin.showMenu();
                    }
                    
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Product added to cart");
                    alert.showAndWait();
                    
                    productSpinner.getValueFactory().setValue(1);
                }
            }
        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("Invalid number format");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }





    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        setQuantity();
    }
}
