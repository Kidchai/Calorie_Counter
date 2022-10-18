package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.getAuthUserId;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("create new meal {} for user {}", meal, getAuthUserId());
        return service.create(getAuthUserId(), meal);
    }

    public void update(int id, Meal meal) {
        log.info("update meal {}", meal);
        service.update(getAuthUserId(), meal);
    }

    public void delete(int mealId) {
        log.info("delete meal {} for user {}", mealId, getAuthUserId());
        service.delete(getAuthUserId(), mealId);
    }

    public Meal get(int mealId) {
        log.info("delete meal {} for user {}", mealId, getAuthUserId());
        return service.get(getAuthUserId(), mealId);
    }

    public List<MealTo> getAll(LocalDate startDate, LocalDate endDate,
                               LocalTime startTime, LocalTime endTime) {
        int userId = getAuthUserId();
        log.info("get all filtered meals for user {}", userId);
        startDate = startDate == null ? LocalDate.MIN : startDate;
        endDate = endDate == null ? LocalDate.MAX : endDate;
        startTime = startTime == null ? LocalTime.MIN : startTime;
        endTime = endTime == null ? LocalTime.MAX : endTime;

        return service.getAll(userId, startDate, endDate, startTime, endTime);
    }
}