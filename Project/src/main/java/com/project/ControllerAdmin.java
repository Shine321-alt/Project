package com.project;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ControllerAdmin implements Initializable{
    @FXML
    private AreaChart<?, ?> dashboandChartIncome;

    @FXML
    private Label dashboandCustomer;

    @FXML
    private BarChart<?, ?> dashboandCustomerChart;

    @FXML
    private Label dashboandIncome;

    @FXML
    private Label dashboandSoldProducts;

    @FXML
    private Label dashboandTotalIncome;

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
    private TableColumn<ProductData, Date> inventoryCol_Date;

    @FXML
    private TableColumn<ProductData, String> inventoryCol_IdProduct;

    @FXML
    private TableColumn<ProductData, Double> inventoryCol_Price;

    @FXML
    private TableColumn<ProductData, String> inventoryCol_ProductName;

    @FXML
    private TableColumn<ProductData, String> inventoryCol_Status;

    @FXML
    private TableColumn<ProductData, Integer> inventoryCol_Stock;

    @FXML
    private TableColumn<ProductData, String> inventoryCol_Type;

    @FXML
    private TableColumn<CustomerData, Integer> columnCustomerId;

    @FXML
    private TableColumn<CustomerData, Date> columnDate;

    @FXML
    private TableColumn<CustomerData, String> columnEmployee;

    @FXML
    private TableColumn<CustomerData, Double> columnTotal;

    @FXML
    private TableView<CustomerData> tableviewCustomer;

    @FXML
    private AnchorPane inventoryForm;

    @FXML
    private ImageView inventoryImage;

    @FXML
    private Button inventoryImport;

    @FXML
    private TableView<ProductData> inventoryTable;

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
    private AnchorPane customersForm;

    @FXML
    private ComboBox<String> typeProduct;

    @FXML
    private Button update;

    private Alert alert;

    private String[] typeList = {"Food","Drink"};

    private String[] statusList = {"Available","Unavailable"};

    private Image image;
 

    @FXML
    public void clearForm(){
        idProduct.clear();
        productName.clear();
        stockProduct.clear();
        priceProduct.clear();
        typeProduct.getSelectionModel().clearSelection();
        statusProduct.getSelectionModel().clearSelection();
        inventoryImage.setImage(null);
        data.path = null;
    }

    public void dashboandDisplayNc(){
        String sql = "SELECT COUNT(CUSTOMER_ID) FROM RECEIPT";
        try(Connection con = DatabaseConection.gC()){
            try(PreparedStatement ppsm = con.prepareStatement(sql);
                ResultSet rs = ppsm.executeQuery()){
                    if(rs.next()){
                        int n = rs.getInt(1);
                        dashboandCustomer.setText(String.valueOf(n)); //เซ็ต lebel แสดงจำนวนใบเสร็จ จาก database
                    }
            }
        }catch(SQLException e){
            e.printStackTrace();
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error loading customer count: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void dashboardDisplayTotalIncome() {
        String sql = "SELECT SUM(TOTAL_AMOUNT) FROM RECEIPT";
        try(Connection con = DatabaseConection.gC();
            PreparedStatement ppsm = con.prepareStatement(sql);
            ResultSet rs = ppsm.executeQuery()) {
            
            if(rs.next()) {
                double total = rs.getDouble(1);
                dashboandTotalIncome.setText(String.format("$%.2f", total)); //เซ็ต lebel แสดงรายได้รวมจาก database
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void dashboardDisplayTodayIncome() {
        String sql = "SELECT SUM(TOTAL_AMOUNT) FROM RECEIPT WHERE DATE = CURRENT_DATE()";
        try(Connection con = DatabaseConection.gC();
            PreparedStatement ppsm = con.prepareStatement(sql);
            ResultSet rs = ppsm.executeQuery()) {
            
            if(rs.next()) {
                double todayIncome = rs.getDouble(1);
                dashboandIncome.setText(String.format("$%.2f", todayIncome)); //เซ็ต lebel แสดงรายได้วันนี้จาก database
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void dashboandCustomerChart(){
        String sql = "SELECT DATE, COUNT(CUSTOMER_ID) as customer_count FROM RECEIPT GROUP BY DATE ORDER BY DATE";
        try(Connection con = DatabaseConection.gC();
            PreparedStatement ppsm = con.prepareStatement(sql);
            ResultSet rs = ppsm.executeQuery()){
                
                XYChart.Series chart = new XYChart.Series();    //การเซ็ตข้อมูลในกราฟ
                chart.setName("Customer Count");

                while(rs.next()){
                    chart.getData().add(new XYChart.Data<>(
                        rs.getDate("DATE").toString(),
                        rs.getInt("customer_count")
                    ));
                }

                dashboandCustomerChart.getData().clear(); //เคลียร์ข้อมูลในกราฟก่อน ก่อนที่จะเซ็ตข้อมูลใหม่
                dashboandCustomerChart.getData().add(chart);
                
            }catch(SQLException e){
                e.printStackTrace();
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error loading chart data: " + e.getMessage());
                alert.showAndWait();
            }
    }

    public void dashboandIncomeChart(){
        String sql = "SELECT DATE, SUM(TOTAL_AMOUNT) as daily_income FROM RECEIPT GROUP BY DATE ORDER BY DATE";
        try(Connection con = DatabaseConection.gC();
            PreparedStatement ppsm = con.prepareStatement(sql);
            ResultSet rs = ppsm.executeQuery()){
                
                XYChart.Series chart = new XYChart.Series();
                chart.setName("Daily Income");

                while(rs.next()){
                    chart.getData().add(new XYChart.Data<>( //การเซ็ตข้อมูลในกราฟของรายได้ในวันที่ต่างๆ
                        rs.getDate("DATE").toString(),
                        rs.getDouble("daily_income")
                    ));
                }

                dashboandChartIncome.getData().clear();
                dashboandChartIncome.getData().add(chart);
                
            }catch(SQLException e){
                e.printStackTrace();
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error loading income chart data: " + e.getMessage());
                alert.showAndWait();
            }
    }

    public void switchForm(ActionEvent event){ //เปลี่ยนหน้า ต่างๆตามปุ่มที่กด
        if(event.getSource() == dashboand){
            dashBoandForm.setVisible(true);
            inventoryForm.setVisible(false);
            customersForm.setVisible(false);
        }else if(event.getSource() == inventory){
            dashBoandForm.setVisible(false);
            inventoryForm.setVisible(true);
            customersForm.setVisible(false);
        }else if(event.getSource() == customers){
            dashBoandForm.setVisible(false);
            inventoryForm.setVisible(false);
            customersForm.setVisible(true);
        }
    }

    public void inventoryUpdateBtn(){   //สำหรับอัพเดตข้อมูลปัจจุบันในตาราง
        if(idProduct.getText().isEmpty() || productName.getText().isEmpty()
        || stockProduct.getText().isEmpty() || priceProduct.getText().isEmpty() 
        || typeProduct.getSelectionModel().getSelectedItem() == null 
        || statusProduct.getSelectionModel().getSelectedItem() == null
        || data.path == null){

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill all the fields");
            alert.showAndWait();
            return;
        }else{
            try(Connection con = DatabaseConection.gC()){
                Double price = Double.parseDouble(priceProduct.getText());
                Integer stock = Integer.parseInt(stockProduct.getText());

                if(!DatabaseForMenu.checkProduct(con, idProduct.getText())){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText(idProduct.getText() + " does not exist");
                    alert.showAndWait();
                    return;
                }
                else if(price <= 0 || stock <= 0){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("Price and Stock must be greater than 0");
                    alert.showAndWait();
                    return;
                }else{
                    DatabaseForMenu.updateMenu(con,     //โค้ดสำหรับอัพเดตข้อมูลใน database
                    idProduct.getText(),
                    productName.getText(),
                    typeProduct.getSelectionModel().getSelectedItem(),
                    stock,
                    price,
                    statusProduct.getSelectionModel().getSelectedItem(),
                    data.path,
                    new java.sql.Date(System.currentTimeMillis()));
                
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("SUCCESS MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Successfully updated");
                alert.showAndWait();

                inventoryShowData(); //แสดงข้อมูลในตารางหลังจากอัพเดต
                clearForm(); //เคลียร์ข้อมูลในช่องต่างๆ
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void inventoryDeleteBtn(){   //ลบข้อมูลในตาราง
        if(idProduct.getText().isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill the Product ID");
            alert.showAndWait();
            return;
        }
        
        try(Connection con = DatabaseConection.gC()){
            if(!DatabaseForMenu.checkProduct(con, idProduct.getText())){ //ดึงข้อมูลจาก textfield idProduct เพิ้อไปเช็คใน database 
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText(idProduct.getText()+" does not exist");
            alert.showAndWait();
            return;
            }

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete " + idProduct.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
            DatabaseForMenu.deleteMenu(con, idProduct.getText()); //ลบข้อมูลใน database
                
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("SUCCESS MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("Successfully deleted");
            alert.showAndWait();

            inventoryShowData(); //แสดงข้อมูลในตารางหลังจากลบ
            clearForm();
            }
        }catch(SQLException e){
            e.printStackTrace();
         } 
    }

    public void inventoryAddBtn(){ //สำหรับเพิ่มข้อมูลในตาราง
        if(idProduct.getText().isEmpty() || productName.getText().isEmpty()
        || stockProduct.getText().isEmpty() || priceProduct.getText().isEmpty() 
        || typeProduct.getSelectionModel().getSelectedItem() == null 
        || statusProduct.getSelectionModel().getSelectedItem() == null
        || data.path == null){

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill all the fields");
            alert.showAndWait();
        }else{

            try(Connection con = DatabaseConection.gC()){ 
                if(DatabaseForMenu.checkProduct(con, idProduct.getText())){ //เช็คว่า idProduct ที่กรอกไปมีอยู่ใน database หรือไม่
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText(idProduct.getText()+" is already exist");
                    alert.showAndWait();
                }else{
                    try{
                    Double price = Double.parseDouble(priceProduct.getText());
                    Integer stock = Integer.parseInt(stockProduct.getText());

                    if(price <= 0 || stock <= 0){ //เช็คว่า price และ stock ที่กรอกไปมีค่ามากกว่า 0 หรือไม่
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("ERROR MESSAGE");
                        alert.setHeaderText(null);
                        alert.setContentText("Price and Stock must be greater than 0");
                        alert.showAndWait();
                    }else{ //ถ้าผ่านการเช็คทั้งหมดให้ทำการเพิ่มข้อมูลใน database
                    
                DatabaseForMenu.insert(con, idProduct.getText(), productName.getText()
                                        , typeProduct.getSelectionModel().getSelectedItem()
                                        , price, stock, statusProduct.getSelectionModel().getSelectedItem()
                                        , data.path);

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("SUCCESS MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Successfully added");
                alert.showAndWait();

                inventoryShowData();
                clearForm();

                    }
            }catch(NumberFormatException e1){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR MESSAGE");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a number");
                    alert.showAndWait();
                    }
                }
            }catch(SQLException e){
                e.printStackTrace();
            }

        }
    }

    public ObservableList<CustomerData> customerList(){
        String sql = "SELECT * FROM RECEIPT";
        ObservableList<CustomerData> customerData = FXCollections.observableArrayList();
        
        try(Connection con = DatabaseConection.gC()){
            try(PreparedStatement ppsm = con.prepareStatement(sql);
                ResultSet rs = ppsm.executeQuery()){
                    while(rs.next()){
                        CustomerData cD = new CustomerData(
                            rs.getInt("RECEIPT_ID"),
                            rs.getInt("CUSTOMER_ID"),
                            rs.getString("EMPLOYEE_USERNAME"),
                            rs.getDouble("TOTAL_AMOUNT"),
                            rs.getDate("DATE")
                        );
                        customerData.add(cD);
                    }
                    return customerData;
            }
        }catch(SQLException e){
            e.printStackTrace();
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Error loading customer data: " + e.getMessage());
            alert.showAndWait();
            return FXCollections.observableArrayList();
        }
    }

    private ObservableList<CustomerData> customerListData;

    public void customersShowData(){ // เซ็ตข้อมูลในหน้า customer ให้แสดงในตาราง โดยเรียกใช้ method customerList() เพื่อดึงข้อมูลจาก database
        customerListData = customerList();

        columnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnEmployee.setCellValueFactory(new PropertyValueFactory<>("employeeUsername"));
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        tableviewCustomer.setItems(customerListData);

    }

    public void inventoryImportBtn(){

        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*.png", "*.jpg", "*.jpeg"));
        
        File file = openFile.showOpenDialog(mainForm.getScene().getWindow());

        if(file != null){

            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(),136,156,false,true);

            inventoryImage.setImage(image);
        }

    }

    public ObservableList<ProductData> inventoryDataList() {
        ObservableList<ProductData> productData = FXCollections.observableArrayList(); //การเซ็ตข้อมูลในตาราง และส่งกลับไปยัง method inventoryShowData()
        String sql = "SELECT * FROM MENU";
        
        try (Connection con = DatabaseConection.gC();
             PreparedStatement prepare = con.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {
            
            if (con == null) {
                throw new SQLException("Database connection failed");
            }
            
            while (result.next()) {
                ProductData prod = new ProductData(
                    result.getInt("ID"),
                    result.getString("PRODUCT_ID"),
                    result.getString("PRODUCT_NAME"),
                    result.getDouble("PRICE"),
                    result.getString("PRODUCT_TYPE"),
                    result.getInt("STOCK"),
                    result.getString("STATUS"),
                    result.getString("IMAGE"),
                    result.getDate("DATE")
                );
                productData.add(prod);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Error loading inventory data: " + e.getMessage());
            alert.showAndWait();
        }
        
        return productData;
    }

    private ObservableList<ProductData> inventoryListData;

    public void inventoryShowData(){
        inventoryListData = inventoryDataList(); //แสดงข้อมูลตาม column
  
        inventoryCol_IdProduct.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventoryCol_ProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventoryCol_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventoryCol_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryCol_Stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventoryCol_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventoryCol_Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventoryTable.setItems(inventoryListData);
    }

    public void inventorySelectData(){ //คลิกที่ข้อมูลในตารางเพื่อให้แสดงในช่องต่างๆ

        ProductData product = inventoryTable.getSelectionModel().getSelectedItem();
        int n = inventoryTable.getSelectionModel().getSelectedIndex();

        if((n-1) < -1) return;  //ป้องกัน NullPointerException กรณีที่ไม่มีการเลือกข้อมูลในตาราง
        idProduct.setText(product.getProductId());
        productName.setText(product.getProductName());
        typeProduct.setValue(product.getType());
        priceProduct.setText(String.valueOf(product.getPrice()));
        stockProduct.setText(String.valueOf(product.getStock()));
        statusProduct.setValue(product.getStatus());

        String path = "File:" + product.getImage();
        image = new Image(path, 136, 156, false, true);
        inventoryImage.setImage(image);


    }

     public void inventoryTypeList(){
         ArrayList<String> arraytypeList = new ArrayList();

         for(String list : this.typeList)
             arraytypeList.add(list);
         

         ObservableList listData = FXCollections.observableArrayList(arraytypeList);
         typeProduct.setItems(listData);

     }

     public void inventoryStatus(){
        ArrayList<String> statuslist = new ArrayList();

        for(String data : this.statusList)
            statuslist.add(data);
        
        ObservableList listData = FXCollections.observableArrayList(statuslist);
        statusProduct.setItems(listData);
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
    public void initialize(URL location, ResourceBundle resources){ //เซ็ตข้อมูลที่ต้องแสดงในตาราง และเซ็ตข้อมูลในช่องต่างๆ
        inventoryTypeList();
        inventoryStatus();
        inventoryShowData();
        customersShowData();
        dashboandDisplayNc();
        dashboardDisplayTotalIncome();
        dashboardDisplayTodayIncome();
        dashboandCustomerChart();   
        dashboandIncomeChart();
    }
}
