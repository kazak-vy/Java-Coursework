package com.shop.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController
{
    @Autowired
    private ProductService productService;

    @GetMapping("/products/product-list")
    public String allProducts(Model model)
    {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "products/product-list.html";
    }

    @GetMapping("/categories/category-list")
    public String allCategories(Model model)
    {
        List<Category> categoryList = productService.getAllCategories();
        model.addAttribute("categories", categoryList);
        return "/categories/category-list.html";
    }

    @GetMapping("/products/create-product")
    public String createProductForm(Model model)
    {
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("product", new Product());
        return "products/create-product.html";
    }

    @GetMapping("/categories/create-category")
    public String createCategory(Model model)
    {
        model.addAttribute("category", new Category());
        return "categories/create-category.html";
    }

    @PostMapping("/products/create-product")
    public String addProduct(@Valid @ModelAttribute Product product, Model model)
    {
        product.setSellerId(1);
        product.setCreationDate(LocalDateTime.now());
        product.setSold(true);

        productService.saveProduct(product);

        return "products/product-info.html";
    }

    @PostMapping("/categories/create-category")
    public String addCategory(@Valid @ModelAttribute Category category, Model model)
    {
        productService.saveCategory(category);
        return "/categories/category-info.html";
    }

    @GetMapping("/products/{productId}")
    public String getProductById(@PathVariable long productId, Model model)
    {
        Product product = productService.getProductById(productId)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Product with %d id not found", productId))
                );
        model.addAttribute("product", product);
        return "products/product-info.html";
    }

    @GetMapping("/products/{productId}/edit")
    public String update(@PathVariable long productId, Model model)
    {
        Product product = productService.getProductById(productId)
                .orElseThrow(
                        () -> new RuntimeException("Item not found")
                );
        model.addAttribute("product", product);
        return "products/product-update.html";
    }

    @PostMapping("/products/{productId}/edit")
    public String updateProduct(@PathVariable long productId, @ModelAttribute Product product, Model model)
    {
        Product updatedProduct = productService.getProductById(productId)
                .orElseThrow(
                        () -> new RuntimeException("Item not found")
                );

        updatedProduct.setName(product.getName());
        updatedProduct.setCategoryId(product.getCategoryId());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setCondition(product.getCondition());
        updatedProduct.setQuantity(product.getQuantity());

        productService.saveProduct(updatedProduct);

        return "redirect:/products/{id}";
    }


    @GetMapping("/products/{productId}/delete")
    public String deleteProduct(@PathVariable long productId)
    {
        Product productToDelete = productService.getProductById(productId)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Product with %d id not found", productId))
                );

        productService.deleteProduct(productToDelete);

        return "redirect:/products/product-list";
    }

}
