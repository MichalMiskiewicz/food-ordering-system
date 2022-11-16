package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;

public interface Command {

    void execute(OrderingRequest orderingRequest);

}
