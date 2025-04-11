package com.qima.product.mapper;

import com.qima.product.domain.Category;
import com.qima.product.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "categoryPath", expression = "java(buildCategoryPath(category))")
    @Mapping(target = "parentId", source = "category.parent.id")
    CategoryDTO toDTO(Category category);

    List<CategoryDTO> toDTOList(List<Category> categories);

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
