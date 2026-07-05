package com.demo.springbootdistributedsystemdemo.service;

import com.demo.springbootdistributedsystemdemo.dto.ProductRequestDTO;
import com.demo.springbootdistributedsystemdemo.entity.Product;
import com.demo.springbootdistributedsystemdemo.exception.ProductNotFoundException;

public interface ProductService {
    Product getById(String id) throws ProductNotFoundException;

    Product create(ProductRequestDTO productRequestDTO);
}
