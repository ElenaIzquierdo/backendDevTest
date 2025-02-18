package com.example.similarproducts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
    //TODO when adding web client I had to remove the @Builder annotation
    String id;
    String name;
    Double price;
    @JsonProperty("availability")
    boolean isAvailable;

    public Product(String id, String name, double price, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
    }
}
