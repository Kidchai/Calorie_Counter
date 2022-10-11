<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        table {
            width: 400px;
            border: 2px solid #75a3a3;
            background: #f0f5f5;
            border-collapse: collapse;
            margin-bottom: 15px;
        }

        td, th {
            text-align: center;
            padding: 3px;
        }

        th {
            color: #3d5c5c;
            border-bottom: 3px solid #75a3a3;
        }

        td {
            border-bottom: 1px solid #75a3a3;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h1>Meals</h1>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>

    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color: ${meal.excess ? "#e60000" : "#006400"}">
            <td>${meal.formattedDateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?id=${meal.id}&action=edit">Edit</a></td>
            <td><a href="meals?id=${meal.id}&action=delete">Delete</a></td>
        </tr>
    </c:forEach>
</table>

<h3><a href="meals?action=add">Add new meal</a></h3>

</body>
</html>
