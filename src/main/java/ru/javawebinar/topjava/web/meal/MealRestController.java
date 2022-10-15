package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create new meal {} for user {}", meal, userId);
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public Meal update(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("update meal {} for user {}", meal, userId);
        return service.update(userId, meal);
    }

    public void delete(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", meal, userId);
        service.delete(userId, meal.getId());
    }

    public Meal get(int mealId) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", mealId, userId);
        return service.get(userId, mealId);
    }

    public List<MealTo> getAll(User user) {
        int userId = SecurityUtil.authUserId();
        log.info("get all meals for user {}", userId);
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }
}