<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        TABLE {
            width: 400px; /* Ширина таблицы */
            border: 2px solid #75a3a3; /* Рамка вокруг таблицы */
            background: #f0f5f5; /* Цвет фона */
            border-collapse: collapse;
        }
        TD, TH {
            text-align: center; /* Выравнивание по центру */
            padding: 3px; /* Поля вокруг текста */
        }
        TH {
            color: #3d5c5c; /* Цвет текста */
            border-bottom: 3px solid #75a3a3; /* Двойная линия снизу */
        }
        TD {
            border-bottom: 1px solid #75a3a3; /* Линия снизу */
        }
    </style>
</head>
<body>
<h1>Meals</h1>

<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <c:forEach var="meal" items="${meals}" >
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color: ${meal.exceed ? "#e60000" : "#006400"}">
            <td><%= TimeUtil.formatDateTime(meal.getDateTime())%></td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
