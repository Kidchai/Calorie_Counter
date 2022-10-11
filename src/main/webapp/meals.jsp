<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        TABLE {
            width: 400px;
            border: 2px solid #75a3a3;
            background: #f0f5f5;
            border-collapse: collapse;
        }

        TD, TH {
            text-align: center;
            padding: 3px;
        }

        TH {
            color: #3d5c5c;
            border-bottom: 3px solid #75a3a3;
        }

        TD {
            border-bottom: 1px solid #75a3a3;
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

    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color: ${meal.exceed ? "#e60000" : "#006400"}">
            <td><%= TimeUtil.formatDateTime(meal.getDateTime())%>
            </td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
