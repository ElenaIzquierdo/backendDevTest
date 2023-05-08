package com.example.backenddevtest.repository;

import lombok.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
public class StarWarsCharacterRepository {
    private final RestTemplate restTemplate;

    public StarWarsCharacterRepository(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri("http://localhost:3001/product/1/similarids").build();
    }

    public Optional<Character> findById(Long id) {

        ResponseEntity<Character> response;
        try {
            response = restTemplate.getForEntity("/people/{id}", Character.class, id);
        } catch (HttpStatusCodeException e) {
            return Optional.empty();
        }

        return Optional.ofNullable(response.getBody());
    }
}
