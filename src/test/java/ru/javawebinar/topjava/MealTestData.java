package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_1_ID = START_SEQ + 3;
    public static final int USER_MEAL_2_ID = START_SEQ + 4;
    public static final int USER_MEAL_3_ID = START_SEQ + 5;
    public static final int ADMIN_MEAL_1_ID = START_SEQ + 6;
    public static final int ADMIN_MEAL_2_ID = START_SEQ + 7;
    public static final int ADMIN_MEAL_3_ID = START_SEQ + 8;

    public static final int MEAL_NOT_FOUND_ID = 10;

    public static final Meal userMeal1 = new Meal(USER_MEAL_1_ID, LocalDateTime.of
            (2022, Month.OCTOBER, 21, 8, 0), "Завтрак пользователя", 400);
    public static final Meal userMeal2 = new Meal(USER_MEAL_2_ID, LocalDateTime.of
            (2022, Month.OCTOBER, 21, 14, 15), "Обед пользователя", 800);
    public static final Meal userMeal3 = new Meal(USER_MEAL_3_ID, LocalDateTime.of
            (2022, Month.OCTOBER, 21, 19, 10), "Ужин пользователя", 800);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_1_ID, LocalDateTime.of
            (2022, Month.OCTOBER, 24, 9, 0), "Завтрак администратора", 400);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL_2_ID, LocalDateTime.of
            (2022, Month.OCTOBER, 24, 14, 30), "Обед администратора", 650);
    public static final Meal adminMeal3 = new Meal(ADMIN_MEAL_3_ID, LocalDateTime.of
            (2022, Month.OCTOBER, 24, 20, 0), "Ужин администратора", 1000);


    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2022, Month.OCTOBER, 23, 10, 30),
                "Завтрак", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal();
        updated.setId(ADMIN_MEAL_1_ID);
        updated.setDateTime(LocalDateTime.of(2022, Month.OCTOBER, 22, 13, 30));
        updated.setDescription("Лёгкий завтрак администратора");
        updated.setCalories(350);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
