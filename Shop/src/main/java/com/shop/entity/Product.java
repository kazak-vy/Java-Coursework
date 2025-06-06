package com.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="products")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @JoinColumn(name = "seller_id", nullable = false)
    private String sellerId;

    @JoinColumn(name = "category_id", nullable = false)
    private long categoryId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "condition", nullable = false)
    private String condition;

    @Column(name = "photoUrl")
    private String photoUrl;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;
}



