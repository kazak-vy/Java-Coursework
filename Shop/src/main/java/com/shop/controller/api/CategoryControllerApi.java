package com.shop.controller.api;

import com.shop.entity.Category;
import com.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-categories")
public class CategoryControllerApi
{
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category-list")
    public ResponseEntity<List<Category>> allCategories()
    {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/create-category")
    public ResponseEntity<Category>  addCategory(@RequestBody Category category)
    {
        categoryService.saveCategory(category);

        return ResponseEntity.ok(category);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getProductById(@PathVariable long categoryId, Model model)
    {
         Category category = categoryService.getCategoryById(categoryId)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Category with %d id not found", categoryId))
                );

        return ResponseEntity.ok(category);
    }

    @PutMapping("/{categoryId}/edit")
    public ResponseEntity<Category> updateCategory(@PathVariable long categoryId, @RequestBody Category updatedCategory)
    {
        Category existingCategory = categoryService.getCategoryById(categoryId)
                .orElseThrow(
                        () -> new RuntimeException("Item not found")
                );

        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());

        categoryService.saveCategory(existingCategory);

        return ResponseEntity.ok(existingCategory);
    }

    @DeleteMapping("/{categoryId}/delete")
    public ResponseEntity<?> deleteCategory(@PathVariable long categoryId)
    {
        Category categoryToDelete = categoryService.getCategoryById(categoryId)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Product with %d id not found", categoryId))
                );

        categoryService.deleteCategory(categoryToDelete);

        return ResponseEntity.ok("Category " + categoryId + " deleted.");
    }

}
