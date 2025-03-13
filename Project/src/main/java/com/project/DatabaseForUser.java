package com.project;

import java.sql.*;

public class DatabaseForUser {

    public static void createTable(Connection con){
        String sql =    "CREATE TABLE IF NOT EXISTS EMPLOYEE("+
                        "ID INT PRIMARY KEY AUTO_INCREMENT,"+
                        "USERNAME VARCHAR(255),"+
                        "PASSWORD VARCHAR(255),"+ 
                        "QUESTION VARCHAR(255),"+
                        "ANSWER VARCHAR(255),"+
                        "EMPLOYEE VARCHAR(255))";
        try(Statement stm = con.createStatement()){stm.execute(sql);}catch(SQLException e){e.printStackTrace();}
    }

    public static void insert(Connection con, String userName, String password, String question, String answer, String employee) {

        String sql = "INSERT INTO EMPLOYEE (USERNAME, PASSWORD, QUESTION, ANSWER, EMPLOYEE) VALUES (?,?,?,?,?)";
        try(PreparedStatement ppsm = con.prepareStatement(sql)){
            ppsm.setString(1, userName);
            ppsm.setString(2, password);
            ppsm.setString(3, question);
            ppsm.setString(4, answer);
            ppsm.setString(5, employee);
            int checkppsm = ppsm.executeUpdate();
            if(checkppsm > 0) {
                System.out.println("User add success");
            }
        }catch(SQLException e){e.printStackTrace();}
    }
    public static void deleteUser(Connection con ,int id ){
        String sql = "DELETE FROM EMPLOYEE WHERE id = ?";
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
    public static void updateUser(Connection conn,int id ,String userName ,String password,String question,String answer ,String employee ){
        String sql = "UPDATE EMPLOYEE SET NAME = ?, QUESTION = ?, ANSWER = ? , EMAIL = ? , DATE = ? WHERE id = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, userName);
            pstm.setString(2, password);
            pstm.setString(3, question);
            pstm.setString(4, answer);
            pstm.setString(5, employee);
            pstm.setInt(6, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        
    }
    public static void getInformation(Connection con){
        String sql = "SELECT * FROM EMPLOYEE";  
        try(Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql)){
            while(rs.next()){
                System.out.println("ID: " + rs.getInt("ID") +  
                " \nUSERNAME:\t" + rs.getString("USERNAME") +  
                " \nPASSWORD:\t" + rs.getString("PASSWORD") + 
                " \nQUESTION:\t" + rs.getString("QUESTION") +
                " \nANSWER:\t\t"+ rs.getString("ANSWER") +
                " \nEMPLOYEE:\t" + rs.getString("EMPLOYEE"));
            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
    }

    public static String getUser(Connection con, int id){
        String sql = "SELECT USERNAME FROM EMPLOYEE WHERE ID = ?";
        try(PreparedStatement ppsm = con.prepareStatement(sql)){
            ppsm.setInt(1, id);
            try(ResultSet rs = ppsm.executeQuery()){
                if(rs.next()){
                    return rs.getString("USERNAME");
                }
            }
            return null; 
        }catch(SQLException e){
            e.printStackTrace();
            return null; 
        }
    }

    public static boolean checkEmployeeType(Connection con, String userName) {
        String sql = "SELECT EMPLOYEE FROM EMPLOYEE WHERE USERNAME = ?";
        try (PreparedStatement ppsm = con.prepareStatement(sql)) {
            ppsm.setString(1, userName);
            try (ResultSet rs = ppsm.executeQuery()) {
                if (rs.next()) {
                    String employeeType = rs.getString("EMPLOYEE");
                    return employeeType != null && employeeType.equalsIgnoreCase("ADMIN");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String getPassword(Connection con, int id){
        String sql = "SELECT PASSWORD FROM EMPLOYEE WHERE ID = ?";
        try(PreparedStatement ppsm = con.prepareStatement(sql)){
            ppsm.setInt(1, id);
            try(ResultSet rs = ppsm.executeQuery()){
                if(rs.next()){
                    return rs.getString("PASSWORD");
                }
            }
            return null; 
        }catch(SQLException e){
            e.printStackTrace();
            return null; 
        }
    }
    public static boolean checkUser(Connection con , String userName){
        String sql = "SELECT * FROM EMPLOYEE WHERE USERNAME = ? ";
        try(PreparedStatement ppsm = con.prepareStatement(sql)){
            ppsm.setString(1,userName);
            try(ResultSet rs = ppsm.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1) > 0;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkLogin(Connection con, String username, String password) {
        String sql = "SELECT * FROM EMPLOYEE WHERE USERNAME = ? AND PASSWORD = ?";
        try (PreparedStatement ppsm = con.prepareStatement(sql)) {
            ppsm.setString(1, username);
            ppsm.setString(2, password);
            try (ResultSet rs = ppsm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
