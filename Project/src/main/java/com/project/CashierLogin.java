package com.project;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import org.h2.result.ResultTarget;

>>>>>>> 7be7ec387773a5b13fad037c91dfd47703463e2c
=======
>>>>>>> feature-1
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CashierLogin implements Initializable{

 
    @FXML
    private TableColumn<CustomerData, Double> ColumnPrice;

    @FXML
    private TableColumn<CustomerData, String> ColumnProductName;

    @FXML
    private TableColumn<CustomerData, Integer> ColumnQuantity;

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
    private TableView<CustomerData> menuTableView;

    @FXML
    private Label menuTotal;

    @FXML
<<<<<<< HEAD
    private Label totalPriceLable;

    @FXML
    private ListView<String> listvieworder;
    
    private double total = 0.00;


    //ราคาสินค้า
    private final double drink1Price = 25.00;
    private final double drink2Price = 20.00;
    private final double drink3Price = 15.00;
    private final double food1Price = 40.00;
    private final double food2Price = 60.00;
    private final double food3Price = 120.00;


    private final ObservableList<String> products = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        listview.setItem(products);
    }

    @FXML
    void addDrink1(ActionEvent event) {
        products.ADD
        total += drink1Price;
        updateTotalPrice();

=======
    private Button payBtn;

    @FXML
    private Button receiptBtn;

    @FXML
    private Button removeBtn;

    private Alert alert;

    private int customerId;

    private ObservableList<ProductData> cardListData;
    private ObservableList<CustomerData> menuListdata;

    private data data;

    private int id;

    public void menuSelectOrder(){
    
        CustomerData selectedItem = menuTableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null) 
            id = selectedItem.getId();
        
>>>>>>> 7be7ec387773a5b13fad037c91dfd47703463e2c
    }

    public void removeBtn() {
        if(menuTableView.getItems().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Cart is empty");
            alert.showAndWait();
            return;
        }

        CustomerData selectedItem = menuTableView.getSelectionModel().getSelectedItem();
        if(selectedItem == null || selectedItem.getId() <= 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item");
            alert.showAndWait();
            return;
        }

        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove " + selectedItem.getProductName() + "?");
        Optional<ButtonType> option = alert.showAndWait();
        
        if(option.isPresent() && option.get() == ButtonType.OK) {
            try (Connection con = DatabaseConection.gC()) {
                DatabaseForCustomer.deleteData(con, customerId, selectedItem.getId());
                menuTableView.getItems().remove(selectedItem);

                double total = 0;
                for(CustomerData item : menuTableView.getItems()) {
                    if(item != null && item.getPrice() != null) {
                        total += item.getPrice() * item.getQuantity();
                    }
                }
                menuTotal.setText(String.format("%.2f", total));

            } catch (SQLException e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setContentText("Error removing item: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public ObservableList<CustomerData> menuList() {
        ObservableList<CustomerData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";
        
        try (Connection con = DatabaseConection.gC();
             PreparedStatement prepare = con.prepareStatement(sql)) {
            
            prepare.setInt(1, customerId);
            try (ResultSet result = prepare.executeQuery()) {
                while (result.next()) {
                    CustomerData customerData = new CustomerData(
                        result.getInt("ID"),
                        result.getString("PRODUCT_ID"),
                        result.getString("PRODUCT_NAME"),
                        result.getInt("QUANTITY"),
                        result.getDouble("PRICE")
                    );
                    listData.add(customerData);
                }
            }
            return listData;
        } catch (SQLException e) {
            e.printStackTrace();
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Error loading menu data: " + e.getMessage());
            alert.showAndWait();
            return FXCollections.observableArrayList();
        }
    }

    public void showMenu() {
        if (menuTableView == null) {
            return;
        }
        
        ColumnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        ColumnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        menuListdata.clear();
        menuListdata.addAll(menuList());
        menuTableView.setItems(menuListdata);

        double total = 0;
        for(CustomerData item : menuListdata) {
            if(item != null) {
                Double price = item.getPrice();
                Integer quantity = item.getQuantity();
                if(price != null && quantity != null) {
                    total += price * quantity;
                }
            }
        }
        
        menuTotal.setText(String.format("%.2f", total));
    }

    public void customerId() {
        String sql = "SELECT MAX(CUSTOMER_ID) FROM CUSTOMER";
        try (Connection con = DatabaseConection.gC()) {

            try (PreparedStatement ppsm = con.prepareStatement(sql);
                 ResultSet rs = ppsm.executeQuery()) {
                if (rs.next()) {
                    customerId = rs.getInt(1);
                }
            }

            String check = "SELECT MAX(CUSTOMER_ID) FROM RECEIPT";
            try (PreparedStatement ppsm = con.prepareStatement(check);
                 ResultSet rs = ppsm.executeQuery()) {
                int checkId = 0;
                if (rs.next()) {
                    checkId = rs.getInt(1);
                }

                customerId = Math.max(customerId, checkId) + 1;
            }


            if (data != null) {
                data.customerId = customerId;
            } else {
                throw new IllegalStateException("Data object is not initialized");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Error generating customer ID: " + e.getMessage());
            alert.showAndWait();
        }
    }


    public void menuAmount() {
        try {
            if (menuAmount.getText() == null || menuAmount.getText().trim().isEmpty()) {
                menuChange.setText("0.00");
                return;
            }

            double amount = Double.parseDouble(menuAmount.getText().trim());
            double total = Double.parseDouble(menuTotal.getText());

            if (amount < 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Amount cannot be negative");
                alert.showAndWait();
                return;
            }

            double change = amount - total;
            menuChange.setText(String.format("%.2f", Math.max(0, change)));
        } catch (NumberFormatException e) {
            menuChange.setText("0.00");
        }
    }
    
    public void payBtn(){
        try{
            if (menuAmount.getText().isEmpty() || menuTotal.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Please enter payment amount");
                alert.showAndWait();
                return;
            }

            if(menuTableView.getItems().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Cart is empty");
                alert.showAndWait();
                return;
            }

            if(menuAmount.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter payment amount");
                alert.showAndWait();
                return;
            }

            double amount = Double.parseDouble(menuAmount.getText());
            double total = Double.parseDouble(menuTotal.getText());

            if(amount < total) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Insufficient payment amount");
                alert.showAndWait();
                return;
            }


            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Total: " + total + "\nAmount: " + amount + "\nChange: " + (amount - total) + "\n\nConfirm payment?");
            Optional<ButtonType> option = alert.showAndWait();
            if(option.get() != ButtonType.OK) {
                return;
            }

            try(Connection con = DatabaseConection.gC()){
                con.setAutoCommit(false);
                try {
                    // อัพเดทสต็อกสินค้า
                    String sql = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";
                    try(PreparedStatement ppsm = con.prepareStatement(sql)){
                        ppsm.setInt(1, customerId);
                        ResultSet rs = ppsm.executeQuery();

                        if (!rs.next()) {
                            throw new SQLException("Customer data not found");
                        }

                        do {
                            String productId = rs.getString("PRODUCT_ID");
                            int quantity = rs.getInt("QUANTITY");

                            int currentStock = DatabaseForMenu.checkStock(con, productId);
                            if(currentStock < quantity) {
                                throw new SQLException("Insufficient stock for product: " + productId);
                            }
                            int newStock = currentStock - quantity;
                            DatabaseForMenu.updateStock(con, productId, newStock);

                            if(newStock == 0)
                                DatabaseForMenu.updateStatus(con, productId, "Unavailable");
                        } while(rs.next());
                    }

                    // บันทึกใบเสร็จ
                    DatabaseForReceipt.insertReceipt(con, customerId, Controller.username, total);
                    DatabaseForReceipt.getReceiptId(con, customerId, Controller.username);
                    // ลบข้อมูลในตาราง CUSTOMER
                    DatabaseForCustomer.deleteData(con, customerId);

                    con.commit();
                } catch(Exception e) {
                    con.rollback();
                    throw e;
                }
            }

            menuTableView.getItems().clear();
            menuTotal.setText("0.00");
            menuAmount.clear();
            menuChange.setText("0.00");

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Payment successful");
            alert.showAndWait();

        }catch(NumberFormatException e ){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid amount");
            alert.showAndWait();
        } catch(SQLException e){
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Error processing payment: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public ObservableList<ProductData> menuGetdata() {
        String sql = "SELECT * FROM MENU ORDER BY ID";
        ObservableList<ProductData> listData = FXCollections.observableArrayList();

        try (Connection con = DatabaseConection.gC()) {
            if (con == null) {
                throw new SQLException("Database connection failed");
            }
            try (PreparedStatement ppsm = con.prepareStatement(sql);
                 ResultSet rs = ppsm.executeQuery()) {

                while (rs.next()) {
                    ProductData proD = new ProductData(
                        rs.getInt("ID"),
                        rs.getString("PRODUCT_ID"),
                        rs.getString("PRODUCT_NAME"),
                        rs.getString("PRODUCT_TYPE"),
                        rs.getDouble("PRICE"),
                        rs.getString("IMAGE")
                    );
                    listData.add(proD);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Error loading menu data: " + e.getMessage());
            alert.showAndWait();
        }
        return listData;
    }

    public void menuDisplayCard() {
        try {
            cardListData.clear();
            ObservableList<ProductData> listData = menuGetdata();
            cardListData.addAll(listData);

            int row = 0;
            int col = 0;

            menuGridPane.getRowConstraints().clear();
            menuGridPane.getColumnConstraints().clear();
            
            for(int i = 0; i < cardListData.size(); i++) {
                URL fxmlUrl = getClass().getResource("/com/project/cardProduct.fxml");
                if (fxmlUrl == null) {
                    throw new IOException("Cannot find cardProduct.fxml in resources");
                }
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                AnchorPane pane = fxmlLoader.load();
                
                ControllerCardProduct controller = fxmlLoader.getController();
                controller.setData(cardListData.get(i));
                controller.setCashierLogin(this); 
                controller.setCustomerId(customerId);

                if(col == 3) {
                    col = 0;
                    row++;
                }
                menuGridPane.add(pane, col++, row);
                GridPane.setMargin(pane, new Insets(10));
            }
        } catch(Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Loading Products");
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
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

                Stage myStage = (Stage) this.logoutBtn.getScene().getWindow();
                myStage.close();
            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        try {
            cardListData = FXCollections.observableArrayList();
            menuListdata = FXCollections.observableArrayList();
            data = new data();
            
            menuAmount.setText("0.00");
            menuTotal.setText("0.00");
            menuChange.setText("0.00");

            customerId();
            showMenu();
            menuDisplayCard();

        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Initialization Error");
            alert.setContentText("Error initializing application: " + e.getMessage());
            alert.showAndWait();
        }
    }
}