package com.qima.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qima.product.dto.ProductDTO;
import com.qima.product.security.JwtAuthFilter;
import com.qima.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthFilter.class)
})
@Import(TestSecurityConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser
    void testListProducts() throws Exception {
        ProductDTO dto = new ProductDTO();
        Page<ProductDTO> page = new PageImpl<>(List.of(dto));
        Mockito.when(productService.list(any(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/products")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void givenExistingProductId_whenGet_ThenReturnProduct() throws Exception {
        ProductDTO dto = new ProductDTO();
        Mockito.when(productService.get(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void givenNonExistingProductId_whenGet_ThenReturnNotFound() throws Exception {
        Mockito.when(productService.get(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void givenProductDto_whenPost_ThenProductCreated() throws Exception {
        ProductDTO input = buildProductDTO(null, "Product Name");
        ProductDTO output = buildProductDTO(1L, "Product Name");
        Mockito.when(productService.create(any(ProductDTO.class))).thenReturn(output);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(input.getName()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void givenProductDto_whenPut_ThenReturnProductUpdated() throws Exception {
        ProductDTO input = buildProductDTO(1L, "Updated Product Name");
        ProductDTO output = buildProductDTO(1L, "Updated Product Name");
        Mockito.when(productService.update(eq(input.getId()), any(ProductDTO.class)))
                .thenReturn(Optional.of(output));

        mockMvc.perform(put("/products/" + input.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(input.getName()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void givenNonExistingProductId_whenPut_ThenReturnNotFound() throws Exception {
        ProductDTO input = new ProductDTO();
        Mockito.when(productService.update(eq(999L), any(ProductDTO.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void givenExistingProductId_whenDelete_thenProductDeleted() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());

        Mockito.verify(productService).delete(1L);
    }

    private ProductDTO buildProductDTO(Long id, String name) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setName(name);
        return productDTO;
    }
}
