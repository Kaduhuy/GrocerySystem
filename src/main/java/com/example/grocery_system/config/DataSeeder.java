package com.example.grocery_system.config;

import com.example.grocery_system.model.Category;
import com.example.grocery_system.model.GroceryItem;
import com.example.grocery_system.repository.CategoryRepository;
import com.example.grocery_system.service.GroceryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedCategories(CategoryRepository categoryRepository, GroceryService groceryService) {
        return args -> {
            List<String> defaultCategories = List.of("FRUITS", "VEGETABLES", "DAIRY", "BAKERY", "SNACKS");

            for (String name : defaultCategories) {
                boolean exists = categoryRepository.findByName(name).isPresent();
                if (!exists) {
                    Category category = new Category();
                    category.setName(name);
                    category.setDescription(name.toLowerCase().charAt(0) + name.toLowerCase().substring(1) + " category");
                    categoryRepository.save(category);
                }
            }

            groceryService.seedGroceryItems();
        };
    }

}
