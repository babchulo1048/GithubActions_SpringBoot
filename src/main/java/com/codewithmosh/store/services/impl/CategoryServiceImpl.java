package com.codewithmosh.store.services.impl;

import com.codewithmosh.store.dtos.requests.CategoryDto;
import com.codewithmosh.store.dtos.responses.ApiResponse;
import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.mappers.CategoryMapper;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ApiResponse<CategoryDto> createCategory(CategoryDto categoryDto) {
        logger.info("Creating category: {}", categoryDto);
        Category category = categoryMapper.toEntity(categoryDto);
        category = categoryRepository.save(category);
        logger.info("Created category with ID: {}", category.getId());
        CategoryDto resultDto = categoryMapper.toDto(category);
        return ApiResponse.<CategoryDto>builder()
                .success(true)
                .message("Category created successfully")
                .data(resultDto)
                .build();
    }

    @Override
    public ApiResponse<CategoryDto> getCategoryById(Byte id) {
        logger.info("Retrieving category with ID: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", id);
                    return new NoSuchElementException("Category not found with ID: " + id);
                });
        CategoryDto resultDto = categoryMapper.toDto(category);
        return ApiResponse.<CategoryDto>builder()
                .success(true)
                .message("Category retrieved successfully")
                .data(resultDto)
                .build();
    }

    @Override
    public ApiResponse<List<CategoryDto>> getAllCategories() {
        logger.info("Retrieving all categories");
        List<Category> categories = categoryRepository.findAll();
        logger.info("Retrieved {} categories", categories.size());
        List<CategoryDto> categoryDtos = categoryMapper.toDtoList(categories);
        return ApiResponse.<List<CategoryDto>>builder()
                .success(true)
                .message("Categories retrieved successfully")
                .data(categoryDtos)
                .build();
    }

    @Override
    public ApiResponse<CategoryDto> updateCategory(Byte id, CategoryDto categoryDto) {
        logger.info("Updating category with ID: {}", id);
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", id);
                    return new NoSuchElementException("Category not found with ID: " + id);
                });
        categoryMapper.update(categoryDto, existingCategory);
        existingCategory = categoryRepository.save(existingCategory);
        logger.info("Updated category with ID: {}", id);
        CategoryDto resultDto = categoryMapper.toDto(existingCategory);
        return ApiResponse.<CategoryDto>builder()
                .success(true)
                .message("Category updated successfully")
                .data(resultDto)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteCategory(Byte id) {
        logger.info("Deleting category with ID: {}", id);
        if (!categoryRepository.existsById(id)) {
            logger.error("Category not found with ID: {}", id);
            throw new NoSuchElementException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
        logger.info("Deleted category with ID: {}", id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Category deleted successfully")
                .data(null)
                .build();
    }
}