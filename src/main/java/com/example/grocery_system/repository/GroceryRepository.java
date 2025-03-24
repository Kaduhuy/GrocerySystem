package com.example.grocery_system.repository;

import com.example.grocery_system.model.GroceryCategory;
import com.example.grocery_system.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroceryRepository extends JpaRepository<GroceryItem, String> {

    List<GroceryItem> findByCategory(GroceryCategory category);

    GroceryItem findByName(String name);
}
