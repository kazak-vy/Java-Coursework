package com.shop.controller;

import com.shop.entity.Category;
import com.shop.repository.CategoryRepository;
import com.shop.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category-list")
    public String allCategories(Model model)
    {
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryList);
        return "/categories/category-list.html";
    }

    @GetMapping("/create-category")
    public String createCategory(Model model)
    {
        model.addAttribute("category", new Category());
        return "categories/create-category.html";
    }

    @PostMapping("/create-category")
    public String addCategory(@Valid @ModelAttribute Category category, Model model)
    {
        categoryService.saveCategory(category);
        return "/categories/category-info.html";
    }


}
