package com.example.similarproducts.usecase;

import com.example.similarproducts.client.ProductInformationClient;
import com.example.similarproducts.client.SimilarProductIdsClient;
import com.example.similarproducts.dto.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SimilarProductsUseCase {

    private final SimilarProductIdsClient similarProductIdsClient;
    private final ProductInformationClient productInformationClient;

    public SimilarProductsUseCase(SimilarProductIdsClient similarProductIdsClient, ProductInformationClient productInformationClient) {
        this.similarProductIdsClient = similarProductIdsClient;
        this.productInformationClient = productInformationClient;
    }


    public List<Product> execute(String productId) {

        return similarProductIdsClient.getSimilarProductIds(productId)
                .stream()
                .map(productInformationClient::getProductInformation)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}
