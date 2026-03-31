package com.example.demo.model;

import java.util.List;

public class FraudResponse {
    private double fraud_score;
    private boolean is_fraud;
    private List<String> explanation;

    public double getFraud_score() {
        return fraud_score;
    }

    public void setFraud_score(double fraud_score) {
        this.fraud_score = fraud_score;
    }

    public boolean isIs_fraud() {
        return is_fraud;
    }

    public void setIs_fraud(boolean is_fraud) {
        this.is_fraud = is_fraud;
    }

    public List<String> getExplanation() {
        return explanation;
    }

    public void setExplanation(List<String> explanation) {
        this.explanation = explanation;
    }
}
