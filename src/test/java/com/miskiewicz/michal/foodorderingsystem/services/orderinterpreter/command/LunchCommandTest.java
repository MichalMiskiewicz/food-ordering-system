package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.entities.CuisineEntity;
import com.miskiewicz.michal.foodorderingsystem.entities.DessertEntity;
import com.miskiewicz.michal.foodorderingsystem.entities.MainCourseEntity;
import com.miskiewicz.michal.foodorderingsystem.inout.IOWriter;
import com.miskiewicz.michal.foodorderingsystem.repositories.CuisineRepository;
import com.miskiewicz.michal.foodorderingsystem.repositories.DessertRepository;
import com.miskiewicz.michal.foodorderingsystem.repositories.MainCourseRepository;
import com.miskiewicz.michal.foodorderingsystem.requests.Dessert;
import com.miskiewicz.michal.foodorderingsystem.requests.Lunch;
import com.miskiewicz.michal.foodorderingsystem.requests.MainCourse;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LunchCommandTest {
    private final ModelMapper modelMapper = mock(ModelMapper.class);
    private final MainCourseRepository mainCourseRepository = mock(MainCourseRepository.class);
    private final DessertRepository dessertRepository = mock(DessertRepository.class);
    private final CuisineRepository cuisineRepository = mock(CuisineRepository.class);
    private final IOWriter io = mock(IOWriter.class);
    private final LunchCommand lunchCommand = new LunchCommand(io, mainCourseRepository, dessertRepository, cuisineRepository, modelMapper);
    private CuisineEntity cuisineEntity;
    private OrderingRequest orderingRequest;
    private DessertEntity dessertEntity;
    private MainCourseEntity mainCourseEntity;
    private Dessert dessert;
    private MainCourse mainCourse;
    private Lunch lunch;

    @BeforeEach
    void setUp() {
        orderingRequest = new OrderingRequest();
        cuisineEntity = new CuisineEntity();
        mainCourseEntity = new MainCourseEntity();
        dessertEntity = new DessertEntity();
        dessert = new Dessert();
        mainCourse = new MainCourse();
        lunch = new Lunch();
        cuisineEntity.setName("");
        dessertEntity.setName("entityName");
        dessertEntity.setPrice(BigDecimal.valueOf(4.5));
        mainCourseEntity.setName("entityName");
        mainCourseEntity.setPrice(BigDecimal.valueOf(4.5));
        dessert.setPrice(BigDecimal.valueOf(0.0));
        mainCourse.setPrice(BigDecimal.valueOf(0.0));
        lunch.setMainCourse(mainCourse);
        lunch.setDessert(dessert);
    }

    @Test
    @DisplayName("Should check if collected lunch is equal to expected lunch object")
    void shouldCheckIfCollectedLunchIsEqualToExpectedLunchObject() {
        given(io.read()).willReturn("0");
        given(cuisineRepository.findAll()).willReturn(List.of(cuisineEntity));
        given(mainCourseRepository.getMainCourseByProvidedCuisine(any())).willReturn(List.of(mainCourseEntity));
        given(dessertRepository.getDessertsByProvidedCuisine("")).willReturn(List.of(dessertEntity));
        given(modelMapper.map(dessertEntity, Dessert.class)).willReturn(dessert);
        given(modelMapper.map(mainCourseEntity, MainCourse.class)).willReturn(mainCourse);

        lunchCommand.execute(orderingRequest);

        assertThat(orderingRequest.getLunch()).usingRecursiveComparison().isEqualTo(lunch);
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
        List<AbstractCommand.IndexedDishes<MainCourseEntity>> availableMainCourses =
                List.of(AbstractCommand.IndexedDishes.of(Tuple.of("0", mainCourseEntity)));

        IllegalArgumentException thrown = Assertions
                .assertThrows(IllegalArgumentException.class, () -> {
                    ReflectionTestUtils.invokeMethod(lunchCommand, "getChosen", availableMainCourses, "1");
                });
        assertThat(thrown.getMessage()).isEqualTo("There is no element of that index!");
    }

    @Test
    @DisplayName("Should throw exception when user choose dessert that not exists")
    void shouldThrowExceptionWhenUserChooseDessertThatNotExists() {
        List<AbstractCommand.IndexedDishes<DessertEntity>> availableDesserts =
                List.of(AbstractCommand.IndexedDishes.of(Tuple.of("0", dessertEntity)));

        IllegalArgumentException thrown = Assertions
                .assertThrows(IllegalArgumentException.class, () -> {
                    ReflectionTestUtils.invokeMethod(lunchCommand, "getChosen", availableDesserts, "1");
                });
        assertThat(thrown.getMessage()).isEqualTo("There is no element of that index!");
    }
}