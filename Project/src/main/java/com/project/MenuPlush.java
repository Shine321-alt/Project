package com.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MenuPlush {
    private static final String URL = "jdbc:sqlite:restaurant.db";

    public static List<MenuItem> getAllMenuItem(){
        List<MenuItem> menu = new ArrayList<>();
        String sql = "SELECT * FROM menu";

        try (Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

                while (rs.next()){
                    menu.add(new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                    ));
                }
            } catch (SQLException e){
                e.printStackTrace();

            }
            return menu;
    }
    
}
