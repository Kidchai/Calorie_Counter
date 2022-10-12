<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<h2>Meal</h2>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="post" action="meals">
    <input type="hidden" name="mealId" value="${meal.id}"/>
    <label>
        <input type="datetime-local" name="dateTime" value="${meal.dateTime}" required/>
    </label>
    <hr>
    <label>
        <input type="text" name="description" value="${meal.description}" required/>
    </label>
    <hr>
    <label>
        <input type="number" name="calories" value="${meal.calories}" required/>
    </label>
    <hr>
    <input type="submit" value="Submit"/>
    <input type="button" onclick="history.back();" value="Cancel">
</form>
</body>
</html>
