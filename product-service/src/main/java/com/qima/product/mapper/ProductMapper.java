package com.qima.product.mapper;

import com.qima.product.domain.Category;
import com.qima.product.domain.Product;
import com.qima.product.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryPath", expression = "java(buildCategoryPath(product.getCategory()))")
    @Mapping(target = "categoryId", source = "product.category.id")
    ProductDTO toDTO(Product product);

    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDTO dto);

    default String buildCategoryPath(Category category) {
        if (category == null) {
            return null;
        }
        List<String> pathParts = new ArrayList<>();
        Category current = category;
        while (current != null) {
            pathParts.add(current.getName());
            current = current.getParent();
        }
        Collections.reverse(pathParts);
        return String.join(" > ", pathParts);
    }
}
