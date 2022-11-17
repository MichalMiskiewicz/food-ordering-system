package com.miskiewicz.michal.foodorderingsystem.requests;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;

@Data
public class OrderingRequest {
    private Lunch lunch;
    private Drink drink;
    private BigDecimal cost = new BigDecimal("0.00");

    public void addToFinalCost(HavingPriceProduct... products) {
        Arrays.stream(products).map(HavingPriceProduct::getPrice).forEach(it -> cost = cost.add(it));
    }

}
