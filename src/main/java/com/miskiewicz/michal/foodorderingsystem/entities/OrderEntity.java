package com.miskiewicz.michal.foodorderingsystem.entities;

import com.miskiewicz.michal.foodorderingsystem.requests.Drink;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
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

    @Enumerated(EnumType.STRING)
    private Drink.Additions additions;

    private BigDecimal cost;

    @ManyToOne
    private MainCourseEntity mainCourse;

    @ManyToOne
    private DessertEntity dessert;

    @ManyToOne
    private DrinkEntity drink;
}
