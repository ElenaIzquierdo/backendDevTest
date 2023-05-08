package com.example.similarproducts.usecase;

import com.example.similarproducts.dto.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimilarProductsUseCase {

    public List<Product> execute(String productId) {
        throw new UnsupportedOperationException("Implement me!");
    }
}
