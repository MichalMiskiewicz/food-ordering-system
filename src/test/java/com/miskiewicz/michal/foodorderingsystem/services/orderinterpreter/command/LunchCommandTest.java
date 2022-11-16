package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.entities.CuisineEntity;
import com.miskiewicz.michal.foodorderingsystem.entities.DessertEntity;
import com.miskiewicz.michal.foodorderingsystem.entities.MainCourseEntity;
import com.miskiewicz.michal.foodorderingsystem.repositories.CuisineRepository;
import com.miskiewicz.michal.foodorderingsystem.repositories.DessertRepository;
import com.miskiewicz.michal.foodorderingsystem.repositories.MainCourseRepository;
import com.miskiewicz.michal.foodorderingsystem.requests.Dessert;
import com.miskiewicz.michal.foodorderingsystem.requests.Lunch;
import com.miskiewicz.michal.foodorderingsystem.requests.MainCourse;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LunchCommandTest {
    private final ModelMapper modelMapper = mock(ModelMapper.class);
    private final MainCourseRepository mainCourseRepository = mock(MainCourseRepository.class);
    private final DessertRepository dessertRepository = mock(DessertRepository.class);
    private final CuisineRepository cuisineRepository = mock(CuisineRepository.class);
    private final InputOutput io = mock(InputOutput.class);
    private final LunchCommand lunchCommand = new LunchCommand(io, mainCourseRepository, dessertRepository, cuisineRepository, modelMapper);
    private OrderingRequest orderingRequest;
    private DessertEntity dessertEntity;
    private MainCourseEntity mainCourseEntity;
    private CuisineEntity cuisineEntity;
    private Dessert dessert;
    private MainCourse mainCourse;

    @BeforeEach
    void setUp() {
        OrderingRequest orderingRequest = new OrderingRequest();
        DessertEntity dessertEntity = new DessertEntity();
        MainCourseEntity mainCourseEntity = new MainCourseEntity();
        CuisineEntity cuisineEntity = new CuisineEntity();
        Dessert dessert = new Dessert();
        MainCourse mainCourse = new MainCourse();
        dessertEntity.setPrice(BigDecimal.valueOf(4.5));
        dessert.setPrice(BigDecimal.valueOf(0.0));
        mainCourseEntity.setPrice(BigDecimal.valueOf(4.5));
        mainCourse.setPrice(BigDecimal.valueOf(0.0));
    }

    @Test
    @DisplayName("Should check if returned object is not null and is an instance of Lunch class")
    void shouldCheckIfReturnedObjectIsNotNullAndIsInstanceOfLunchClass() {
        given(io.read()).willReturn("0");
        given(dessertRepository.getDessertsByProvidedCuisine(any())).willReturn(List.of(dessertEntity));
        given(mainCourseRepository.getMainCourseByProvidedCuisine(any())).willReturn(List.of(mainCourseEntity));
        given(cuisineRepository.findAll()).willReturn(List.of(cuisineEntity));
        given(modelMapper.map(dessertEntity, Dessert.class)).willReturn(dessert);
        given(modelMapper.map(mainCourseEntity, MainCourse.class)).willReturn(mainCourse);

        lunchCommand.execute(orderingRequest);

        assertThat(orderingRequest.getLunch()).isNotNull();
        assertThat(orderingRequest.getLunch()).isInstanceOf(Lunch.class);
    }

    @Test
    @DisplayName("Should throw exception when user choose cuisine that not exists")
    void shouldThrowExceptionWhenUserChooseCuisineThatNotExists() {
        given(cuisineRepository.findAll()).willReturn(List.of(cuisineEntity));
        given(io.read()).willReturn("1");

        IllegalArgumentException thrown = Assertions
                .assertThrows(IllegalArgumentException.class, () -> {
                    lunchCommand.execute(orderingRequest);
                });
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).isEqualTo("There is no cuisine of that index!");
    }

    @Test
    @DisplayName("Should throw exception when user choose main course that not exists")
    void shouldThrowExceptionWhenUserChooseMainCourseThatNotExists() {
        List<LunchCommand.MainCoursePair> availableMainCourses =
                List.of(LunchCommand.MainCoursePair.of("0", mainCourseEntity),
                        LunchCommand.MainCoursePair.of("1", mainCourseEntity));


        InvocationTargetException thrown = Assertions
                .assertThrows(InvocationTargetException.class, () -> {
                    getGetMainCourseMethod().invoke(lunchCommand, availableMainCourses, "2");
                });

        assertThat(thrown.getCause()).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getCause().getMessage()).isEqualTo("There is no main course of that index!");
    }

    @Test
    @DisplayName("Should throw exception when user choose dessert that not exists")
    void shouldThrowExceptionWhenUserChooseDessertThatNotExists() {
        List<LunchCommand.DessertPair> availableDesserts =
                List.of(LunchCommand.DessertPair.of("0", dessertEntity),
                        LunchCommand.DessertPair.of("1", dessertEntity));

        InvocationTargetException thrown = Assertions
                .assertThrows(InvocationTargetException.class, () -> {
                    getGetDessertMethod().invoke(lunchCommand, availableDesserts, "2");
                });

        assertThat(thrown.getCause()).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getCause().getMessage()).isEqualTo("There is no dessert of that index!");
    }

    private Method getGetMainCourseMethod() throws NoSuchMethodException {
        Method method = LunchCommand.class.getDeclaredMethod("getMainCourse", List.class, String.class);
        method.setAccessible(true);
        return method;
    }

    private Method getGetDessertMethod() throws NoSuchMethodException {
        Method method = LunchCommand.class.getDeclaredMethod("getDessert", List.class, String.class);
        method.setAccessible(true);
        return method;
    }
}