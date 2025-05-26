package com.codewithmosh.store.dtos.responses;

import com.codewithmosh.store.dtos.requests.ProductDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private ProductDto product;
    private int quantity;
    private BigDecimal totalPrice;
}