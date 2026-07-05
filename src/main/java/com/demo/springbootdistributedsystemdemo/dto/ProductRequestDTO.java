package com.demo.springbootdistributedsystemdemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductRequestDTO(
        @NotBlank(message = "Product name is required") String name,
        @Positive(message = "Price must be greater than zero") Double price,
        @NotBlank(message = "Description is required") String description) {
}
