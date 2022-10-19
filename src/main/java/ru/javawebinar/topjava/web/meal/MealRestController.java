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

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = authUserId();
        checkNew(meal);
        log.info("create new meal {} for user {}", meal, userId);
        return service.create(userId, meal);
    }

    public void update(Meal meal) {
        log.info("update meal {}", meal);
        assureIdConsistent(meal, meal.getId());
        service.update(authUserId(), meal);
    }

    public void delete(int mealId) {
        int userId = authUserId();
        log.info("delete meal {} for user {}", mealId, userId);
        service.delete(userId, mealId);
    }

    public Meal get(int mealId) {
        int userId = authUserId();
        log.info("delete meal {} for user {}", mealId, userId);
        return service.get(userId, mealId);
    }

    public List<MealTo> getAll(LocalDate startDate, LocalDate endDate,
                               LocalTime startTime, LocalTime endTime) {
        int userId = authUserId();
        log.info("get all filtered meals for user {}", userId);
        startDate = startDate == null ? LocalDate.MIN : startDate;
        endDate = endDate == null ? LocalDate.MAX : endDate;
        startTime = startTime == null ? LocalTime.MIN : startTime;
        endTime = endTime == null ? LocalTime.MAX : endTime;

        return service.getAll(userId, startDate, endDate, startTime, endTime);
    }
}