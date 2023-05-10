package com.example.similarproducts.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SimilarProductIdsClient {

    public static final int ONE_SECOND = 1000;


    @Cacheable("similarProducts")
    public List<Integer> getSimilarProductIds(String productId) {
        String url = "http://localhost:3001/product/" + productId + "/similarids";

        WebClient.Builder builder = createWebClientWithConnectAndReadTimeOuts();

        return builder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Integer.class)
                .timeout(Duration.ofSeconds(1))
                .collectList()
                .block();
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
