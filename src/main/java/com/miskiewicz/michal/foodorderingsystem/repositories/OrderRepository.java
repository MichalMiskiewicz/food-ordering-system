package com.miskiewicz.michal.foodorderingsystem.repositories;

import com.miskiewicz.michal.foodorderingsystem.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}
