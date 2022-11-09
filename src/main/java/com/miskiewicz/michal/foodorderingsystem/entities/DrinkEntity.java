package com.miskiewicz.michal.foodorderingsystem.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "drinks")
public class DrinkEntity extends VictualsEntity {
    @Override
    public String toString() {
        return this.getName() + " " + this.getPrice() + " zł";
    }
}
