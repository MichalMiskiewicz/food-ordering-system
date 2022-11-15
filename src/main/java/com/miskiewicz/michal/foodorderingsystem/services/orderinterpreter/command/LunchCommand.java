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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class LunchCommand implements Command {

    private final Scanner scanner;
    private final MainCourseRepository mainCourseRepository;
    private final DessertRepository dessertRepository;
    private final CuisineRepository cuisineRepository;
    private final ModelMapper mapper;

    @Override
    public void execute(OrderingRequest orderingRequest) {
        Lunch lunch = new Lunch();
        List<CuisinePair> availableCuisines = getAvailableCuisines();
        availableCuisines.forEach(System.out::println);
        String chosenCuisine = scanner.nextLine();
        if (Integer.parseInt(chosenCuisine) >= availableCuisines.size()) {
            throw new IllegalArgumentException("There is no cuisine of that index!");
        }
        System.out.println("Choose main course:");
        List<MainCoursePair> availableMainCourses =
                getAvailableMainCourses(availableCuisines.get(Integer.parseInt(chosenCuisine)).cuisine().getName());
        availableMainCourses.forEach(System.out::println);
        String chosenMainCourse = scanner.nextLine();
        MainCourse mainCourse = getMainCourse(availableMainCourses, chosenMainCourse);
        lunch.setMainCourse(mainCourse);
        System.out.println("Choose dessert:");
        List<DessertPair> availableDesserts =
                getAvailableDesserts(availableCuisines.get(Integer.parseInt(chosenCuisine)).cuisine().getName());
        availableDesserts.forEach(System.out::println);
        String chosenDessert = scanner.nextLine();
        Dessert dessert = getDessert(availableDesserts, chosenDessert);
        lunch.setDessert(dessert);
        orderingRequest.setLunch(lunch);
        orderingRequest.addToFinalCost(dessert.getPrice());
        orderingRequest.addToFinalCost(mainCourse.getPrice());
    }

    private MainCourse getMainCourse(List<MainCoursePair> availableMainCourses, String chosenMainCourseIndex) {
        MainCoursePair chosenMainCourse = availableMainCourses.stream()
                .filter(mainCourse -> mainCourse.index().equals(chosenMainCourseIndex))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no main course of that index!"));
        return mapper.map(chosenMainCourse.mainCourse(), MainCourse.class);
    }

    private Dessert getDessert(List<DessertPair> availableDesserts, String chosenDessertIndex) {
        DessertPair chosenDessert = availableDesserts.stream()
                .filter(dessert -> dessert.index().equals(chosenDessertIndex))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no dessert of that index!"));
        return mapper.map(chosenDessert.dessert(), Dessert.class);
    }

    private List<CuisinePair> getAvailableCuisines() {
        List<CuisineEntity> cuisines = cuisineRepository.findAll();
        return IntStream.range(0, cuisines.size())
                .mapToObj(index -> CuisinePair.of(String.valueOf(index), cuisines.get(index)))
                .toList();
    }

    private List<MainCoursePair> getAvailableMainCourses(String chosenCuisine) {
        List<MainCourseEntity> mainCourses = mainCourseRepository.getMainCourseByProvidedCuisine(chosenCuisine);
        return IntStream.range(0, mainCourses.size())
                .mapToObj(index -> MainCoursePair.of(String.valueOf(index), mainCourses.get(index)))
                .toList();
    }

    private List<DessertPair> getAvailableDesserts(String chosenCuisine) {
        List<DessertEntity> desserts = dessertRepository.getDessertsByProvidedCuisine(chosenCuisine);
        return IntStream.range(0, desserts.size())
                .mapToObj(index -> DessertPair.of(String.valueOf(index), desserts.get(index)))
                .toList();
    }


    public record DessertPair(String index, DessertEntity dessert) {
        static DessertPair of(String index, DessertEntity dessert) {
            return new DessertPair(index, dessert);
        }

        @Override
        public String toString() {
            return "(" + index + ") " + dessert.toString();
        }
    }

    public record MainCoursePair(String index, MainCourseEntity mainCourse) {
        static MainCoursePair of(String index, MainCourseEntity mainCourse) {
            return new MainCoursePair(index, mainCourse);
        }

        @Override
        public String toString() {
            return "(" + index + ") " + mainCourse.toString();
        }
    }

    public record CuisinePair(String index, CuisineEntity cuisine) {
        static CuisinePair of(String index, CuisineEntity cuisine) {
            return new CuisinePair(index, cuisine);
        }

        @Override
        public String toString() {
            return "(" + index + ") " + cuisine.toString();
        }
    }
}
