package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// TODO add userId
public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(int userId, Meal meal);

    boolean delete(int userId, int id);

    // null if meal does not belong to userId
    Meal get(int userId, int id);

    // ORDERED dateTime desc
    List<MealTo> getAll(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
}
