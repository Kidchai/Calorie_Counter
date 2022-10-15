package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    public boolean delete(int userId, int mealId) {
        return repository.delete(userId, mealId);
    }

    public Meal get(int userId, int mealId) {
        return repository.get(userId, mealId);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }



}