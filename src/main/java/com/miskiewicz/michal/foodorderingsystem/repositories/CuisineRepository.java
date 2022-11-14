package com.miskiewicz.michal.foodorderingsystem.repositories;

import com.miskiewicz.michal.foodorderingsystem.entities.CuisineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CuisineRepository extends JpaRepository<CuisineEntity, UUID> {

}
