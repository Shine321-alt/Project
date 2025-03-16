package com.project;

import java.sql.Date;

public class CustomerData {
    
    private String productName;
    private String productType;
    private int stock;
    private String productId;

    private Integer quantity;
    private Double price;
    private Integer id;
    private Integer customerId;
    private Double totalPrice;;
    private String employeeUsername;
    private Date date;

    public CustomerData(Integer id , Integer customerId , String employeeUsername, Double totalPrice,Date date) {
        this.id = id;
        this.customerId = customerId;
        this.employeeUsername = employeeUsername;
        this.totalPrice = totalPrice;
        this.date = date;
    }


    
    public CustomerData(int id,String productName, int quantity, Double price) {
        this.productName = productName;
        this.productType = productType;
        this.stock = stock;
        this.id = id;
    }
    public CustomerData(String productName, int quantity, Double price) {
        this.productName = productName;
        this.quantity = quantity;    
        this.price = price;
    }

    public CustomerData(String productId,String productName, int quantity, Double price) {
        this.productName = productName;
        this.productId = productId;
        this.quantity = quantity;    
        this.price = price;         
    }
    public CustomerData(int id,String productId,String productName, int quantity, Double price) {
        this.id = id;
        this.productName = productName;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
    public String getProductName(){return this.productName;}
    public int getQuantity(){return this.quantity;}
    public Double getPrice(){return this.price;}
    public String getProductType(){return this.productType;}
    public int getStock(){return this.stock;}
    public String getProductId(){return this.productId;}
    public int getCustomerId() {return customerId;}
    public Double getTotalPrice() {return totalPrice;}
    public String getEmployeeUsername() {return employeeUsername;}
    public Date getDate() {return date;}


    
    
    public int getId() {
        return id;
    }
}
