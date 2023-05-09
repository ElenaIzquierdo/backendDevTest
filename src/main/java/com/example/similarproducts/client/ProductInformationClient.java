package com.example.similarproducts.client;

import com.example.similarproducts.dto.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;


@Service
public class ProductInformationClient {

    public Optional<Product> getProductInformation(String productId) {
        String url = "http://localhost:3001/product/" + productId;

        WebClient.Builder builder = WebClient.builder();
        Product response = builder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Product.class)
                .block();

        if(response == null) {
            return Optional.empty();
        }

        return Optional.of(response);
    }
}
