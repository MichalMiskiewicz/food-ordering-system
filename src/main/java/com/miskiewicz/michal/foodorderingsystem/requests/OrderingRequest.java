package com.miskiewicz.michal.foodorderingsystem.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderingRequest {
    private Lunch lunch;
    private Drink drink;
    private BigDecimal cost = new BigDecimal("0.00");
}
