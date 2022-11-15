package com.miskiewicz.michal.foodorderingsystem.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;


@Getter
@Setter
@ToString
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
}
