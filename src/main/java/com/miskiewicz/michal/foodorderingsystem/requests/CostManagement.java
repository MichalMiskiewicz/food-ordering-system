package com.miskiewicz.michal.foodorderingsystem.requests;

import java.math.BigDecimal;

public interface CostManagement {
    void addToFinalCost(HavingPriceProduct... amount);
}
