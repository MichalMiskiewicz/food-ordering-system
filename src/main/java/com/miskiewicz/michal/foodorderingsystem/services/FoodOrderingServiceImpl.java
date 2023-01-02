package com.miskiewicz.michal.foodorderingsystem.services;

import com.miskiewicz.michal.foodorderingsystem.entities.OrderEntity;
import com.miskiewicz.michal.foodorderingsystem.inout.IOWriter;
import com.miskiewicz.michal.foodorderingsystem.repositories.OrderRepository;
import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;
import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.ChooseInterpreter;
import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodOrderingServiceImpl implements FoodOrderingService {
    private final ChooseInterpreter chooseInterpreter;
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    private final IOWriter io;

    @Override
    public void placeOrder() {
        while (io.hasNext()) {
            OrderingRequest orderingRequest = new OrderingRequest();
            String chosen = io.read();
            if (chosen.equals("5")) {
                break;
            }
            List<Command> commands = chooseInterpreter.interpreter(chosen);
            try {
                commands.forEach(command -> command.execute(orderingRequest));
                saveOrder(orderingRequest);
            } catch (IllegalArgumentException e) {
                io.write(e.getMessage());
            }
            showAppMenu();
        }
    }

    private void saveOrder(OrderingRequest orderingRequest) {
        if (isEmpty(orderingRequest)) {
            log.info("Ordering request has not been saved");
            return;
        }
        OrderEntity completedOrder = mapper.map(orderingRequest, OrderEntity.class);
        orderRepository.save(completedOrder);
        showSummary(completedOrder);
    }

    private boolean isEmpty(OrderingRequest orderingRequest) {
        return isNull(orderingRequest.getDrink()) && isNull(orderingRequest.getLunch());
    }

    private void showSummary(OrderEntity completedOrder) {
        io.write("Your order is collected!");
        io.write(completedOrder.toString());
    }

    @Override
    public void showAppMenu() {
        io.write("What do you want to order? [(1) drink / (2) lunch / (3) both] | [(5) exit]:");
    }
}
