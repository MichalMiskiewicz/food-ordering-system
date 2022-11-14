package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.entities.DrinkEntity;
import com.miskiewicz.michal.foodorderingsystem.repositories.DrinkRepository;
import com.miskiewicz.michal.foodorderingsystem.requests.Drink;
import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DrinkCommand implements Command {

    private final Scanner scanner;
    private final DrinkRepository drinkRepository;
    private final ModelMapper mapper;

    @Override
    public void execute(OrderingRequest orderingRequest) {
        try {
            List<DrinkPair> availableDrinks = getAvailableDrinks();
            availableDrinks.forEach(System.out::println);
            String chosenDrink = scanner.nextLine();
            Drink drink = getDrink(availableDrinks, chosenDrink);
            System.out.println("What about additions?:");
            List<AdditionsPair> availableAdditions = getAvailableAdditions();
            availableAdditions.forEach(System.out::println);
            String chosenAdditions = scanner.nextLine();
            Drink.Additions addition = getDrinkAdditions(availableAdditions, chosenAdditions);
            drink.setAddition(addition);
            orderingRequest.setDrink(drink);
            orderingRequest.setCost(orderingRequest.getCost().add(drink.getPrice()));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private Drink getDrink(List<DrinkPair> availableDrinks, String chosenDrink) {
        Optional<DrinkPair> optionalDrink = Optional.ofNullable(availableDrinks.stream()
                .filter(drink -> drink.getIndex().equals(chosenDrink))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no drink of that index!")));
        return mapper.map(optionalDrink.get().getDrinkEntity(), Drink.class);
    }

    private Drink.Additions getDrinkAdditions(List<AdditionsPair> availableAdditions, String chosenAdditions) {
        Optional<AdditionsPair> optionalAddition = Optional.ofNullable(availableAdditions.stream()
                .filter(additions -> additions.getIndex().equals(chosenAdditions))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no addition of that index!")));
        return optionalAddition.get().getAddition();
    }

    private List<DrinkPair> getAvailableDrinks() {
        List<DrinkEntity> drinks = drinkRepository.findAll();
        return IntStream.range(0, drinks.size())
                .mapToObj(index -> DrinkPair.of(String.valueOf(index), drinks.get(index)))
                .toList();
    }

    private List<AdditionsPair> getAvailableAdditions() {
        Drink.Additions[] additions = Drink.Additions.values();
        return IntStream.range(0, additions.length)
                .mapToObj(index -> AdditionsPair.of(String.valueOf(index), additions[index]))
                .toList();
    }

    @Value
    public static class DrinkPair {
        String index;
        DrinkEntity drinkEntity;

        static DrinkPair of(String index, DrinkEntity name) {
            return new DrinkPair(index, name);
        }

        @Override
        public String toString() {
            return "(" + index + ") " + drinkEntity.getName() + " " + drinkEntity.getPrice() + " zł";
        }
    }

    @Value
    public static class AdditionsPair {
        String index;
        Drink.Additions addition;

        static AdditionsPair of(String index, Drink.Additions addition) {
            return new AdditionsPair(index, addition);
        }

        @Override
        public String toString() {
            return "(" + index + ") " + addition.toString();
        }
    }
}
