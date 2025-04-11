package com.qima.product.controller;

import com.qima.product.domain.Product;
import com.qima.product.dto.ProductDTO;
import com.qima.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;
    private final static String DEFAULT_SORT = "id,asc";

    //TODO: Implement filter
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<ProductDTO> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean available,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = DEFAULT_SORT) String sort
    ) {
        Pageable pageable = createPageRequest(page, size, sort);
        Specification<Product> specification = buildSpecification(name, available);
        return service.list(specification, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ProductDTO> get(@PathVariable Long id) {
        return service.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return service.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private Specification<Product> buildSpecification(String name, Boolean available) {
        return Specification.where(null);
/*        List<Specification<Product>> filters = Stream.of(
                        Optional.ofNullable(name).map(ProductSpecification::hasName),
                        Optional.ofNullable(available).map(ProductSpecification::hasAvailability)
                ).filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        return filters.stream()
                .reduce(Specification.where(null), Specification::and);*/
    }

    private Pageable createPageRequest(int page, int size, String sort) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] split = StringUtils.isEmpty(sort)
                ? DEFAULT_SORT.split(",")
                : sort.split(",");
        orders.add(new Sort.Order(Sort.Direction.fromString(split[1]), split[0]));
        return PageRequest.of(page, size, Sort.by(orders));
    }
}
