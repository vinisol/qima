package com.qima.product.service;

import com.qima.product.domain.Category;
import com.qima.product.domain.Product;
import com.qima.product.dto.ProductDTO;
import com.qima.product.mapper.ProductMapper;
import com.qima.product.repository.CategoryRepository;
import com.qima.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDTO create(ProductDTO dto) {
        Product product = mapper.toEntity(dto);
        setCategory(product, dto.getCategoryId());
        return mapper.toDTO(repository.save(product));
    }

    @Override
    public Optional<ProductDTO> update(Long id, ProductDTO dto) {
        Product product = repository.findById(id).orElse(null);
        if (product == null) {
            return Optional.empty();
        }
        BeanUtils.copyProperties(dto, product, "id", "createdAt");
        setCategory(product, dto.getCategoryId());
        return Optional.ofNullable(mapper.toDTO(repository.save(product)));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<ProductDTO> get(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Page<ProductDTO> list(Specification<Product> spec, Pageable pageable) {
        return repository.findAll(spec, pageable).map(mapper::toDTO);
    }

    private void setCategory(Product product, Long categoryId) {
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            product.setCategory(category);
        }
    }
}
