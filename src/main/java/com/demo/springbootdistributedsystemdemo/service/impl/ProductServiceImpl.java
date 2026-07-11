package com.demo.springbootdistributedsystemdemo.service.impl;

import com.demo.springbootdistributedsystemdemo.dto.ProductRequestDTO;
import com.demo.springbootdistributedsystemdemo.entity.Product;
import com.demo.springbootdistributedsystemdemo.exception.ProductNotFoundException;
import com.demo.springbootdistributedsystemdemo.repository.ProductRepository;
import com.demo.springbootdistributedsystemdemo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@CacheConfig(cacheNames = "products")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(key = "#id")
    public Product getById(String id) throws ProductNotFoundException {
        log.info("********************Method Called***********************");
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

    @Override
    public Product update(String id, ProductRequestDTO productRequestDTO) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        Product fromDb = byId.get();
        fromDb.setName(productRequestDTO.name());
        fromDb.setPrice(productRequestDTO.price());
        fromDb.setDescription(productRequestDTO.description());
        return productRepository.save(fromDb);
    }

    @Override
    @CacheEvict(key = "#id")
    public String delete(String id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
        return id;
    }
}
