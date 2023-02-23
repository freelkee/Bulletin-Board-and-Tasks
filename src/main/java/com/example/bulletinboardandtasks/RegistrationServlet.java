package com.example.bulletinboardandtasks;


import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "RegistrationPage", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Регистрация пользователя</title></head>");
        out.println("<body>");

        request.getRequestDispatcher("header.jsp").include(request, response);

        out.println("<h1>Регистрация пользователя</h1>");
        out.println("<form action=\"registration\" accept-charset =\"uft-8\" method=\"post\">");
        out.println("Имя пользователя: <input type=\"text\" name=\"username\" required><br>");
        out.println("Пароль: <input type=\"password\" name=\"password\" required><br>");
        out.println("<input type=\"submit\" value=\"Зарегистрироваться \">");
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

        if(saveUser(username,password)) {
            out.println("<head><title>Регистрация прошла успешно</title></head>");
            out.println("<body>");

            request.getRequestDispatcher("header.jsp").include(request, response);

            out.println("<h1>Регистрация прошла успешно</h1>");
            out.println("<p>Thank you for registering, " + username + ".</p>");

            out.println("<form action=\"login\" method=\"get\">");
            out.println("<input type=\"submit\" value=\"Войти\">");
            out.println("</form>");
        }
        else{
            out.println("<head><title>Ошибка регистрации</title></head>");
            out.println("<body>");

            request.getRequestDispatcher("header.jsp").include(request, response);

            out.println("<h1>Ошибка регистрации</h1>");
            out.println("<p>Попробуйте использовать другое имя пользователя.</p>");

            out.println("<form action=\"registration\" method=\"get\">");
            out.println("<input type=\"submit\" value=\"Повторить\">");
            out.println("</form>");
        }
        out.println("</body>");
        out.println("</html>");
    }


    public static boolean saveUser(String username, String password) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Connect to the database
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bulletin_board_and_tasks",
                    "postgres"," ");

            // Create the SQL statement
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the SQL statement
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
    }
}
