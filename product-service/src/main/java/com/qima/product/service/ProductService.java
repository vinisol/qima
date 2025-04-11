package com.qima.product.service;

import com.qima.product.domain.Product;
import com.qima.product.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface ProductService {
    ProductDTO create(ProductDTO dto);

    Optional<ProductDTO> update(Long id, ProductDTO dto);

    void delete(Long id);

    Optional<ProductDTO> get(Long id);

    Page<ProductDTO> list(Specification<Product> spec, Pageable pageable);
}
