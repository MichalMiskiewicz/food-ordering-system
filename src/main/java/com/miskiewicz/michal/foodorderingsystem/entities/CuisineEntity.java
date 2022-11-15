package com.miskiewicz.michal.foodorderingsystem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cuisines")
public class CuisineEntity extends BaseEntity {

    private String name;

    @Override
    public String toString() {
        return this.getName();
    }
}
