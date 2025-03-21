package com.example.grocery_system.controller;

import com.example.grocery_system.service.GroceryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final GroceryService groceryService;

    public HomeController(GroceryService groceryService){
        this.groceryService = groceryService;
    }
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("groceries", groceryService.getAllGroceryItems());
        return "home";
    }
}
