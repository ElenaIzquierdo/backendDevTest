package com.example.similarproducts.usecase;

import com.example.similarproducts.client.ProductInformationClient;
import com.example.similarproducts.client.SimilarProductIdsClient;
import com.example.similarproducts.dto.Product;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

class SimilarProductsUseCaseTest {

    private final SimilarProductIdsClient similarProductIdsClient = mock(SimilarProductIdsClient.class);
    private final ProductInformationClient productInformationClient = mock(ProductInformationClient.class);

    private final SimilarProductsUseCase useCase = new SimilarProductsUseCase(similarProductIdsClient, productInformationClient);

    @Test
    void shouldReturnAListOfSimilarProductsByProductId() {
        Product product1 = new Product("1", "ball", 10.0, true);
        Product product2 = new Product("2", "sock", 15.0, true);

        when(similarProductIdsClient.getSimilarProductIds("someProductId")).thenReturn(List.of(1, 2));
        when(productInformationClient.getProductInformation("1")).thenReturn(Optional.of(product1));
        when(productInformationClient.getProductInformation("2")).thenReturn(Optional.of(product2));

        List<Product> similarProducts = useCase.execute("someProductId");

        assertThat(similarProducts, is(List.of(product1, product2)));
    }

    @Test
    void shouldReturnEmptyListIfThereAreNoSimilarProductIdsRelatedToThatProductId() {
        when(similarProductIdsClient.getSimilarProductIds("someProductId")).thenReturn(List.of());

        List<Product> similarProducts = useCase.execute("someProductId");

        assertThat(similarProducts, is(List.of()));
        verifyNoInteractions(productInformationClient);
    }

    @Test
    void shouldNotReturnProductsThatCouldNotGetProductDetails() {
        Product product1 = new Product("1", "ball", 10.0, true);

        when(similarProductIdsClient.getSimilarProductIds("someProductId")).thenReturn(List.of(1, 2));
        when(productInformationClient.getProductInformation("1")).thenReturn(Optional.of(product1));
        when(productInformationClient.getProductInformation("2")).thenReturn(Optional.empty());

        List<Product> similarProducts = useCase.execute("someProductId");

        assertThat(similarProducts, is(List.of(product1)));
    }
}