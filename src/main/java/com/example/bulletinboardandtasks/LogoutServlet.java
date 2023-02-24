package com.example.bulletinboardandtasks;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Удаляем cookie, созданный для авторизации пользователя
        HttpSession session = request.getSession();
        session.setAttribute("auth","false");

        // Перенаправляем пользователя на страницу входа
        response.sendRedirect("main.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

}
