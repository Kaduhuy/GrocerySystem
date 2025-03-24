package com.example.grocery_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class GroceryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int quantity;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private GroceryCategory category;

    public GroceryItem(){}

    public GroceryItem(Long id, String name, double price, int quantity, GroceryCategory category){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
    public GroceryItem(Long id, String name, double price, int quantity, GroceryCategory category, String imageUrl){
        this(id, name, price, quantity, category);
        this.imageUrl = imageUrl;
    }
}
