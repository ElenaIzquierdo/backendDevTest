package com.example.similarproducts.controller;

import com.example.similarproducts.dto.Product;
import com.example.similarproducts.usecase.SimilarProductsUseCase;
import org.springframework.http.MediaType;
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

    @GetMapping(path="/product/{productId}/similar", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getSimilarProducts(@PathVariable String productId) {
        return useCase.execute(productId);
    }
}
