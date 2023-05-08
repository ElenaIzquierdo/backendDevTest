package com.example.similarproducts.controller;

import com.example.similarproducts.dto.Product;
import com.example.similarproducts.usecase.SimilarProductsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SimilarProductsControllerTest {

    @MockBean
    private SimilarProductsUseCase useCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn200AndReturnSimilarProductsWhenThereAreProductsReturnedByUseCase() throws Exception {
        Product someProduct = Product.builder()
                .id("someId")
                .name("someName")
                .price(1L)
                .isAvailable(true)
                .build();
        when(useCase.execute("someProductId")).thenReturn(List.of(someProduct));
        mockMvc.perform(get("/product/someProductId/similar")).andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"someId\",\"name\":\"someName\",\"price\":1,\"available\":true}]"));
    }

    @Test
    void shouldReturn200AndReturnEmptyListWhenNoProductsReturnedByUseCase() throws Exception {
        when(useCase.execute("someProductId")).thenReturn(List.of());
        mockMvc.perform(get("/product/someProductId/similar")).andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}