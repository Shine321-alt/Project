package com.project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public static void updateMenu(Connection conn, String productId,        //อัพเดตข้อมูลในฐานข้อมูล
                                 String productName, String type
                                , int stock, double price
                                , String status, String image, Date date) {
        String sql = "UPDATE MENU SET PRODUCT_NAME = ?, PRODUCT_TYPE = ?, " +
                    "STOCK = ?, PRICE = ?, STATUS = ?, IMAGE = ?, DATE = ? WHERE PRODUCT_ID = ?";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, productName);
            pstm.setString(2, type);
            pstm.setInt(3, stock);
            pstm.setDouble(4, price);
            pstm.setString(5, stock == 0 ? "unavailable" : "available"); // ถ้าเป็นจริงให้เปลี่ยนเป็น unavailable
            pstm.setString(6, image);
            pstm.setDate(7, date != null ? date : new Date(System.currentTimeMillis())); // ใช้วันที่ปัจจุบันถ้า date เป็น null
            pstm.setString(8, productId);

            if (pstm.executeUpdate() > 0) {
                System.out.println("Menu item updated successfully");
            } else {
                System.out.println("Menu item not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStock(Connection con, String productId, int newStock) {
        String sql = "UPDATE MENU SET STOCK = ? WHERE PRODUCT_ID = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, newStock);
            pstm.setString(2, productId);
            int result = pstm.executeUpdate();
            if (result > 0) {
                System.out.println("Stock updated successfully");
            } else {
                System.out.println("Product not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStatus(Connection con, String productId, String status) {
        String sql = "UPDATE MENU SET STATUS =? WHERE PRODUCT_ID =?";
        try (PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setString(1, status);
            pstm.setString(2, productId);
            int result = pstm.executeUpdate();
            if(result > 0){
                System.out.println("Status updated successfully");
            }else{
                System.out.println("Product not found");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteMenu(Connection con , String productId) { //ลบข้อมูลในฐานข้อมูล
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

    public static boolean checkProduct(Connection con, String productId) { //เช็คว่าในฐานข้อมูลมีสินค้าอยู่ไหม
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

    public static int checkStock(Connection con, String productId) {
        String sql = "SELECT STOCK FROM MENU WHERE PRODUCT_ID = ?";
        try (PreparedStatement ppsm = con.prepareStatement(sql)) {
            ppsm.setString(1, productId);  
            try (ResultSet rs = ppsm.executeQuery()) {  
                if (rs.next()) {
                    return rs.getInt("STOCK");
                }
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean checkStatus(Connection con , String productId){
        String sql = "SELECT STATUS FROM MENU WHERE PRODUCT_ID =?";
        try(PreparedStatement ppsm = con.prepareStatement(sql)){
            ppsm.setString(1, productId);
            try(ResultSet rs = ppsm.executeQuery()){
                if(rs.next())
                    return "available".equalsIgnoreCase(rs.getString("STATUS"));
                
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void resetDatabase(Connection con) {
        String deleteDataSQL = "DELETE FROM MENU"; 
        String resetAutoIncrementSQL = "ALTER TABLE MENU AUTO_INCREMENT = 1"; 
        
        try (Statement stm = con.createStatement()) {
            // ลบข้อมูลทั้งหมด
            stm.executeUpdate(deleteDataSQL);
            System.out.println("All data deleted successfully");
        
            // รีเซ็ตค่า AUTO_INCREMENT
            stm.executeUpdate(resetAutoIncrementSQL);
            System.out.println("AUTO_INCREMENT reset successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

