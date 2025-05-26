package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.requests.ProductDto;
import com.codewithmosh.store.dtos.responses.ApiResponse;
import com.codewithmosh.store.services.ProductService;
//import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @PostMapping
//    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody ProductDto productDto) {
//        logger.info("Received request to create product: {}", productDto);
//        ApiResponse<ProductDto> response = productService.createProduct(productDto);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("Received request to create product: {}", productDto);
        ApiResponse<ProductDto> response = productService.createProduct(productDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable Long id) {
        logger.info("Received request to get product with ID: {}", id);
        ApiResponse<ProductDto> response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts() {
        logger.info("Received request to get all products");
        ApiResponse<List<ProductDto>> response = productService.getAllProducts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategory(@PathVariable Byte categoryId) {
        logger.info("Received request to get products for category ID: {}", categoryId);
        ApiResponse<List<ProductDto>> response = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        logger.info("Received request to update product with ID: {}", id);
        ApiResponse<ProductDto> response = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        logger.info("Received request to delete product with ID: {}", id);
        ApiResponse<Void> response = productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}