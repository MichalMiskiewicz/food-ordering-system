package com.miskiewicz.michal.foodorderingsystem.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Dessert {
    private String name;
    private BigDecimal price;
}
