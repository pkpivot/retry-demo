package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RetryService {

    private final RestTemplate restTemplate;

    public RetryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Retryable(retryFor = RuntimeException.class)
    ResponseEntity<String> retryableCall() {
        return restTemplate.getForEntity("http://localhost:8080/broken", String.class);
    }
}
