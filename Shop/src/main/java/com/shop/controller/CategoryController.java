package com.shop.controller;

import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());

        categoryService.saveCategory(newCategory);

        return "redirect:/categories/category-list";
    }

    @GetMapping("/{categoryId}/edit")
    public String update(@PathVariable long categoryId, Model model)
    {
        Category category = categoryService.getCategoryById(categoryId)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Category with %d id not found", categoryId))
                );

        model.addAttribute("category", category);
        return "categories/category-update.html";
    }

    @PostMapping("/{categoryId}/edit")
    public String updateProduct(@PathVariable long categoryId, @ModelAttribute Category category,
                                Model model, HttpServletRequest request)
    {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken);

        Category newCategory = categoryService.getCategoryById(categoryId)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Category with %d id not found", categoryId))
                );

        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());

        categoryService.saveCategory(newCategory);

        return "redirect:/categories/category-list";
    }

    @GetMapping("/{categoryId}/delete")
    public String deleteCategory(@PathVariable long categoryId, Model model)
    {
        Category category = categoryService.getCategoryById(categoryId)
                .orElseThrow(
                () -> new RuntimeException(String.format("Category with %d id not found", categoryId))
        );

        categoryService.deleteCategory(category);

        return "redirect:/categories/category-list";
    }

}
