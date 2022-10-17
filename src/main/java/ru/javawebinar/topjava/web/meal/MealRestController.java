package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
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

    public void delete(int mealId) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", mealId, userId);
        service.delete(userId, mealId);
    }

    public Meal get(int mealId) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", mealId, userId);
        return service.get(userId, mealId);
    }

    public List<Meal> getAll(int userId) {
        log.info("get all meals for user {}", userId);
        return service.getAll(userId);
    }
}