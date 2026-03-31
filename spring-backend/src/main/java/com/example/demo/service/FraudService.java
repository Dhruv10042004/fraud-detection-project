package com.example.demo.service;

import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class FraudService {

    private final RestTemplate restTemplate;
    private final String mlUrl;

    public FraudService(@Value("${app.ml.predict-url}") String mlUrl) {
        this.restTemplate = new RestTemplate();
        this.mlUrl = mlUrl;
    }

    public FraudResponse getPrediction(TransactionRequest request) {
        FraudResponse response = restTemplate.postForObject(mlUrl, request, FraudResponse.class);
        if (response == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "ML service returned an empty response"
            );
        }
        return response;
    }
}
