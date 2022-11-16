package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.entities.DrinkEntity;
import com.miskiewicz.michal.foodorderingsystem.repositories.DrinkRepository;
import com.miskiewicz.michal.foodorderingsystem.requests.Drink;
import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;
import com.miskiewicz.michal.foodorderingsystem.inout.InputOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


class DrinkCommandTest {

    private final ModelMapper modelMapper = mock(ModelMapper.class);
    private final DrinkRepository drinkRepository = mock(DrinkRepository.class);
    private final InputOutput io = mock(InputOutput.class);
    private final DrinkCommand drinkCommand = new DrinkCommand(drinkRepository, modelMapper, io);
    private OrderingRequest orderingRequest;
    private DrinkEntity drinkEntity;
    private Drink drink;

    @BeforeEach
    void setUp() {
        orderingRequest = new OrderingRequest();
        drinkEntity = new DrinkEntity();
        drink = new Drink();
        drinkEntity.setPrice(BigDecimal.valueOf(4.5));
        drink.setPrice(BigDecimal.valueOf(0.0));
    }

    @Test
    @DisplayName("Should check if returned object is not null and is an instance of Drink class")
    void shouldCheckIfReturnedObjectIsNotNullAndIsInstanceOfDrinkClass() {
        given(io.read()).willReturn("0");
        given(drinkRepository.findAll()).willReturn(List.of(drinkEntity));
        given(modelMapper.map(drinkEntity, Drink.class)).willReturn(drink);

        drinkCommand.execute(orderingRequest);

        assertThat(orderingRequest.getDrink()).isNotNull();
    }

    @Test
    @DisplayName("Should throw exception when user choose drink that not exists")
    void shouldThrowExceptionWhenUserChooseDrinkThatNotExists() {
        List<DrinkCommand.DrinkPair> availableDrinks =
                List.of(DrinkCommand.DrinkPair.of("0", drinkEntity),
                        DrinkCommand.DrinkPair.of("1", drinkEntity));

        InvocationTargetException thrown = Assertions
                .assertThrows(InvocationTargetException.class, () -> {
                    getGetDrinkMethod().invoke(drinkCommand, availableDrinks, "2");
                });

        assertThat(thrown.getCause()).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getCause().getMessage()).isEqualTo("There is no drink of that index!");
    }

    @Test
    @DisplayName("Should throw exception when user choose addition that not exists")
    void shouldThrowExceptionWhenUserChooseAdditionThatNotExists() {
        List<DrinkCommand.AdditionsPair> availableAdditions =
                List.of(DrinkCommand.AdditionsPair.of("0", Drink.Additions.NONE),
                        DrinkCommand.AdditionsPair.of("1", Drink.Additions.LEMON),
                        DrinkCommand.AdditionsPair.of("2", Drink.Additions.ICE_CUBES),
                        DrinkCommand.AdditionsPair.of("3", Drink.Additions.ALL));

        InvocationTargetException thrown = Assertions
                .assertThrows(InvocationTargetException.class, () -> {
                    getGetDrinkAdditionsMethod().invoke(drinkCommand, availableAdditions, "4");
                });

        assertThat(thrown.getCause()).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getCause().getMessage()).isEqualTo("There is no addition of that index!");
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