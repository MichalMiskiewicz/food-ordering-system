package com.miskiewicz.michal.foodorderingsystem.repositories;

import com.miskiewicz.michal.foodorderingsystem.entities.MainCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MainCourseRepository extends JpaRepository<MainCourseEntity, UUID> {

    @Query(value = "select * from main_courses m " +
            "join cuisines c on m.cuisine_id = c.id " +
            "where c.name = :cuisine", nativeQuery = true)
    List<MainCourseEntity> getMainCourseByProvidedCuisine(@Param("cuisine") String cuisine);
}
