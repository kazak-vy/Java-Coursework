package com.shop.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.shop.model.Category;
import com.shop.model.Product;
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

    @GetMapping("/product-list")
    public String allProducts(Model model)
    {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "product-list.html";
    }

    @GetMapping("/category-list")
    public String allCategories(Model model)
    {
        List<Category> categoryList = productService.getAllCategories();
        model.addAttribute("categories", categoryList);
        return "category-list.html";
    }

    @GetMapping("/create-product")
    public String createProductForm(Model model)
    {
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("product", new Product());
        return "create-product.html";
    }

    @GetMapping("/create-category")
    public String createCategory(Model model)
    {
        model.addAttribute("category", new Category());
        return "create-category.html";
    }

    @PostMapping("/create-product")
    public String addProduct(@Valid @ModelAttribute Product product, Model model)
    {
        product.setSellerId(1);
        product.setCreationDate(LocalDateTime.now());
        product.setSold(true);

        productService.saveProduct(product);

        return "product-info.html";
    }

    @PostMapping("/create-category")
    public String addCategory(@Valid @ModelAttribute Category category, Model model)
    {
        productService.saveCategory(category);
        return "category-info.html";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable long id, Model model)
    {
        Product product = productService.getProductById(id)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Product with %d id not found", id))
                );
        model.addAttribute("product", product);
        return "product-info.html";
    }

    @GetMapping("{id}/edit")
    public String update(@PathVariable long id, Model model)
    {
        Product product = productService.getProductById(id)
                .orElseThrow(
                        () -> new RuntimeException("Item not found")
                );
        model.addAttribute("product", product);
        return "product-update.html";
    }

    @PostMapping("{id}/edit")
    public String updateProduct(@PathVariable long id, @ModelAttribute Product product, Model model)
    {
        Product updatedProduct = productService.getProductById(id)
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

        return "redirect:/{id}";
    }


    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable long id)
    {
        Product productToDelete = productService.getProductById(id)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Product with %d id not found", id))
                );

        productService.deleteProduct(productToDelete);

        return "redirect:/product-list";
    }

}
