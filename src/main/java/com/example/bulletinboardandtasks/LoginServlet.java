package com.example.bulletinboardandtasks;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "Login", value = "/login")

public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>User Login</title></head>");
        out.println("<body>");

        request.getRequestDispatcher("header.jsp").include(request, response);

        out.println("<h1>User Login</h1>");
        out.println("<form action=\"login\" method=\"post\">");
        out.println("Имя пользователя: <input type=\"text\" name=\"username\"><br>");
        out.println("Пароль: <input type=\"password\" name=\"password\"><br>");
        out.println("<input type=\"submit\" value=\"Войти\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");

        if (isValidUser(username, password)) {
            out.println("<head><title>Вход в систему выполнен успешно</title></head>");
            out.println("<body>");

            HttpSession session = request.getSession();
            session.setAttribute("auth", "true");
            session.setMaxInactiveInterval(60*60);

            request.getRequestDispatcher("header.jsp").include(request, response);

            out.println("<h1>Вход в систему выполнен успешно</h1>");
            out.println("<p>Добро пожаловать, " + username + ".</p>");

            out.println("<form action=\"personal_account\" method=\"get\">");
            out.println("<input type=\"submit\" value=\"Войти в личный кабинет\">");
            out.println("</form>");


        } else {
            out.println("<head><title>Ошибка входа</title></head>");
            out.println("<body>");

            request.getRequestDispatcher("header.jsp").include(request, response);

            out.println("<h1>Ошибка входа</h1>");
            out.println("<p>Неверное имя пользователя или пароль.</p>");

            out.println("<form action=\"login\" method=\"get\">");
            out.println("<input type=\"submit\" value=\"Повторить попытку\">");
            out.println("</form>");
        }
        out.println("</body>");
        out.println("</html>");
    }

    public boolean isValidUser(String username, String password) {

        boolean isValid = false;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bulletin_board_and_tasks",
                    "postgres", " ");
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            isValid = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and connection
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isValid;
    }

}
