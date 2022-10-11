package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.table.MealTable;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private MealTable meals = new MealTable();
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
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
                response.sendRedirect("meals.jsp");
                break;
            default:
                log.debug("forward to meals");
                request.setAttribute("meals", MealsUtil.getFilteredByStreams(meals.getAll()));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }
}
