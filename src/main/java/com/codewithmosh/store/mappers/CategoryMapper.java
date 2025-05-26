package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.requests.CategoryDto;
import com.codewithmosh.store.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);
    void update(CategoryDto categoryDto, @MappingTarget Category category);
    List<CategoryDto> toDtoList(List<Category> categories);
}