package com.miskiewicz.michal.foodorderingsystem.entities;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    @ColumnDefault("random_uuid()")
    private UUID id;

    private Boolean iceCubes = false;

    private Boolean lemon = false;

    private Double cost = 0.0;

    @ManyToOne
    private MainCourseEntity mainCourse;

    @ManyToOne
    private DessertEntity dessert;

    @ManyToOne
    private DrinkEntity drink;
}
