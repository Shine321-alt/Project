package com.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConection {

    private static final String JDBC_URL = "jdbc:h2:file:C:/Users/Sh/Project/Project/database/database;AUTO_SERVER=TRUE";
    private static final String user = "Admin";
    private static final String password = "Admin123";

    public static Connection gC(){
        try{
            return DriverManager.getConnection(JDBC_URL, user, password);
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
