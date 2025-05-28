package com.shop.service;

import com.shop.model.Category;
import com.shop.model.Product;
import com.shop.repository.CategoryRepository;
import com.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id)
    {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product)
    {
        return productRepository.save(product);
    }

    public void deleteProduct(Product product)
    {
        productRepository.delete(product);
    }

    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(long id)
    {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category)
    {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Category category)
    {
        categoryRepository.delete(category);
    }
}


