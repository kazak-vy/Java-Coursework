package com.shop.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.shop.entity.Product;
import com.shop.service.CartService;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.shop.utils.UserUtils.getUserId;

@Controller
@RequestMapping("/products")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartService cartService;

    @GetMapping("/product-list")
    public String allProducts(Model model)
    {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "products/product-list.html";
    }

    @GetMapping("/create-product")
    public String createProductForm(Model model)
    {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("product", new Product());
        return "products/create-product.html";
    }

    @PostMapping("/create-product")
    public String addProduct(@Valid @ModelAttribute Product product, Model model)
    {
        product.setSellerId(getUserId());
        product.setCreationDate(LocalDateTime.now());
        product.setSold(true);

        productService.saveProduct(product);

        return "products/product-info.html";
    }

    @GetMapping("/{productId}")
    public String getProductById(@PathVariable long productId, Model model)
    {
        Product product = productService.getProductById(productId)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Product with %d id not found", productId))
                );
        model.addAttribute("product", product);
        return "products/product-info.html";
    }

    @GetMapping("/{productId}/edit")
    public String update(@PathVariable long productId, Model model)
    {
        Product product = productService.getProductById(productId)
                .orElseThrow(
                        () -> new RuntimeException("Item not found")
                );
        model.addAttribute("product", product);
        return "products/product-update.html";
    }

    @PostMapping("/{productId}/edit")
    public String updateProduct(@PathVariable long productId, @ModelAttribute Product product, Model model, HttpServletRequest request)
    {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken);

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

        return "redirect:/products/product-list";
    }

    @GetMapping("/{productId}/delete")
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
