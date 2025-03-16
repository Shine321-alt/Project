package com.project;

import java.sql.*;

public class DatabaseForCustomer {
    public static void createTable(Connection con){
        try(Statement stm = con.createStatement()){

            String sql = "CREATE TABLE IF NOT EXISTS CUSTOMER(" +
                        "ID INT AUTO_INCREMENT PRIMARY KEY," +
                        "CUSTOMER_ID INT," +
                        "PRODUCT_ID VARCHAR(255)," + 
                        "PRODUCT_NAME VARCHAR(255)," +
                        "QUANTITY INT," +
                        "PRICE DOUBLE," +
                        "DATE DATE," + 
                        "EMPLOYEE_USERNAME VARCHAR(255))"; 
            stm.execute(sql);

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertData(Connection con, int customerId, String productId,
                                String productName, int quantity,
                                double price, String employeeUsername) {
        try (PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO CUSTOMER (CUSTOMER_ID, PRODUCT_ID, PRODUCT_NAME, QUANTITY, PRICE, DATE, EMPLOYEE_USERNAME) " +
                "VALUES (?, ?, ?, ?, ?, CURRENT_DATE, ?)")){
            pstm.setInt(1, customerId);
            pstm.setString(2, productId);
            pstm.setString(3, productName);
            pstm.setInt(4, quantity);
            pstm.setDouble(5, price);   
            pstm.setString(6, employeeUsername);
            int result = pstm.executeUpdate();
            if (result > 0) {
                System.out.println("Customer order added successfully");
            } else {
                System.out.println("Failed to add customer order");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllData(Connection con) {
        String sql = "DELETE FROM CUSTOMER";
        try (Statement stm = con.createStatement()) {
            stm.execute(sql);
            System.out.println("All data deleted from CUSTOMER table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteData(Connection con, int customerId) {
        String sql = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, customerId);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully deleted " + rowsAffected + " records for customer ID: " + customerId);
            } else {
                System.out.println("No records found for customer ID: " + customerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting customer data: " + e.getMessage());
        }
    }

    public static void deleteData(Connection con, int customerId, int id) {
        String sql = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ? AND ID = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, customerId);
            pstm.setInt(2, id);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully deleted item ID: " + id + " for customer ID: " + customerId);
            } else {
                System.out.println("No record found for customer ID: " + customerId + " and item ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting customer data: " + e.getMessage());
        }
    }

    public static void getInformation(Connection con) {  
        try (Statement stm = con.createStatement();  
             ResultSet rs = stm.executeQuery("SELECT * FROM CUSTOMER")) {
                if(!rs.next()){
                    System.out.println("No record found");
                }
                while(rs.next()) {
                    System.out.println("\n=== Customer Record ===");
                    System.out.println("ID: " + rs.getInt("ID"));
                    System.out.println("Customer ID: " + rs.getInt("CUSTOMER_ID"));
                    System.out.println("Product ID: " + rs.getString("PRODUCT_ID"));
                    System.out.println("Product Name: " + rs.getString("PRODUCT_NAME"));
                    System.out.println("Quantity: " + rs.getInt("QUANTITY"));
                    System.out.println("Price: " + rs.getDouble("PRICE"));
                    System.out.println("Date: " + rs.getDate("DATE"));
                    System.out.println("Employee: " + rs.getString("EMPLOYEE_USERNAME"));
                    System.out.println("=====================");
                }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
