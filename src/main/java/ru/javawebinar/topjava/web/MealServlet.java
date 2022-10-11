package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private final MealDAO meals = new MealDAO();
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "showAll";
        }
        switch (action) {
            case "add":
            case "edit":
                log.debug("'add' or 'edit', forward to meals");
                Meal meal;
                if ("edit".equals(action)) {
                    meal = meals.get(getId(request));
                } else {
                    meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
                }
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            case "delete":
                log.debug("'delete', redirect to meals");
                meals.remove(getId(request));
                response.sendRedirect("meals");
                break;
            case "showAll":
                log.debug("forward to meals");
                request.setAttribute("meals", MealsUtil.getFilteredByStreams(meals.getAll()));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("'add' or 'edit', forward to meals");
        String mealId = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);
        if (mealId == null || mealId.isEmpty()) {
            meals.save(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            meals.save(meal);
        }
        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }
}
