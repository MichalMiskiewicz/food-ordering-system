package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter;

import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command.Command;
import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command.DrinkCommand;
import com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command.LunchCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class ChooseInterpreterTest {
    static DrinkCommand drinkCommand = mock(DrinkCommand.class);
    static LunchCommand lunchCommand = mock(LunchCommand.class);
    ChooseInterpreter chooseInterpreter = new ChooseInterpreter(drinkCommand, lunchCommand);

    public static Stream<Arguments> getCommandsList() {
        return Stream.of(
                Arguments.of("1", List.of(drinkCommand)),
                Arguments.of("2", List.of(lunchCommand)),
                Arguments.of("3", List.of(drinkCommand, lunchCommand))
        );
    }

    @ParameterizedTest
    @DisplayName("Should check if returned list size is correct")
    @MethodSource("getCommandsList")
    void shouldCheckIfReturnedListSizeIsCorrect(String input, List<Command> expected) {
        List<Command> result = chooseInterpreter.interpreter(input);

        assertThat(result.size()).isEqualTo(expected.size());
    }
}