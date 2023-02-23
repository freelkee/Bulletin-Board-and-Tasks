
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Secure Page</title>
</head>
<body>
<%

    /*
    Optional<Cookie> auth = Arrays.stream(request.getCookies()).filter(o -> o.getName().equals("auth")).findAny();
    if(auth.isPresent()){
        if(!auth.get().getValue().equals("true")){
            response.sendRedirect("index.jsp");
        }
    }
    else {
        response.sendRedirect("index.jsp");
    }
     */
%>
<h1>Welcome to the Secure Page!</h1>
<p>You have successfully logged in.</p>
<p>Here's some top secret information that only authorized users can see...</p>
<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed auctor erat sed quam tempor, a luctus enim porttitor. Ut sed ipsum suscipit, sodales mi sed, bibendum turpis. Quisque a vestibulum sapien. Morbi rhoncus, velit at ullamcorper fermentum, sem nisl tristique felis, id sollicitudin odio tellus eu mi. Ut quis tellus magna. Aliquam erat volutpat. Donec sit amet laoreet nibh, sed consectetur est. Fusce eu semper mauris. Nullam at leo vel augue laoreet pellentesque non eget odio.</p>
    <p>If you want to log out, just click the link below:</p>
    <a href="logout">Log Out</a>
    </body>
    </html>
