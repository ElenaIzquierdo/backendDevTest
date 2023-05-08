package com.example.similarproducts.client;

import com.example.similarproducts.dto.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProductInformationClient {

    public Optional<Product> getProductInformation(String productId) {
        throw new UnsupportedOperationException("Implement me!");
    }
}
