package com.example.similarproducts.client;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;

@Service
public class SimilarProductIdsClient {

    @Cacheable("similarProducts")
    public List<Integer> getSimilarProductIds(String productId) {
        String url = "http://host.docker.internal:3001/product/" + productId + "/similarids";

        HttpClient client = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(1));

        WebClient.Builder builder = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(client));

        return builder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Integer.class)
                .collectList()
                .block();

    }
}
