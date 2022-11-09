package com.miskiewicz.michal.foodorderingsystem.requests;

import lombok.Data;

@Data
public class Lunch {
    private MainCourse mainCourse;
    private Dessert dessert;
}
