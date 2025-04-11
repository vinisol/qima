package com.qima.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CategoryDTO {

    private Long id;
    private String name;
    private Long parentId;
    private String categoryPath;
    private Instant createdAt;
    private Instant updatedAt;
}
