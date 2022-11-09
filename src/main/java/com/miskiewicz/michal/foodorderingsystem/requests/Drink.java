package com.miskiewicz.michal.foodorderingsystem.requests;

import lombok.Data;

import java.util.List;

@Data
public class Drink {
    private String name;
    private Double price;
    private Additions addition;
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
        LEMON{
            @Override
            public String toString() {
                return "lemon";
            }
        },
        ALL{
            @Override
            public String toString() {
                return "ice cubes and lemon";
            }
        }
    }

    @Override
    public String toString() {
        return name + " with " + addition.toString();
    }
}
