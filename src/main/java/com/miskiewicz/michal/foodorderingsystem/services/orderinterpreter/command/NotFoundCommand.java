package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.inout.InputOutput;
import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotFoundCommand implements Command {

    private final InputOutput io;
    @Override
    public void execute(OrderingRequest orderingRequest) {
        io.write("Command not found! Try again");
    }
}
