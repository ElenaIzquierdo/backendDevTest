package com.example.similarproducts.client;

import com.example.similarproducts.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Optional;


@Service
@Slf4j
public class ProductInformationClient {

    @Cacheable("productDetails")
    public Optional<Product> getProductInformation(String productId) {
        String url = "http://host.docker.internal:3001/product/" + productId;
        try {
            log.info(String.format("Starting request to %s", url));
            HttpClient client = HttpClient.create()
                    .responseTimeout(Duration.ofSeconds(1));

            WebClient.Builder builder = WebClient.builder()
                    .clientConnector(new ReactorClientHttpConnector(client));

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
        } catch(WebClientRequestException exception) {
            String message = String.format("Call to %s failed due to time out", url);
            log.error(message);
            throw new RuntimeException(message);
        }

    }
}
