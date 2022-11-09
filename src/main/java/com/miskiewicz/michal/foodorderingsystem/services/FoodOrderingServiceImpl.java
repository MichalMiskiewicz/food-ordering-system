package com.miskiewicz.michal.foodorderingsystem.services;

import com.miskiewicz.michal.foodorderingsystem.entities.OrderEntity;
import com.miskiewicz.michal.foodorderingsystem.repositories.OrderRepository;
import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;
import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.ChooseInterpreter;
import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodOrderingServiceImpl implements FoodOrderingService {

    private final Scanner scanner;
    private final ChooseInterpreter chooseInterpreter;
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;

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
                OrderEntity map = mapper.map(orderingRequest, OrderEntity.class);
                orderRepository.save(map);

            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            } finally {
                System.out.println(showAppMenu());
            }
        }
    }

    @Override
    public String showAppMenu() {
        return "What do you want to order? [(1) drink / (2) lunch / (3) both] | [(5) exit]:";
    }
}
