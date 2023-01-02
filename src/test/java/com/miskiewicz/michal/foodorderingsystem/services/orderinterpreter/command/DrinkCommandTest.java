package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.entities.DrinkEntity;
import com.miskiewicz.michal.foodorderingsystem.inout.IOWriter;
import com.miskiewicz.michal.foodorderingsystem.repositories.DrinkRepository;
import com.miskiewicz.michal.foodorderingsystem.requests.Drink;
import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;
import io.vavr.Tuple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


class DrinkCommandTest {

    private final ModelMapper modelMapper = mock(ModelMapper.class);
    private final DrinkRepository drinkRepository = mock(DrinkRepository.class);
    private final IOWriter io = mock(IOWriter.class);
    private final DrinkCommand drinkCommand = new DrinkCommand(io, drinkRepository, modelMapper);
    private OrderingRequest orderingRequest;
    private DrinkEntity drinkEntity;
    private Drink drink;

    @BeforeEach
    void setUp() {
        orderingRequest = new OrderingRequest();
        drinkEntity = new DrinkEntity();
        drink = new Drink();
        drinkEntity.setPrice(BigDecimal.valueOf(0.0));
        drink.setPrice(BigDecimal.valueOf(4.0));
    }

    @Test
    @DisplayName("Should check if returned object is not null and is an instance of Drink class")
    void shouldCheckIfReturnedObjectIsNotNullAndIsInstanceOfDrinkClass() {
        given(io.read()).willReturn("0");
        given(drinkRepository.findAll()).willReturn(List.of(drinkEntity));
        given(modelMapper.map(drinkEntity, Drink.class)).willReturn(drink);

        drinkCommand.execute(orderingRequest);

        assertThat(orderingRequest.getDrink()).usingRecursiveComparison().isEqualTo(drink);
    }

    @Test
    @DisplayName("Should throw exception when user choose drink that not exists")
    void shouldThrowExceptionWhenUserChooseDrinkThatNotExists() {
        List<AbstractCommand.IndexedDishes<DrinkEntity>> availableDrinks =
                List.of(AbstractCommand.IndexedDishes.of(Tuple.of("0", drinkEntity)));

        IllegalArgumentException thrown = Assertions
                .assertThrows(IllegalArgumentException.class, () -> {
                    ReflectionTestUtils.invokeMethod(drinkCommand, "getChosen", availableDrinks, "1");
                });
        assertThat(thrown.getMessage()).isEqualTo("There is no element of that index!");
    }

    @Test
    @DisplayName("Should throw exception when user choose addition that not exists")
    void shouldThrowExceptionWhenUserChooseAdditionThatNotExists() {
        List<AbstractCommand.IndexedDishes<Drink.Additions>> availableAdditions =
                List.of(AbstractCommand.IndexedDishes.of(Tuple.of("0", Drink.Additions.NONE)),
                        AbstractCommand.IndexedDishes.of(Tuple.of("1", Drink.Additions.ICE_CUBES)),
                        AbstractCommand.IndexedDishes.of(Tuple.of("2", Drink.Additions.LEMON)),
                        AbstractCommand.IndexedDishes.of(Tuple.of("3", Drink.Additions.ALL)));

        IllegalArgumentException thrown = Assertions
                .assertThrows(IllegalArgumentException.class, () -> {
                    ReflectionTestUtils.invokeMethod(drinkCommand, "getChosen", availableAdditions, "4");
                });
        assertThat(thrown.getMessage()).isEqualTo("There is no element of that index!");
    }
}