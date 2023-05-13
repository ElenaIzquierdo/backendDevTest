package com.example.similarproducts.client;

import com.example.similarproducts.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Optional;


@Service
@Slf4j
public class ProductInformationClient {

    @Value("${external-api.baseUrl}")
    private String externalApiBaseUrl;

    private final WebClient webClient;

    public ProductInformationClient() {
        HttpClient client = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(1));

        webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(client)).build();

    }
    @Cacheable("productDetails")
    public Optional<Product> getProductInformation(String productId) {
        String url = externalApiBaseUrl + productId;
        try {
            log.info(String.format("Starting request to %s", url));

            Product response = webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .block();

            if(response == null) {
                return Optional.empty();
            }

            return Optional.of(response);
        } catch(WebClientResponseException.NotFound exception) {
            log.error(String.format("Product with product id %s not found", productId));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch(WebClientRequestException exception) {
            log.error(String.format("Call to %s failed due to time out", url));
            throw new RuntimeException();
        }

    }
}
