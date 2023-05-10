package com.example.similarproducts.client;

import com.example.similarproducts.dto.Product;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class ProductInformationClient {

    public final int ONE_SECOND = 1000;

    @Cacheable("productDetails")
    public Optional<Product> getProductInformation(String productId) {
        String url = "http://localhost:3001/product/" + productId;

        WebClient.Builder builder = createWebClientWithConnectAndReadTimeOuts();

        Product response = builder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Product.class)
                .timeout(Duration.ofSeconds(1))
                .block();

        if(response == null) {
            return Optional.empty();
        }

        return Optional.of(response);
    }

    private WebClient.Builder createWebClientWithConnectAndReadTimeOuts() {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(tcpClient -> {
                    tcpClient = tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, ONE_SECOND);
                    tcpClient = tcpClient.doOnConnected(conn -> conn
                            .addHandlerLast(new ReadTimeoutHandler(ONE_SECOND, TimeUnit.SECONDS)));
                    return tcpClient;
                });

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        return WebClient.builder().clientConnector(connector);
    }
}
