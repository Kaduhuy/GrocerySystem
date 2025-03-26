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
    private String unit;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public GroceryItem(){}

    public GroceryItem(Long id, String name, double price, int quantity, Category category){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
    public GroceryItem(Long id, String name, double price, int quantity, Category category, String imageUrl){
        this(id, name, price, quantity, category);
        this.imageUrl = imageUrl;
    }
}
