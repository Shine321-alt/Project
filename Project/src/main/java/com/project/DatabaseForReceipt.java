package com.project;

import java.sql.*;

public class DatabaseForReceipt {
    public static void createTable(Connection con) {
        String createTable = "CREATE TABLE IF NOT EXISTS RECEIPT (" +
                "RECEIPT_ID INT PRIMARY KEY AUTO_INCREMENT," +
                "CUSTOMER_ID INT," +
                "EMPLOYEE_USERNAME VARCHAR(255)," +
                "TOTAL_AMOUNT DOUBLE," +
                "DATE DATE)";
                
        try (Statement stm = con.createStatement()) {
            stm.execute(createTable);
            System.out.println("Table RECEIPT created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertReceipt(Connection con, int customerId, 
                                    String employeeUsername, double totalAmount) {
        String sql = "INSERT INTO RECEIPT (CUSTOMER_ID, EMPLOYEE_USERNAME, TOTAL_AMOUNT, DATE) " +
                    "VALUES (?, ?, ?, CURRENT_DATE)";
                        
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, customerId);
            pstm.setString(2, employeeUsername);
            pstm.setDouble(3, totalAmount);

            int result = pstm.executeUpdate();
            if (result > 0) {
               System.out.println("Receipt added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getReceiptId(Connection con, int customerId, String employeeUsername) {
        String sql = "SELECT RECEIPT_ID, DATE, TOTAL_AMOUNT FROM RECEIPT " +
                    "WHERE CUSTOMER_ID = ? AND EMPLOYEE_USERNAME = ? " +
                    "ORDER BY RECEIPT_ID DESC LIMIT 1";
                    
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, customerId);
            pstm.setString(2, employeeUsername);
            
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    System.out.println("------------------------");
                    System.out.println("Receipt ID: " + rs.getInt("RECEIPT_ID"));
                    System.out.println("Date: " + rs.getDate("DATE"));
                    System.out.printf("Total Amount: %.2f%n", rs.getDouble("TOTAL_AMOUNT"));
                    System.out.println("------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting receipt details: " + e.getMessage());
        }
    }
    
    public static void getInformation(Connection con) {
        String sql = "SELECT * FROM RECEIPT ORDER BY RECEIPT_ID DESC";
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql)) {
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("------------------------");
                System.out.println("Receipt ID: " + rs.getInt("RECEIPT_ID"));
                System.out.println("Customer ID: " + rs.getInt("CUSTOMER_ID"));
                System.out.println("Employee Username: " + rs.getString("EMPLOYEE_USERNAME"));
                System.out.printf("Total Amount: %.2f%n", rs.getDouble("TOTAL_AMOUNT"));
                System.out.println("Date: " + rs.getDate("DATE"));
                System.out.println("------------------------");
            }
            if (!found) {
                System.out.println("No receipts found");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving receipts: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
