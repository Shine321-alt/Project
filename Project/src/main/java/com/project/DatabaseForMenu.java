package com.project;

import java.sql.*;

public class DatabaseForMenu {
    public static void createTable(Connection con){
        try(Statement stm = con.createStatement()){

            String sql = "CREATE TABLE IF NOT EXISTS MENU("+
                        "ID INT AUTO_INCREMENT PRIMARY KEY,"+
                        "PRODUCT_ID VARCHAR(255),"+
                        "PRODUCT_NAME VARCHAR(255),"+ 
                        "PRODUCT_TYPE VARCHAR(255),"+
                        "STOCK INT,"+
                        "PRICE DOUBLE,"+
                        "STATUS VARCHAR(255),"+
                        "IMAGE VARCHAR(255),"+
                        "DATE DATE)";
            stm.execute(sql);


            ResultSet rs = con.getMetaData().getColumns(null, null, "MENU", "PRODUCT_TYPE");
            if (!rs.next()) {  
                String addColumn = "ALTER TABLE MENU ADD COLUMN PRODUCT_TYPE VARCHAR(255)";
                stm.execute(addColumn);
                System.out.println("Added column PRODUCT_TYPE successfully");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insert(Connection con, String productId
                                , String productName,String type, Double productPrice
                                , Integer stock, String status
                                , String image) {

        String sql = "INSERT INTO MENU (PRODUCT_ID ,PRODUCT_NAME ,PRODUCT_TYPE,STOCK ,PRICE ,STATUS ,IMAGE ,DATE) VALUES (? , ? , ? , ? , ? , ? , ? ,CURRENT_DATE)";
        try(PreparedStatement ppsm = con.prepareStatement(sql)){
            ppsm.setString(1, productId);
            ppsm.setString(2, productName);
            ppsm.setString(3, type);
            ppsm.setInt(4, stock);
            ppsm.setDouble(5, productPrice);
            ppsm.setString(6, status);
            ppsm.setString(7, image);
            int checkppsm = ppsm.executeUpdate();
            if(checkppsm > 0) {
                System.out.println("Menu item added success");
            }
        }catch(SQLException e){e.printStackTrace();}
    }

    public static void updateMenu(Connection conn,String PRODUCT_ID 
                                    ,String PRODUCT_NAME ,String type,int STOCK 
                                    ,double PRICE ,String STATUS 
                                    ,String IMAGE ,Date DATE){
                                        
        String sql = "UPDATE MENU SET PRODUCT_NAME = ?, PRODUCT_TYPE = ?, " +
                    "STOCK = ?, PRICE = ?, STATUS = ?, IMAGE = ?, DATE = ? WHERE PRODUCT_ID = ?";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, PRODUCT_NAME);
            pstm.setString(2, type);
            pstm.setInt(3, STOCK);
            pstm.setDouble(4, PRICE);
            pstm.setString(5, STATUS);
            pstm.setString(6, IMAGE);
            pstm.setDate(7, DATE);
            pstm.setString(8, PRODUCT_ID);
            pstm.executeUpdate();
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public static void deleteMenu(Connection con , String productId) {
            String sql = "DELETE FROM MENU WHERE PRODUCT_ID = ?";
            try(PreparedStatement pstm = con.prepareStatement(sql)){
                pstm.setString(1, productId);
                pstm.executeUpdate();
            }catch(SQLException e){e.printStackTrace();}
        }
    
        public static void getInformation(Connection con){
            String sql = "SELECT * FROM MENU";
            try(Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery(sql)){
                    while(rs.next()){
                        System.out.println("ID: " + rs.getInt("ID"));
                        System.out.println("Product ID: " + rs.getString("PRODUCT_ID"));
                        System.out.println("Product Name: " + rs.getString("PRODUCT_NAME"));
                        System.out.println("Product Type: " + rs.getString("PRODUCT_TYPE"));
                        System.out.println("Stock: " + rs.getInt("STOCK"));
                        System.out.println("Price: " + rs.getDouble("PRICE"));
                        System.out.println("Status: " + rs.getString("STATUS"));
                        System.out.println("Image: " + rs.getString("IMAGE"));
                        System.out.println("Date: " + rs.getDate("DATE"));
                    }
                }catch(SQLException e){e.printStackTrace();}

    }
    public static boolean checkProduct(Connection con, String productId) {
        String sql = "SELECT PRODUCT_ID FROM MENU WHERE PRODUCT_ID = ?";
        try (PreparedStatement ppsm = con.prepareStatement(sql)) {
            ppsm.setString(1, productId);
            try (ResultSet rs = ppsm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

