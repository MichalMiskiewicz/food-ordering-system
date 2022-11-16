package com.miskiewicz.michal.foodorderingsystem;

import com.miskiewicz.michal.foodorderingsystem.services.FoodOrderingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RequiredArgsConstructor
public class FoodOrderingSystemApplication implements CommandLineRunner {

    private final FoodOrderingService foodOrderingService;

    public static void main(String[] args) {
        SpringApplication.run(FoodOrderingSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        foodOrderingService.showAppMenu();
        foodOrderingService.placeOrder();
        System.exit(0);
    }
}
