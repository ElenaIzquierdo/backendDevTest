package com.example.similarproducts.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class SimilarProductIdsClient {

    @Cacheable("similarProducts")
    public List<Integer> getSimilarProductIds(String productId) {
        String url = "http://host.docker.internal:3001/product/" + productId + "/similarids";
        try {
            log.info(String.format("Starting request to %s", url));
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
        } catch(WebClientRequestException exception) {
            String message = String.format("Call to %s failed due to time out", url);
            log.error(message);
            throw new RuntimeException(message);
        }

    }
}
