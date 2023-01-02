package com.miskiewicz.michal.foodorderingsystem.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "mainCourses")
public class MainCourseEntity extends BasicOrderEntity {

    @ManyToOne
    private CuisineEntity cuisine;
}
