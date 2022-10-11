package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealDao {
    void save(Meal meal);

    Meal get(int id);

    void remove(int id);

    Collection<Meal> getAll();
}
