package com.miskiewicz.michal.foodorderingsystem.requests;

import lombok.Data;

@Data
public class OrderingRequest {
    private Lunch lunch;
    private Drink drink;
}