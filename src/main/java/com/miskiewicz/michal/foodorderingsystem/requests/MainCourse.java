package com.miskiewicz.michal.foodorderingsystem.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MainCourse {
    private String name;
    private BigDecimal price;
}
