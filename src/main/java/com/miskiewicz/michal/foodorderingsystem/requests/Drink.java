package com.miskiewicz.michal.foodorderingsystem.requests;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Drink {
    UUID id;
    private String name;
    private BigDecimal price;
    private Additions addition;

    @Override
    public String toString() {
        return name + " with " + addition.toString();
    }

    public enum Additions {
        NONE {
            @Override
            public String toString() {
                return "nothing";
            }
        },
        ICE_CUBES {
            @Override
            public String toString() {
                return "ice cubes";
            }
        },
        LEMON {
            @Override
            public String toString() {
                return "lemon";
            }
        },
        ALL {
            @Override
            public String toString() {
                return "ice cubes and lemon";
            }
        }
    }
}
