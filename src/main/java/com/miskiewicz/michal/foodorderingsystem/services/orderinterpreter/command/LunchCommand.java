package com.miskiewicz.michal.foodorderingsystem.services.orderinterpreter.command;

import com.miskiewicz.michal.foodorderingsystem.entities.CuisineEntity;
import com.miskiewicz.michal.foodorderingsystem.entities.DessertEntity;
import com.miskiewicz.michal.foodorderingsystem.entities.MainCourseEntity;
import com.miskiewicz.michal.foodorderingsystem.inout.InputOutput;
import com.miskiewicz.michal.foodorderingsystem.repositories.CuisineRepository;
import com.miskiewicz.michal.foodorderingsystem.repositories.DessertRepository;
import com.miskiewicz.michal.foodorderingsystem.repositories.MainCourseRepository;
import com.miskiewicz.michal.foodorderingsystem.requests.Dessert;
import com.miskiewicz.michal.foodorderingsystem.requests.Lunch;
import com.miskiewicz.michal.foodorderingsystem.requests.MainCourse;
import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;
import io.vavr.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class LunchCommand extends AbstractCommand implements Command {
    private final MainCourseRepository mainCourseRepository;
    private final DessertRepository dessertRepository;
    private final CuisineRepository cuisineRepository;
    private final ModelMapper mapper;

    public LunchCommand(InputOutput io, MainCourseRepository mainCourseRepository, DessertRepository dessertRepository, CuisineRepository cuisineRepository, ModelMapper mapper) {
        super(io);
        this.mainCourseRepository = mainCourseRepository;
        this.dessertRepository = dessertRepository;
        this.cuisineRepository = cuisineRepository;
        this.mapper = mapper;
    }

    @Override
    public void execute(OrderingRequest orderingRequest) {
        Lunch lunch = new Lunch();
        List<IndexedDishes<CuisineEntity>> availableCuisines = getAvailableCuisines();
        io.write("What cuisine do you prefer?:");
        write(availableCuisines);
        String chosenCuisine = io.read();
        io.write("Choose main course:");
        List<IndexedDishes<MainCourseEntity>> availableMainCourses =
                getAvailableMainCourses(getNameOfChosenCuisine(availableCuisines, chosenCuisine));
        write(availableMainCourses);
        String chosenMainCourse = io.read();
        MainCourseEntity mainCourseEntity= getChosen(availableMainCourses, chosenMainCourse);
        MainCourse mappedMainCourse = mapper.map(mainCourseEntity, MainCourse.class);
        lunch.setMainCourse(mappedMainCourse);
        io.write("Choose dessert:");
        List<IndexedDishes<DessertEntity>> availableDesserts =
                getAvailableDesserts(getNameOfChosenCuisine(availableCuisines, chosenCuisine));
        availableDesserts.forEach(dessert -> io.write(dessert.toString()));
        String chosenDessert = io.read();
        DessertEntity dessertEntity = getChosen(availableDesserts, chosenDessert);
        Dessert mappedDessert = mapper.map(dessertEntity, Dessert.class);
        lunch.setDessert(mappedDessert);
        orderingRequest.setLunch(lunch);
        orderingRequest.addToFinalCost(mappedDessert, mappedMainCourse);
    }

    private List<IndexedDishes<CuisineEntity>> getAvailableCuisines() {
        List<CuisineEntity> cuisines = cuisineRepository.findAll();
        return getIndexedDishes(cuisines);
    }

    private List<IndexedDishes<MainCourseEntity>> getAvailableMainCourses(String chosenCuisine) {
        List<MainCourseEntity> mainCourses = mainCourseRepository.getMainCourseByProvidedCuisine(chosenCuisine);
        return getIndexedDishes(mainCourses);
    }

    private List<IndexedDishes<DessertEntity>> getAvailableDesserts(String chosenCuisine) {
        List<DessertEntity> desserts = dessertRepository.getDessertsByProvidedCuisine(chosenCuisine);
        return getIndexedDishes(desserts);
    }

    private <T> List<IndexedDishes<T>> getIndexedDishes(List<T> elements) {
        return IntStream.range(0, elements.size())
                .mapToObj(index -> Tuple.of(String.valueOf(index), elements.get(index)))
                .map(IndexedDishes::of)
                .toList();
    }

    private <T> T getChosen(List<IndexedDishes<T>> courses, String chosen) {
        return courses.stream().filter(
                it -> it.getIndex().equals(chosen)
        ).map(IndexedDishes::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no element of that index!"));
    }

    private String getNameOfChosenCuisine(List<IndexedDishes<CuisineEntity>> availableCuisines, String chosenCuisine) {
        return availableCuisines.stream().filter(
                it -> it.getIndex().equals(chosenCuisine)
                ).map(IndexedDishes::getValue)
                .map(CuisineEntity::getName)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no cuisine of that index!"));
    }

}
