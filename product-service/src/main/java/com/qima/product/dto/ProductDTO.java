package com.qima.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean available;
    private String sku;
    private Long categoryId;
    private String categoryPath;
    private Instant createdAt;
    private Instant updatedAt;
}
