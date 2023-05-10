package com.example.similarproducts.client;

import com.example.similarproducts.dto.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Optional;


@Service
public class ProductInformationClient {

    @Cacheable("productDetails")
    public Optional<Product> getProductInformation(String productId) {
        String url = "http://localhost:3001/product/" + productId;

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
    }
}
