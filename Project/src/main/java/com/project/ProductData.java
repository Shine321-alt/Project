package com.project;

import java.sql.Date;

public class ProductData {
    private Integer id;
    private String productId;
    private String productName;
    private Double productPrice;
    private Integer stock;
    private String status;
    private String image;
    private Date date;
    private String type;

    public ProductData(Integer id , String productId
                        , String productName, Double productPrice,String type
                        , Integer stock, String status
                        , String image, Date date){

        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.stock = stock;
        this.status = status;
        this.image = image;
        this.date = date;
        this.type = type;
    }

    public Integer getId(){
        return id;
    }

    public String getProductId(){
        return productId;
    }

    public Double getPrice(){
        return productPrice;
    }

    public String getProductName(){
        return productName;
    }

    public String getType(){
        return type;
    }

    public Double getProductPrice(){
        return productPrice;
    }

    public Integer getStock(){
        return stock;
    }

    public String getStatus(){
        return status;
    }

    public String getImage(){
        return image;
    }

    public Date getDate(){
        return date;
    }

}
