package com.shop.controller.api;

import com.shop.entity.Product;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.shop.utils.UserUtils.getUserId;

@RestController
@RequestMapping("/api-products")
public class ProductControllerApi
{
    @Autowired
    private ProductService productService;

    @GetMapping("/product-list")
    public ResponseEntity<List<Product>> allProducts()
    {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/create-product")
    public ResponseEntity<Product>  addProduct(@RequestBody Product product)
    {
        product.setSellerId(getUserId());
        product.setCreationDate(LocalDateTime.now());

        productService.saveProduct(product);

        return ResponseEntity.ok(product);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable long productId, Model model)
    {
         Product product = productService.getProductById(productId)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Product with %d id not found", productId))
                );

        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}/edit")
    public ResponseEntity<Product> updateProduct(@PathVariable long productId, @RequestBody Product updatedProduct)
    {
        Product existingProduct = productService.getProductById(productId)
                .orElseThrow(
                        () -> new RuntimeException("Item not found")
                );

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setCategoryId(updatedProduct.getCategoryId());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCondition(updatedProduct.getCondition());
        existingProduct.setQuantity(updatedProduct.getQuantity());

        productService.saveProduct(existingProduct);

        return ResponseEntity.ok(existingProduct);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable long productId)
    {
        Product productToDelete = productService.getProductById(productId)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Product with %d id not found", productId))
                );

        productService.deleteProduct(productToDelete);

        return ResponseEntity.ok("Product " + productId + " deleted.");
    }
}
