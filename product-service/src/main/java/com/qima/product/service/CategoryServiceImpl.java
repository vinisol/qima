package com.qima.product.service;

import com.qima.product.dto.CategoryDTO;
import com.qima.product.mapper.CategoryMapper;
import com.qima.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryDTO> list() {
        return mapper.toDTOList(repository.findAll());
    }
}
