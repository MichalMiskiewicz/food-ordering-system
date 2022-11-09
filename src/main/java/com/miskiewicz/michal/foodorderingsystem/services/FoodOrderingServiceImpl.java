package com.miskiewicz.michal.foodorderingsystem.services;

import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;
import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.ChooseInterpreter;
import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command.Command;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class FoodOrderingServiceImpl implements FoodOrderingService {

    @Autowired
    private Scanner scanner;
    @Autowired
    private ChooseInterpreter chooseInterpreter;

    @Override
    public void placeOrder() {
        OrderingRequest orderingRequest = new OrderingRequest();
        while (scanner.hasNext()) {
            String chosen = scanner.nextLine();

            if (chosen.equals("5")) {
                break;
            }

            List<Command> commands = chooseInterpreter.interpreter(chosen);

            try {
                commands.forEach(command -> command.execute(orderingRequest));
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
                System.out.println(showAppMenu());
            }
        }
    }

    @Override
    public String showAppMenu() {
        return "What do you want to order? [(1) drink / (2) lunch / (3) both] | [(5) exit]:";
    }
}
