package com.example.similarproducts.controller;

import com.example.similarproducts.dto.Product;
import com.example.similarproducts.usecase.SimilarProductsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SimilarProductsController {
    private final SimilarProductsUseCase useCase;

    public SimilarProductsController(SimilarProductsUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/product/{productId}/similar")
    public List<Product> getEmployee(@PathVariable String productId) {
        return useCase.execute(productId);
    }
}
