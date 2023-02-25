<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Header</title>
    <style>
        /* Стили для горизонтального меню */
        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #333;
        }

        li {
            float: left;
        }

        li a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;

            text-decoration: none;
        }

        li a:hover:not(.active) {
            background-color: #111;
        }

        .active {
            background-color: #4CAF50;
        }

        /* Стили для кнопок в верхнем левом углу */
        .topnav {
            position: absolute;
            top: 8px;
            right: 8px;
            background-color: #333;
            overflow: hidden;
        }

        .topnav a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }

        .topnav a:hover {
            background-color: #ddd;
            color: black;
        }
    </style>
</head>
<body>

<div class="topnav">
    <%
        if (request.getSession().getAttribute("auth") != null && request.getSession().getAttribute("auth").equals("true")){
    %>
    <a href="personal_account">Личный кабинет</a>
    <a href="logout">Выйти</a>
    <%} else {%>
    <a href="login">Войти</a>
    <a href="registration">Зарегестрироваться</a>
    <%}%>
</div>

<ul>
    <li><a class="active" href="main.jsp">Главная</a></li>
    <li><a href="#">О нас</a></li>
    <li><a href="#">Контакты</a></li>
</ul>

</body>
</html>