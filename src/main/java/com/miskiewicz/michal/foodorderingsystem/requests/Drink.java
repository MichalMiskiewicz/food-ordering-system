package com.miskiewicz.michal.foodorderingsystem.requests;

import lombok.Data;

@Data
public class Drink {
    private String name;
    private Double price;

    public enum Addition {
        ICE_CUBES,
        LEMON
    }
}
