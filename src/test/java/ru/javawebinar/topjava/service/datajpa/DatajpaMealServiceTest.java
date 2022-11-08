package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DatajpaMealServiceTest extends AbstractMealServiceTest {
    @Test
    public void getMealWithUser() {
        Meal actualMeal = service.getMealWithUser(ADMIN_MEAL_ID + 1, ADMIN_ID);
        USER_MATCHER.assertMatch(actualMeal.getUser(), admin);
        MEAL_MATCHER.assertMatch(actualMeal, adminMeal2);
    }
}