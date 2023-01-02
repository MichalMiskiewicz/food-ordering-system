package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.entities.DrinkEntity;
import com.miskiewicz.michal.foodorderingsystem.inout.IOWriter;
import com.miskiewicz.michal.foodorderingsystem.repositories.DrinkRepository;
import com.miskiewicz.michal.foodorderingsystem.requests.Drink;
import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DrinkCommand extends AbstractCommand {
    private final DrinkRepository drinkRepository;
    private final ModelMapper mapper;

    public DrinkCommand(IOWriter io, DrinkRepository drinkRepository, ModelMapper mapper) {
        super(io);
        this.drinkRepository = drinkRepository;
        this.mapper = mapper;
    }

    @Override
    public void execute(OrderingRequest orderingRequest) {
        List<AbstractCommand.IndexedDishes<DrinkEntity>> availableDrinks = getAvailableDrinks();
        write(availableDrinks);
        String chosenDrink = io.read();
        DrinkEntity drinkEntity = getChosen(availableDrinks, chosenDrink);
        Drink drink = mapper.map(drinkEntity, Drink.class);
        io.write("What about additions?:");
        List<AbstractCommand.IndexedDishes<Drink.Additions>> availableAdditions = getAvailableAdditions();
        write(availableAdditions);
        String chosenAdditions = io.read();
        Drink.Additions addition = getChosen(availableAdditions, chosenAdditions);
        drink.setAddition(addition);
        orderingRequest.setDrink(drink);
        orderingRequest.addToFinalCost(drink);
    }

    private List<AbstractCommand.IndexedDishes<Drink.Additions>> getAvailableAdditions() {
        Drink.Additions[] additions = Drink.Additions.values();
        return getIndexedDishes(Arrays.stream(additions).collect(Collectors.toList()));
    }

    private List<AbstractCommand.IndexedDishes<DrinkEntity>> getAvailableDrinks() {
        List<DrinkEntity> drinks = drinkRepository.findAll();
        return getIndexedDishes(drinks);
    }

}