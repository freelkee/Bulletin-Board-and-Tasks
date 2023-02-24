package com.example.bulletinboardandtasks;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PersonalAccount", value = "/personal_account")
public class PersonalAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Личный кабинет</title>");
        out.println("</head>");
        out.println("<body>");
        RequestDispatcher dispatcher = request.getRequestDispatcher("header.jsp");
        dispatcher.include(request, response);

        HttpSession session = request.getSession();
        if (session.getAttribute("auth") != null && session.getAttribute("auth").equals("true")) {

            out.println("<h1>Добро пожаловать в личный кабинет!</h1>");
            out.println("<p>Вот некоторая совершенно секретная информация, которую можете видеть только вы...</p>");

        } else {
            out.println("<h1>Вы не авторизованы. Повторите попытку после входа в систему.</h1>");
            response.sendRedirect("<a href=\"main.jsp\">Вернуться на главную страницу</a>");
        }
        out.println("</body>");
        out.println("</html>");
    }
}