package com.qima.product.service;

import com.qima.product.domain.Category;
import com.qima.product.dto.CategoryDTO;
import com.qima.product.mapper.CategoryMapper;
import com.qima.product.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListCategories() {
        List<Category> categories = List.of(buildCategory(1L, "category1"), buildCategory(2L, "category2"));
        List<CategoryDTO> dtos = List.of(buildCategoryDTO(1L, "category1"), buildCategoryDTO(2L, "category2"));

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toDTOList(categories)).thenReturn(dtos);

        List<CategoryDTO> result = categoryService.list();

        assertEquals(2, result.size());
        assertEquals(dtos, result);
        verify(categoryRepository).findAll();
        verify(categoryMapper).toDTOList(categories);
    }

    private Category buildCategory(Long id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }

    private CategoryDTO buildCategoryDTO(Long id, String name) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        categoryDTO.setName(name);
        return categoryDTO;
    }
}
