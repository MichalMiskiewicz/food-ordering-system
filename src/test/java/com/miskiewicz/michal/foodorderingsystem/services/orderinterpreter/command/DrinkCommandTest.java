package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.entities.DrinkEntity;
import com.miskiewicz.michal.foodorderingsystem.repositories.DrinkRepository;
import com.miskiewicz.michal.foodorderingsystem.requests.Drink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;


class DrinkCommandTest {

    private final ModelMapper modelMapper = mock(ModelMapper.class);
    private final DrinkRepository drinkRepository = mock(DrinkRepository.class);
    private final Scanner scanner = new Scanner(System.in);
    private final DrinkCommand drinkCommand = new DrinkCommand(scanner, drinkRepository, modelMapper);

    @Test
    @DisplayName("Should not choose any drink and throw exception")
    void shouldNotChooseAnyDrinkAndThrowException() throws NoSuchMethodException, IllegalAccessException {
        List<DrinkCommand.DrinkPair> availableDrinks =
                List.of(DrinkCommand.DrinkPair.of("0", new DrinkEntity()),
                        DrinkCommand.DrinkPair.of("1", new DrinkEntity()));
        Exception exception = null;

        try {
            getGetDrinkMethod().invoke(drinkCommand, availableDrinks, "2");
        } catch (InvocationTargetException e) {
            exception = e;
        }

        assert exception != null;
        assertThat(exception.getCause()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Should not choose any drink additions and throw exception")
    void shouldNotChooseAnyDrinkAdditionsAndThrowException() throws NoSuchMethodException, IllegalAccessException {
        List<DrinkCommand.AdditionsPair> availableAdditions =
                List.of(DrinkCommand.AdditionsPair.of("0", Drink.Additions.NONE),
                        DrinkCommand.AdditionsPair.of("1", Drink.Additions.LEMON),
                        DrinkCommand.AdditionsPair.of("2", Drink.Additions.ICE_CUBES),
                        DrinkCommand.AdditionsPair.of("3", Drink.Additions.ALL));
        Exception exception = null;

        try {
            getGetDrinkAdditionsMethod().invoke(drinkCommand, availableAdditions, "4");
        } catch (InvocationTargetException e) {
            exception = e;
        }

        assert exception != null;
        assertThat(exception.getCause()).isInstanceOf(IllegalArgumentException.class);
    }

    private Method getGetDrinkAdditionsMethod() throws NoSuchMethodException {
        Method method = DrinkCommand.class.getDeclaredMethod("getDrinkAdditions", List.class, String.class);
        method.setAccessible(true);
        return method;
    }

    private Method getGetDrinkMethod() throws NoSuchMethodException {
        Method method = DrinkCommand.class.getDeclaredMethod("getDrink", List.class, String.class);
        method.setAccessible(true);
        return method;
    }
}