package com.miskiewicz.michal.foodorderingsystem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BasicOrderEntity extends BaseEntity {

    private String name;
    private BigDecimal price;

    @Override
    public String toString() {
        return this.getName() + " " + this.getPrice() + " zł";
    }

}