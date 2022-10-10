package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    private static final List<Meal> meals;

    static {
        meals = new ArrayList<>();
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Breakfast", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Lunch", 1000));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Dinner", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Breakfast", 1000));
        meals.add( new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Lunch", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Dinner", 510));
    }

    public static List<MealTo> getMealList() {
        int caloriesPerDay = 2000;
        return filteredByStreams(meals, null, null, caloriesPerDay);
    }

    private static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
