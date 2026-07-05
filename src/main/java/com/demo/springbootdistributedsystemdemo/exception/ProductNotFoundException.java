package com.demo.springbootdistributedsystemdemo.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String id) {
        super(id);
    }
}
