<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Доска объявлений и заданий</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<%
    HttpSession firstSession = request.getSession();
    if (firstSession.getAttribute("auth") == null) {
        firstSession.setAttribute("auth", "false");
        firstSession.setAttribute("username","unknown");
    }%>
<h1>Добро пожаловать на доску объявлений и заданий 352-ой квартиры!</h1>
<p>Здесь вы можете найти или опубликовать объявления о событиях, прямо или косвенно связанных с квартирой 352 на Есенина
    26к1.</p>
<p>Также вы можете найти или опубликовать задания на различные виды работ, включая уборку, покупкой товаров, переводы и
    многое другое.</p>

<jsp:include page="tasktable"/>
<br>
<% if (!request.getSession().getAttribute("auth").equals("true")) {%>
<p>Для принятия участия в сервисе необходимо авторизоваться или зарегистрироваться.</p>
<%}else {%>
<jsp:include page="button.jsp"/>
<%}%>

</body>
</html>