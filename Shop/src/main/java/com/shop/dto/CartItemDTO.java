package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO
{
    private long productId;
    private String productName;
    private int availableQuantity;
    private int quantity;
    private double price;
}
