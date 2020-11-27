package com.example.myapplication;

import java.util.ArrayList;

public class InventoryCategory {
    private String name;

    public InventoryCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<String> categories() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Food");
        arr.add("Appliances");
        arr.add("Maintenance dues");
        return arr;
    }
}
