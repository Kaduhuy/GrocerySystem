package com.example.grocery_system.controller;

import com.example.grocery_system.model.GroceryItem;
import com.example.grocery_system.repository.CategoryRepository;
import com.example.grocery_system.service.GroceryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private final GroceryService groceryService;
    private final CategoryRepository categoryRepository;

    public HomeController(GroceryService groceryService, CategoryRepository categoryRepository){
        this.groceryService = groceryService;
        this.categoryRepository = categoryRepository;
    }
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("groceries", groceryService.getAllGroceryItems());
        return "home";
    }

    @GetMapping("/groceries/add")
    public String showAddGroceryForm(Model model) {
        model.addAttribute("groceryItem", new GroceryItem());
        model.addAttribute("categories", categoryRepository.findAll());
        return "add-grocery";
    }

    @PostMapping("/groceries/add")
    public String addGrocery(@ModelAttribute GroceryItem groceryItem) {
        groceryService.addGroceryItem(groceryItem);
        return "redirect:/home"; // Redirects back to homepage after adding
    }

    @GetMapping("/groceries/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        GroceryItem item = groceryService.getGroceryItemById(id.toString());
        model.addAttribute("groceryItem", item);
        model.addAttribute("categories", categoryRepository.findAll());
        return "edit-grocery";
    }

    @PostMapping("/groceries/{id}/edit")
    public String updateGrocery(@PathVariable Long id, @ModelAttribute GroceryItem groceryItem) {
        groceryService.updateGroceryItem(id, groceryItem);
        return "redirect:/home";
    }

    @DeleteMapping("/groceries/{id}")
    public String deleteGrocery(@PathVariable Long id) {
        groceryService.deleteGroceryItem(id);
        return "redirect:/home";  // reloads the home page after deletion
    }
}
