
  Created by IntelliJ IDEA.
  User: freelkee
  Date: 20.02.2023
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Secure Page</title>
</head>
<body>
<%
    Cookie[] cookies = request.getCookies(); // получаем массив cookie
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth")) { // ищем cookie с именем "username"
                String auth = cookie.getValue(); // получаем значение cookie
                if (!auth.equals("true")) {
                    response.sendRedirect("index.jsp");
                } else {
                    break;
                }
                break;
            }
        }
    }
    else{
        response.sendRedirect("index.jsp");
    }
%>
<h1>Welcome to the Secure Page!</h1>
<p>You have successfully logged in.</p>
<p>Here's some top secret information that only authorized users can see...</p>
<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed auctor erat sed quam tempor, a luctus enim porttitor. Ut sed ipsum suscipit, sodales mi sed, bibendum turpis. Quisque a vestibulum sapien. Morbi rhoncus, velit at ullamcorper fermentum, sem nisl tristique felis, id sollicitudin odio tellus eu mi. Ut quis tellus magna. Aliquam erat volutpat. Donec sit amet laoreet nibh, sed consectetur est. Fusce eu semper mauris. Nullam at leo vel augue laoreet pellentesque non eget odio.</p>
<p>If you want to log out, just click the link below:</p>
<a href="LogoutServlet">Log Out</a>
</body>
</html>
