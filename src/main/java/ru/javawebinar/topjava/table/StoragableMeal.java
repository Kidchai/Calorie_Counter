package ru.javawebinar.topjava.table;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface StoragableMeal {
    Meal save(Meal meal);
    Meal get(int id);
    void remove(int id);
    Collection<Meal> getAll();
}
