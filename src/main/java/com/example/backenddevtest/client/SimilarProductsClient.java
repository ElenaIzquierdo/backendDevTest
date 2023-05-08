package com.example.backenddevtest.client;

import com.example.backenddevtest.exceptions.ClientNullResponseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SimilarProductsClient {
    public List<Integer> getSimilarProductsForProductId(String productId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:3001/product/" + productId + "/similarids";

        Integer[] response = restTemplate.getForObject(url, Integer[].class);

        if(response == null) {
            throw new ClientNullResponseException("Invalid similar product ids response");
        }
        return List.of(response);

    }
}
