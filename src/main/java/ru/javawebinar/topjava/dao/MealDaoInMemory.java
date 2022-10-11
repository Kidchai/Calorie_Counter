package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemory implements MealDao {
    private final Map<Integer, Meal> mealMap = new Hashtable<>();
    public static final AtomicInteger index = new AtomicInteger();

    {
        MealsUtil.getAllMeals().forEach(this::save);
    }

    @Override
    public void save(Meal meal) {
        if (!meal.hasId()) {
            meal.setId(index.getAndIncrement());
            mealMap.put(meal.getId(), meal);
            return;
        }

        mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public Meal get(int id) {
        return mealMap.get(id);
    }

    @Override
    public void remove(int id) {
        mealMap.remove(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return mealMap.values();
    }
}
