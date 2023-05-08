package com.example.backenddevtest.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;
import wiremock.org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest({SimilarProductsClient.class})
@RunWith(SpringRunner.class)
class SimilarProductsClientTest {

    @Autowired
    private SimilarProductsClient similarProductsClient;

    private MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    public void setUp() {
        RestTemplate template = new RestTemplate();
        mockRestServiceServer= MockRestServiceServer.createServer(template);
    }
    @AfterEach
    void resetMockServer(){
        mockRestServiceServer.reset();
    }

    @Test
    void shouldThrowRunTimeExceptionWhenSimilarProductsClientResponseIs5XX() {
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo("http://localhost:3001/product/1/similarids"))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(HttpServerErrorException.class, () -> similarProductsClient.getSimilarProductsForProductId("1"));
    }

    @Test
    public void shouldThrowClientNullResponseExceptionWhenResponseIsNull() {
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo("http://localhost:3001/product/1/similarids"))
                .andRespond(withStatus(HttpStatus.OK));

        assertThrows(HttpServerErrorException.class, () -> similarProductsClient.getSimilarProductsForProductId("1"));
    }

    @Test
    void shouldReturnSimilarProductIdsForSpecificProductId() throws IOException {

        mockRestServiceServer
                .expect(requestTo("http://localhost:3001/product/1/similarids"))
                .andRespond(withSuccess(getFileContent("similaridsResponse.json"), MediaType.APPLICATION_JSON));

        List<Integer> similarProductIds = similarProductsClient.getSimilarProductsForProductId("1");

        assertEquals(similarProductIds, List.of(2,3,4));
    }


    private String getFileContent(String fileName) throws IOException {
        final FileInputStream inputStream = new FileInputStream(String.format("src/test/resources/similar-ids/%s", fileName));
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

}