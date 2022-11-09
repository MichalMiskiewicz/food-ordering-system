package com.miskiewicz.michal.foodorderingsystem.repositories;

import com.miskiewicz.michal.foodorderingsystem.entities.DrinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DrinkRepository extends JpaRepository<DrinkEntity, UUID> {
}
