package com.demo.springbootdistributedsystemdemo.service.impl;

import com.demo.springbootdistributedsystemdemo.dto.ProductRequestDTO;
import com.demo.springbootdistributedsystemdemo.entity.Product;
import com.demo.springbootdistributedsystemdemo.exception.ProductNotFoundException;
import com.demo.springbootdistributedsystemdemo.repository.ProductRepository;
import com.demo.springbootdistributedsystemdemo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getById(String id) throws ProductNotFoundException {
        Optional<Product> byId = productRepository.findById(id);
        return byId.orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product create(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.name());
        product.setDescription(productRequestDTO.description());
        product.setPrice(productRequestDTO.price());
        return productRepository.save(product);
    }
}
