package com.miskiewicz.michal.foodorderingsystem.requests;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Dessert {
    private UUID id;
    private String name;
    private BigDecimal price;
}
