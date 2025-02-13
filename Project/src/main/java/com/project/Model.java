package com.project;

public class Model {
    public static void main(String[] args) {
        MenuItem item = new MenuItem(1, "FrideRice", 50);
        System.out.println(" menu: " + item.getName() + " price " + item.getPrice() + " baht");
    }
}
