package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.requests.CategoryDto;
import com.codewithmosh.store.dtos.responses.ApiResponse;
import com.codewithmosh.store.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(@RequestBody CategoryDto categoryDto) {
        logger.info("Received request to create category: {}", categoryDto);
        try {
            ApiResponse<CategoryDto> response = categoryService.createCategory(categoryDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return new ResponseEntity<>(
                    ApiResponse.<CategoryDto>builder()
                            .success(false)
                            .message(e.getMessage())
                            .data(null)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            logger.error("Error creating category: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                    ApiResponse.<CategoryDto>builder()
                            .success(false)
                            .message("Failed to create category: " + e.getMessage())
                            .data(null)
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(@PathVariable Byte id) {
        logger.info("Received request to get category with ID: {}", id);
        ApiResponse<CategoryDto> response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
        logger.info("Received request to get all categories");
        ApiResponse<List<CategoryDto>> response = categoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(@PathVariable Byte id, @RequestBody CategoryDto categoryDto) {
        logger.info("Received request to update category with ID: {}", id);
        try {
            ApiResponse<CategoryDto> response = categoryService.updateCategory(id, categoryDto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return new ResponseEntity<>(
                    ApiResponse.<CategoryDto>builder()
                            .success(false)
                            .message(e.getMessage())
                            .data(null)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            logger.error("Error updating category: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                    ApiResponse.<CategoryDto>builder()
                            .success(false)
                            .message("Failed to update category: " + e.getMessage())
                            .data(null)
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Byte id) {
        logger.info("Received request to delete category with ID: {}", id);
        try {
            ApiResponse<Void> response = categoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception e) {
            logger.error("Error deleting category: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                    ApiResponse.<Void>builder()
                            .success(false)
                            .message("Failed to delete category: " + e.getMessage())
                            .data(null)
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}