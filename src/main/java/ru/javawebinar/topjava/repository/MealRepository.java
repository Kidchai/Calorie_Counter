package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealRepository {
    Meal save(int userId, Meal meal);

    boolean delete(int userId, int id);

    Meal get(int userId, int id);

    List<MealTo> getAll(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
}
