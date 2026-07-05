package com.demo.springbootdistributedsystemdemo.mapper;

import com.demo.springbootdistributedsystemdemo.dto.ErrorResponseDTO;
import com.demo.springbootdistributedsystemdemo.dto.ProductResponseDTO;
import com.demo.springbootdistributedsystemdemo.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponseDTO map(Product byId) {
        return new ProductResponseDTO(byId.getName(), byId.getPrice(), byId.getDescription());
    }

    public ErrorResponseDTO errorResponse(String message) {
        return new ErrorResponseDTO(message);
    }
}
