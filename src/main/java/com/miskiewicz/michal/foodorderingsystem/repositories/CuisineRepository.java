package com.miskiewicz.michal.foodorderingsystem.repositories;

import com.miskiewicz.michal.foodorderingsystem.entities.CuisineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CuisineRepository extends JpaRepository<CuisineEntity, UUID> {

}
