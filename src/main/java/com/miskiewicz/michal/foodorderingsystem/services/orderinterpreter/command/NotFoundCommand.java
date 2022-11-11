package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;

public class NotFoundCommand implements Command{
    @Override
    public void execute(OrderingRequest orderingRequest) {
        System.out.println("Command not found! Try again");
    }
}
