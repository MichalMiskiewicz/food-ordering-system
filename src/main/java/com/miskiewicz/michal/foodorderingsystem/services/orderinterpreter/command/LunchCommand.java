package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;
import org.springframework.stereotype.Component;

@Component
public class LunchCommand implements Command{
    @Override
    public void execute(OrderingRequest orderingRequest) {

    }
}
