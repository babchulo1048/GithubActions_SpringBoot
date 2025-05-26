package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.requests.ProductDto;
import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category.id", source = "categoryId")
    Product toEntity(ProductDto productDto);

    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDto(Product product);

    @Mapping(target = "category.id", source = "categoryId")
    void update(ProductDto productDto, @MappingTarget Product product);

    List<ProductDto> toDtoList(List<Product> products);
}