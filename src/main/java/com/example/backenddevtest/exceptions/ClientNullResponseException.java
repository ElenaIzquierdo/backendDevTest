package com.example.backenddevtest.exceptions;

public class ClientNullResponseException extends RuntimeException{

    public ClientNullResponseException(String message) {
        super(message);
    }
}
