package com.example.similarproducts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private String id;
    private String name;
    private Double price;
    private boolean isAvailable;
}
