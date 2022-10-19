package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
        save(1, new Meal(LocalDateTime.of(2022, Month.JANUARY, 31, 10, 0), "Завтрак пользователя 1", 700));
        save(2, new Meal(LocalDateTime.of(2022, Month.JANUARY, 31, 10, 0), "Завтрак пользователя 2", 800));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        repository.putIfAbsent(userId, new ConcurrentHashMap<>());
        Map<Integer, Meal> meals = getAllMeals(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        return repository.get(userId).remove(id, get(userId, id));
    }

    @Override
    public Meal get(int userId, int id) {
        return repository.get(userId).getOrDefault(id, null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return new ArrayList<>(repository.getOrDefault(userId, new ConcurrentHashMap<>()).values());
    }

    @Override
    public List<Meal> getFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId).stream()
                .filter(meal -> startDate.compareTo(meal.getDate()) <= 0 && endDate.compareTo(meal.getDate()) >= 0)
                .collect(Collectors.toList());
    }

    private Map<Integer, Meal> getAllMeals(int userId) {
        return repository.getOrDefault(userId, new ConcurrentHashMap<>());
    }
}