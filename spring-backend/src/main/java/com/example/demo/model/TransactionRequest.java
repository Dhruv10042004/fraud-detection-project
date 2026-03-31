package com.example.demo.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class TransactionRequest {
    @NotNull
    @PositiveOrZero
    private Double amount;

    @NotNull
    @Min(0)
    @Max(23)
    private Integer hour;

    @NotNull
    @Min(0)
    @Max(6)
    private Integer day_of_week;

    @NotNull
    private Double amount_diff;

    @NotNull
    @PositiveOrZero
    private Double time_diff;

    @NotNull
    @PositiveOrZero
    private Double amount_velocity;

    @NotNull
    @PositiveOrZero
    private Double rolling_avg;

    @NotNull
    private Double deviation;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(Integer day_of_week) {
        this.day_of_week = day_of_week;
    }

    public Double getAmount_diff() {
        return amount_diff;
    }

    public void setAmount_diff(Double amount_diff) {
        this.amount_diff = amount_diff;
    }

    public Double getTime_diff() {
        return time_diff;
    }

    public void setTime_diff(Double time_diff) {
        this.time_diff = time_diff;
    }

    public Double getAmount_velocity() {
        return amount_velocity;
    }

    public void setAmount_velocity(Double amount_velocity) {
        this.amount_velocity = amount_velocity;
    }

    public Double getRolling_avg() {
        return rolling_avg;
    }

    public void setRolling_avg(Double rolling_avg) {
        this.rolling_avg = rolling_avg;
    }

    public Double getDeviation() {
        return deviation;
    }

    public void setDeviation(Double deviation) {
        this.deviation = deviation;
    }
}
