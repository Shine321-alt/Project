package com.project;

import java.sql.*;

public class DatabaseForUser {

    public static void createTable(Connection con){
        String sql =    "CREATE TABLE IF NOT EXISTS USERS("+
                        "ID INT PRIMARY KEY AUTO_INCREMENT,"+
                        "NAME VARCHAR(255),"+
                        "QUESTION VARCHAR(255),"+
                        "ANSWER VARCHAR(255),"+
                        "EMAIL VARCHAR(255),"+
                        "DATE VARCHAR(255))";
        try(Statement stm = con.createStatement()){stm.execute(sql);}catch(SQLException e){e.printStackTrace();}
    }

    public static void insert(Connection con,String name ,String question,String answer,String email ,String date ){
        String sql = "INSERT INTO USERS (NAME,QUESTION,ANSWER,EMAIL,DATE) VALUES (?,?,?,?,?)";
        try(PreparedStatement ppsm = con.prepareStatement(sql)){
            ppsm.setString(1,name);
            ppsm.setString(2,question);
            ppsm.setString(3,answer);
            ppsm.setString(4,email);
            ppsm.setString(5,date);
            int checkppsm = ppsm.executeUpdate();
            if(checkppsm > 0 ){
                System.out.println("User add success");
            }
        }catch(SQLException e){e.printStackTrace();}
    }
    public static void deleteUser(Connection con ,int id ){
        String sql = "DELETE FROM USERS WHERE id = ?";
        try(PreparedStatement pstm = con.prepareStatement(sql)){
            pstm.setInt(1, id);
            int check = pstm.executeUpdate();
            if(check > 0 ){
                System.out.println("Success");
            }else{
                System.out.println("false");
            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
    }
    public static void updateUser(Connection conn,int id , String name , String email,String question,String answer,String date){
        String sql = "UPDATE USERS SET NAME = ?, QUESTION = ?, ANSWER = ? , EMAIL = ? , DATE = ? WHERE id = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, name);
            pstm.setString(2, question);
            pstm.setString(3, answer);
            pstm.setString(4, email);
            pstm.setString(5, date);
            pstm.setInt(6, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    public static void getUser(Connection con){
        String sql = "SELECT * FROM USERS";
        try(Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql)){
            while(rs.next()){
                System.out.println("ID: " + rs.getInt("ID") +  
                " NAME: " + rs.getString("NAME") +  
                " EMAIL: " + rs.getString("EMAIL"));
            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
    }
}
