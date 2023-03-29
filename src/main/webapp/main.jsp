<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bulletin board and tasks</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<%
    HttpSession firstSession = request.getSession();
    if (firstSession.getAttribute("auth") == null) {
        firstSession.setAttribute("auth", "false");
        firstSession.setAttribute("username", "unknown");
    }%>
<h1>Welcome to the job board!</h1>
<p>Here you can find or post announcements about events directly or indirectly related to where you live.</p>
<p>You can also find or post jobs for a variety of jobs, including cleaning, shopping, translating, and much more.</p>
<jsp:include page="announcementtable"/>
<jsp:include page="tasktable"/>
<br>
<% if (!request.getSession().getAttribute("auth").equals("true")) {%>
<p>You need to log in or register to take part in the service.</p>
<%} else {%>
<jsp:include page="button.jsp"/>
<%}%>

</body>
</html>