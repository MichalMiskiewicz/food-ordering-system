package com.miskiewicz.michal.foodorderingsystem;

import com.miskiewicz.michal.foodorderingsystem.services.FoodOrderingService;
import com.miskiewicz.michal.foodorderingsystem.services.FoodOrderingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FoodOrderingSystemApplication implements CommandLineRunner {

    @Autowired
    private FoodOrderingService foodOrderingService;
    public static void main(String[] args) {
        SpringApplication.run(FoodOrderingSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(foodOrderingService.showAppMenu());
        foodOrderingService.placeOrder();
        System.exit(0);
    }
}
