package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.FraudService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/fraud")
public class FraudController {

    private final FraudService service;

    public FraudController(FraudService service) {
        this.service = service;
    }

    @PostMapping("/predict")
    public FraudResponse predict(@Valid @RequestBody TransactionRequest request) {
        return service.getPrediction(request);
    }
}
