package com.miskiewicz.michal.foodorderingsystem.repositories;

import com.miskiewicz.michal.foodorderingsystem.entities.DessertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DessertRepository extends JpaRepository<DessertEntity, UUID> {

    @Query(value = "select * from desserts d " +
            "join cuisines c on d.cuisine_id = c.id " +
            "where c.name = :cuisine", nativeQuery = true)
    List<DessertEntity> getDessertsByProvidedCuisine(@Param("cuisine") String cuisine);
}
