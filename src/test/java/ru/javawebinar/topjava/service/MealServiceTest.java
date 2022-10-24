package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_1_ID, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate startDate = LocalDate.of(2022, Month.OCTOBER, 22);
        LocalDate endDate = LocalDate.of(2022, Month.OCTOBER, 24);
        List<Meal> actualMeals = service.getBetweenInclusive(startDate, endDate, ADMIN_ID);
        assertMatch(actualMeals, adminMeal3, adminMeal2, adminMeal1);
    }

    @Test
    public void getAll() {
        List<Meal> actualMeals = service.getAll(USER_ID);
        assertMatch(actualMeals, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_NOT_FOUND_ID, USER_ID));
    }

    @Test
    public void getNotYoursMeal() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_2_ID, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_2_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_2_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_NOT_FOUND_ID, ADMIN_ID));
    }

    @Test
    public void deleteNotYoursMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_3_ID, ADMIN_ID));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(ADMIN_MEAL_1_ID, ADMIN_ID), getUpdated());
    }

    @Test
    public void updateNotYours() {
        assertThrows(NotFoundException.class, () -> service.update(adminMeal3, USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal duplicateMeal = new Meal(adminMeal7.getDateTime(), "Ужин администратора, повтор", 1000);
        assertThrows(DataAccessException.class, () -> service.create(duplicateMeal, ADMIN_ID));
    }
}