package com.demo.springbootdistributedsystemdemo.service.impl;

import com.demo.springbootdistributedsystemdemo.dto.ProductRequestDTO;
import com.demo.springbootdistributedsystemdemo.entity.Product;
import com.demo.springbootdistributedsystemdemo.exception.ProductNotFoundException;
import com.demo.springbootdistributedsystemdemo.repository.ProductRepository;
import com.demo.springbootdistributedsystemdemo.service.ProductService;
import com.demo.springbootdistributedsystemdemo.util.KeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public ProductServiceImpl(ProductRepository productRepository, @Qualifier("sbRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getById(String id) throws ProductNotFoundException {
        String productKey = KeyGenerator.getProductKey(id);
        try {
            Object o = redisTemplate.opsForValue().get(productKey);
            if (o instanceof Product product) {
                log.info("Product from cache:{}", id);
                return product;
            }
        } catch (Exception e) {
            log.warn("Redis unavailable, falling back to DB", e);
        }
        Optional<Product> byId = productRepository.findById(id);
        try {
            byId.ifPresent(product -> redisTemplate.opsForValue().set(productKey, product, Duration.ofMinutes(2)));
        } catch(Exception e) {
            log.warn("Redis is unavailable", e);
        }
        log.info("Product from DB:{}", id);
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
        Product saved = productRepository.save(fromDb);
        try {
            String productKey = KeyGenerator.getProductKey(saved.getId());
            redisTemplate.delete(productKey);
        } catch (Exception ex) {
            log.warn("Redis is unavailable", ex);
        }
        return saved;
    }
}
