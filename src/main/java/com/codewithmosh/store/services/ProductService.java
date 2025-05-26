package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.requests.ProductDto;
import com.codewithmosh.store.dtos.responses.ApiResponse;

import java.util.List;

public interface ProductService {
    ApiResponse<ProductDto> createProduct(ProductDto productDto);
    ApiResponse<ProductDto> getProductById(Long id);
    ApiResponse<List<ProductDto>> getAllProducts();
    ApiResponse<List<ProductDto>> getProductsByCategory(Byte categoryId);
    ApiResponse<ProductDto> updateProduct(Long id, ProductDto productDto);
    ApiResponse<Void> deleteProduct(Long id);
}