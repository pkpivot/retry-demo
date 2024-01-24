package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


@Controller
public class DefaultController {


    private final RetryService retryService;
    private int callCount ;
    

    public DefaultController(RetryService retryService) {
        this.retryService = retryService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @GetMapping("/broken")
    public ResponseEntity<String> broken() {
        System.out.println(String.format("Hitting broken endpoint %d time", callCount));
        if (callCount < 2) {
            callCount++;
            return new ResponseEntity<>("", HttpStatus.SERVICE_UNAVAILABLE);
        } else {
            callCount = 0;
            return new ResponseEntity<>("WORKING NOW", HttpStatus.OK);
        }
    }

    @GetMapping("/tryit")
    public ResponseEntity<String> tryit() {
        return retryService.retryableCall() ;
        
    }
}