package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

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
        return getAllMeals(userId).remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllMeals(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private Map<Integer, Meal> getAllMeals(int userId) {
        return repository.getOrDefault(userId, new ConcurrentHashMap<>());
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return getAll(userId).stream()
                .filter(meal -> startDate.compareTo(meal.getDate()) <= 0 && endDate.compareTo(meal.getDate()) >= 0)
                .filter(meal -> startTime.compareTo(meal.getTime()) <= 0 && endTime.compareTo(meal.getTime()) >= 0)
                .collect(Collectors.toList());
    }
}

