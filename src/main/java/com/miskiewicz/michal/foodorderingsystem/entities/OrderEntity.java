package com.miskiewicz.michal.foodorderingsystem.entities;

import com.miskiewicz.michal.foodorderingsystem.requests.Drink;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
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

   /* @Enumerated(EnumType.STRING)
    private Additions additions;*/

    private Boolean iceCubes = false;

    private Boolean lemon = false;

    private BigDecimal cost;

    @ManyToOne
    private MainCourseEntity mainCourse;

    @ManyToOne
    private DessertEntity dessert;

    @ManyToOne
    private DrinkEntity drink;

    /*public enum Additions {
        NONE {
            @Override
            public String toString() {
                return "nothing";
            }
        },
        ICE_CUBES {
            @Override
            public String toString() {
                return "ice cubes";
            }
        },
        LEMON {
            @Override
            public String toString() {
                return "lemon";
            }
        },
        ALL {
            @Override
            public String toString() {
                return "ice cubes and lemon";
            }
        }
    }*/
}
