package com.example.grocery_system.controller;

import com.example.grocery_system.model.Category;
import com.example.grocery_system.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category create(@RequestBody Category category){
        return categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category updateCategory){
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updateCategory.getName());
                    category.setDescription(updateCategory.getDescription());
                    return ResponseEntity.ok(categoryRepository.save(category));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!categoryRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
