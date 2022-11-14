package com.miskiewicz.michal.foodorderingsystem.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "desserts")
public class DessertEntity extends VictualsEntity {

    @ManyToOne
    private CuisineEntity cuisine;

    @Override
    public String toString() {
        return this.getName() + " " + this.getPrice() + " zł";
    }

}
