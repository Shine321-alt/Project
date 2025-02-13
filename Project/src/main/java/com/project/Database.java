package com.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqliter:estaurant.db"; 

    public static void createTable(){
        String sql = "CREATE TABLE IF NOT EXIT meue ("   
                   +"id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   +"name TEXT NOT NULL, "
                   +"prive REAL NOT NULL)";
                   
    try (Connection conn = DriverManager.getConnection(URL);
    Statement stmt = conn.createStatement()){
        stmt.execute(sql);
        System.out.println("Menu table created successfully");

    } catch (SQLException e){
        e.printStackTrace();
    }
    
}

public static void addMenuItem(String name, double price) {
    String sql = "INSET INTO menu(name,price) VALUES (?, ?)";

    try (Connection conn = DriverManager.getConnection(URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, name);
        pstmt.setDouble(2, price);
        pstmt.executeUpdate();
        System.out.println("Add menu: " + name);
    } catch (SQLException e){
        e.printStackTrace();
    }
}

public static void getMenuItems() {
    String sql = "SELECT * FROM menu";
    
    try(Connection conn = DriverManager.getConnection(URL);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){

        while (rs.next()){
            System.out.println("ID" + rs.getInt("id") + ". "
                                + rs.getString("name") + " - "
                                + rs.getDouble("price") + " baht");

        }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}