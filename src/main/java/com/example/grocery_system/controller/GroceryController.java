package com.example.grocery_system.controller;

import com.example.grocery_system.model.GroceryItem;
import com.example.grocery_system.service.GroceryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/grocery")
public class GroceryController {
    private final GroceryService groceryService;

    public GroceryController(GroceryService groceryService){
        this.groceryService = groceryService;
    }

    @GetMapping
    public List<GroceryItem> getAllGroceryItems(){
        return groceryService.getAllGroceryItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroceryItem> getGroceryItemById(@PathVariable String id){
        GroceryItem item = groceryService.getGroceryItemById(id);
        if(item != null){
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<GroceryItem> addGroceryItem(@RequestBody GroceryItem newGroceryItem){
        GroceryItem createdGroceryItem = groceryService.addGroceryItem(newGroceryItem);
        return new ResponseEntity<>(createdGroceryItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroceryItem> updateGroceryItem(
            @PathVariable String id,
            @RequestBody GroceryItem updatedGroceryItem){
        GroceryItem groceryItem = groceryService.updateGroceryItem(id, updatedGroceryItem);
        if(groceryItem != null){
            return ResponseEntity.ok(groceryItem);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroceryItem(@PathVariable String id){
        boolean deleted = groceryService.deleteGroceryItem(id);
        if(deleted){
            return ResponseEntity.noContent().build(); //204
        }
        return ResponseEntity.notFound().build();
    }
}
