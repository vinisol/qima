package com.qima.product.specification;

import com.qima.product.domain.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasName(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasAvailability(Boolean available) {
        return (root, query, cb) -> cb.equal(root.get("available"), available);
    }
}
