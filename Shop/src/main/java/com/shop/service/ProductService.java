package com.shop.service;

import com.shop.entity.Product;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepository;

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

    public List<Product> getProductsById(List<Long> ids)
    {
        return productRepository.findAllById(ids);
    }
}


