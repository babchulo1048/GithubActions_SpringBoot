package com.codewithmosh.store.services.impl;

import com.codewithmosh.store.dtos.requests.ProductDto;
import com.codewithmosh.store.dtos.responses.ApiResponse;
import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.ProductRepository;
import com.codewithmosh.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ApiResponse<ProductDto> createProduct(ProductDto productDto) {
        logger.info("Creating product: {}", productDto);
        Product product = productMapper.toEntity(productDto);
        // Ensure category is set
        if (productDto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(productDto.getCategoryId());
            product.setCategory(category);
        }
        product = productRepository.save(product);
        logger.info("Created product with ID: {}", product.getId());
        ProductDto resultDto = productMapper.toDto(product);
        return ApiResponse.<ProductDto>builder()
                .success(true)
                .message("Product created successfully")
                .data(resultDto)
                .build();
    }

    @Override
    public ApiResponse<ProductDto> getProductById(Long id) {
        logger.info("Retrieving product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", id);
                    return new NoSuchElementException("Product not found with ID: " + id);
                });
        ProductDto resultDto = productMapper.toDto(product);
        return ApiResponse.<ProductDto>builder()
                .success(true)
                .message("Product retrieved successfully")
                .data(resultDto)
                .build();
    }

    @Override
    public ApiResponse<List<ProductDto>> getAllProducts() {
        logger.info("Retrieving all products");
        List<Product> products = productRepository.findAllWithCategory();
        logger.info("Retrieved {} products", products.size());
        List<ProductDto> productDtos = productMapper.toDtoList(products);
        logger.info("Mapped {} DTOs", productDtos.size());
        return ApiResponse.<List<ProductDto>>builder()
                .success(true)
                .message("Products retrieved successfully")
                .data(productDtos)
                .build();
    }

    @Override
    public ApiResponse<List<ProductDto>> getProductsByCategory(Byte categoryId) {
        logger.info("Retrieving products for category ID: {}", categoryId);
        List<Product> products = productRepository.findByCategoryId(categoryId);
        logger.info("Retrieved {} products for category ID: {}", products.size(), categoryId);
        List<ProductDto> productDtos = productMapper.toDtoList(products);
        logger.info("Mapped {} DTOs", productDtos.size());
        return ApiResponse.<List<ProductDto>>builder()
                .success(true)
                .message("Products retrieved successfully for category ID: " + categoryId)
                .data(productDtos)
                .build();
    }

    @Override
    public ApiResponse<ProductDto> updateProduct(Long id, ProductDto productDto) {
        logger.info("Updating product with ID: {}", id);
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", id);
                    return new NoSuchElementException("Product not found with ID: " + id);
                });
        productMapper.update(productDto, existingProduct);
        // Update category if provided
        if (productDto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(productDto.getCategoryId());
            existingProduct.setCategory(category);
        }
        existingProduct = productRepository.save(existingProduct);
        logger.info("Updated product with ID: {}", id);
        ProductDto resultDto = productMapper.toDto(existingProduct);
        return ApiResponse.<ProductDto>builder()
                .success(true)
                .message("Product updated successfully")
                .data(resultDto)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteProduct(Long id) {
        logger.info("Deleting product with ID: {}", id);
        if (!productRepository.existsById(id)) {
            logger.error("Product not found with ID: {}", id);
            throw new NoSuchElementException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
        logger.info("Deleted product with ID: {}", id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Product deleted successfully")
                .data(null)
                .build();
    }
}