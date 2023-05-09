package com.example.similarproducts.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class SimilarProductIdsClient {

    public List<Integer> getSimilarProductIds(String productId) {
        String url = "http://localhost:3001/product/" + productId + "/similarids";

        WebClient.Builder builder = WebClient.builder();

        return builder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Integer.class)
                .collectList()
                .block();

    }
}
