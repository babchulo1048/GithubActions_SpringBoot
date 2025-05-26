package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.requests.CategoryDto;
import com.codewithmosh.store.dtos.responses.ApiResponse;

import java.util.List;

public interface CategoryService {
    ApiResponse<CategoryDto> createCategory(CategoryDto categoryDto);
    ApiResponse<CategoryDto> getCategoryById(Byte id);
    ApiResponse<List<CategoryDto>> getAllCategories();
    ApiResponse<CategoryDto> updateCategory(Byte id, CategoryDto categoryDto);
    ApiResponse<Void> deleteCategory(Byte id);
}