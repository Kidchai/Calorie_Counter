package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
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
        int mealId = meal.getId();
        int userId = authUserId();
        log.info("update meal {} for user {}", mealId, userId);
        assureIdConsistent(meal, mealId);
        service.update(authUserId(), meal, mealId);
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

    public List<MealTo> getAll() {
        int userId = authUserId();
        log.info("get all meals for user {}", userId);
        List<Meal> meals = service.getAll(userId);
        return convertToMealTo(meals).stream()
                .sorted(Comparator.comparing(MealTo::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate,
                                    LocalTime sTime, LocalTime eTime) {
        int userId = authUserId();
        log.info("get all filtered meals for user {}", userId);

        startDate = startDate == null ? LocalDate.MIN : startDate;
        endDate = endDate == null ? LocalDate.MAX : endDate;

        List<Meal> mealsByDate = service.getFilteredByDate(userId, startDate, endDate);

        LocalTime startTime = sTime == null ? LocalTime.MIN : sTime;
        LocalTime endTime = eTime == null ? LocalTime.MAX : eTime;

        return convertToMealTo(mealsByDate).stream()
                .filter(meal -> isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .sorted(Comparator.comparing(MealTo::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private List<MealTo> convertToMealTo(List<Meal> meals) {
        return MealsUtil.getTos(meals, authUserCaloriesPerDay());
    }
}