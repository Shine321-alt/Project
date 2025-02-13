package com.project;

import java.util.List;

public class Plush {
    public static void main(String[] args) {
        List<MenuItem> menu = MenuPlush.getAllMenuItem();
        for(MenuItem item : menu){
            System.out.println("^ " + item.getId() + ". " + item.getName() + "- " + item.getPrice() + " baht");
        }
    }
    
}
