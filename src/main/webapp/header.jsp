<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Header</title>
    <style>
        /* horizontal menu */
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

        /* button in left up circle  */
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
    <a href="personal_account">My Account</a>
    <a href="logout">Log out</a>
    <%} else {%>
    <a href="login">Sign in</a>
    <a href="registration">Sign up</a>
    <%}%>
</div>

<ul>
    <li><a class="active" href="main.jsp">Home page</a></li>
    <li><a href="info.jsp">About Us</a></li>
    <li><a href="contacts.jsp">Contact</a></li>
</ul>

</body>
</html>