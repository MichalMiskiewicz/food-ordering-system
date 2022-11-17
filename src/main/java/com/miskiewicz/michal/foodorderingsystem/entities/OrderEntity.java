package com.miskiewicz.michal.foodorderingsystem.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;


@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    private Boolean iceCubes = false;

    private Boolean lemon = false;

    private BigDecimal cost;

    @ManyToOne
    private MainCourseEntity mainCourse;

    @ManyToOne
    private DessertEntity dessert;

    @ManyToOne
    private DrinkEntity drink;

    @Override
    public String toString() {
        return """
                Main Course: %s
                Dessert: %s
                Drink: %s
                    Ice Cubes: %s
                    Lemon: %s
                COST: %s
                """.formatted(
                        (mainCourse == null ? "no" : mainCourse.toString()),
                        (dessert == null ? "no" : dessert.toString()),
                        (drink == null ? "no" : drink.toString()),
                        iceCubes,
                        lemon,
                        cost
                );
    }
}
