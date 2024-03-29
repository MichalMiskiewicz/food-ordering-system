package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter;

import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command.Command;
import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command.DrinkCommand;
import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command.LunchCommand;
import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command.NotFoundCommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChooseInterpreter {

    private final ConcurrentHashMap<String, List<Command>> orders;
    private final NotFoundCommand defaultCommand;

    public ChooseInterpreter(DrinkCommand drinkCommand, LunchCommand lunchCommand, NotFoundCommand notFoundCommand) {
        orders = new ConcurrentHashMap<>() {{
            put("1", List.of(drinkCommand));
            put("2", List.of(lunchCommand));
            put("3", List.of(drinkCommand, lunchCommand));
        }};
        this.defaultCommand = notFoundCommand;
    }

    public List<Command> interpreter(String chosen) {
        return orders.getOrDefault(chosen, List.of(defaultCommand));
    }
}
