package com.miskiewicz.michal.foodorderingsystem.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderingRequest implements CostManagement{
    private Lunch lunch;
    private Drink drink;
    private BigDecimal cost = new BigDecimal("0.00");

    @Override
    public void addToFinalCost(BigDecimal amount) {
        this.cost = this.cost.add(amount);
    }
}
