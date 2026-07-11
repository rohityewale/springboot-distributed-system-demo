package com.demo.springbootdistributedsystemdemo.controller;

import com.demo.springbootdistributedsystemdemo.dto.ProductRequestDTO;
import com.demo.springbootdistributedsystemdemo.dto.ProductResponseDTO;
import com.demo.springbootdistributedsystemdemo.entity.Product;
import com.demo.springbootdistributedsystemdemo.mapper.ProductMapper;
import com.demo.springbootdistributedsystemdemo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> byId(@PathVariable String id) {
        Product byId = productService.getById(id);
        return ResponseEntity.ok(productMapper.map(byId));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Validated ProductRequestDTO productRequestDTO) {
        Product product = productService.create(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productMapper.map(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable String id, @RequestBody @Validated ProductRequestDTO productRequestDTO) {
        Product product = productService.update(id, productRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.map(product));
    }
}
