package com.example.grocery_system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroceryItem {
    private String id;
    private String name;
    private int price;
    private String imageUrl;

    public GroceryItem(){}

    public GroceryItem(String id, String name, int price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public GroceryItem(String id, String name, int price, String imageUrl){
        this(id, name, price);
        this.imageUrl = imageUrl;
    }
}
