package com.qima.product.service;

import com.qima.product.domain.Category;
import com.qima.product.domain.Product;
import com.qima.product.dto.ProductDTO;
import com.qima.product.mapper.ProductMapper;
import com.qima.product.repository.CategoryRepository;
import com.qima.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenProduct_whenCreate_thenProductCreated() {
        ProductDTO dto = buildProductDTO(1L, "product");
        Long categoryId = 1L;
        dto.setCategoryId(categoryId);
        Product product = buildProduct(1L, "product");
        Category category = new Category();

        when(productMapper.toEntity(dto)).thenReturn(product);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(dto);

        ProductDTO result = productService.create(dto);

        assertEquals(dto, result);
        verify(productRepository).save(product);
        verify(productMapper).toEntity(dto);
        verify(productMapper).toDTO(product);
    }

    @Test
    void givenExistingProduct_whenUpdate_thenProductUpdated() {
        Product existing = buildProduct(1L, "product");
        ProductDTO dto = buildProductDTO(1L, "updated product");
        Long categoryId = 2L;
        dto.setCategoryId(categoryId);
        Category category = new Category();

        when(productRepository.findById(existing.getId())).thenReturn(Optional.of(existing));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(existing);
        when(productMapper.toDTO(existing)).thenReturn(dto);

        Optional<ProductDTO> result = productService.update(existing.getId(), dto);

        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void givenNonExistingProduct_whenUpdate_thenReturnEmpty() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<ProductDTO> result = productService.update(999L, new ProductDTO());

        assertFalse(result.isPresent());
        verify(productRepository, never()).save(any());
    }

    @Test
    void givenExistingProductId_whenDelete_ThenProductDeleted() {
        productService.delete(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void givenExistingProduct_whenFindById_thenReturnProduct() {
        Product product = buildProduct(1L, "product");
        ProductDTO dto = buildProductDTO(1L, "product");

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(dto);

        Optional<ProductDTO> result = productService.get(product.getId());

        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void givenNonExistingProduct_whenFindById_thenReturnEmpty() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<ProductDTO> result = productService.get(2L);

        assertFalse(result.isPresent());
    }

    private Product buildProduct(Long id, String name) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        return product;
    }

    private ProductDTO buildProductDTO(Long id, String name) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setName(name);
        return productDTO;
    }
}
