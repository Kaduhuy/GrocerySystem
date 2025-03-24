package com.example.grocery_system.controller;

import com.example.grocery_system.model.GroceryCategory;
import com.example.grocery_system.model.GroceryItem;
import com.example.grocery_system.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/grocery")
public class GroceryController {

    @Autowired
    private final GroceryService groceryService;

    public GroceryController(GroceryService groceryService){
        this.groceryService = groceryService;
    }

    @GetMapping
    public ResponseEntity<List<GroceryItem>> getAllGroceryItems() {
        List<GroceryItem> items = groceryService.getAllGroceryItems();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if list is empty
        }
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroceryItem> getGroceryItemById(@PathVariable String id) {
        try {
            GroceryItem item = groceryService.getGroceryItemById(id);
            return ResponseEntity.ok(item);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // 404 Not Found if item doesn't exist
        }
    }

    @PostMapping
    public ResponseEntity<GroceryItem> addGroceryItem(@RequestBody GroceryItem newGroceryItem) {
        if (newGroceryItem.getName() == null || newGroceryItem.getPrice() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        }
        GroceryItem createdGroceryItem = groceryService.addGroceryItem(newGroceryItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroceryItem); // 201 Created
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<GroceryItem>> getItemsByCategory(@PathVariable GroceryCategory category) {
        List<GroceryItem> items = groceryService.getItemsByCategory(category);
        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found if no items in category
        }
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroceryItem> updateGroceryItem(
            @PathVariable Long id,
            @RequestBody GroceryItem updatedGroceryItem) {
        if (updatedGroceryItem.getName() == null || updatedGroceryItem.getPrice() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        }

        GroceryItem groceryItem = groceryService.updateGroceryItem(id, updatedGroceryItem);
        if (groceryItem != null) {
            return ResponseEntity.ok(groceryItem); // 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroceryItem(@PathVariable String id) {
        boolean deleted = groceryService.deleteGroceryItem(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }
}
