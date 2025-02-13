package com.project;

public class MainApp{
    public static void mainapp(String[] args){
        Database.createTable();
        Database.addMenuItem("Fried Rice", 50);
        Database.addMenuItem("Pad Kaprao", 45);
        Database.getMenuItems();

    }
    
}
    