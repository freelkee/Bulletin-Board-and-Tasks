package com.example.bulletinboardandtasks.servlets;


import com.example.bulletinboardandtasks.models.Props;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "Login", value = "/login")

public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Log in</title></head>");
        out.println("<body>");

        request.getRequestDispatcher("header.jsp").include(request, response);

        out.println("<h1>User Login</h1>");
        out.println("<form action=\"login\" method=\"post\" accept-charset=\"UTF-8\"> ");
        out.println("Username: <input type=\"text\" name=\"username\"><br>");
        out.println("Password: <input type=\"password\" name=\"password\"><br>");
        out.println("<input type=\"submit\" value=\"Log in\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();
        out.println("<html>");


        if (isValidUser(username, password)) {
            out.println("<head><title>Logging in is successful</title></head>");
            out.println("<body>");

            HttpSession session = request.getSession();
            session.setAttribute("auth", "true");
            session.setMaxInactiveInterval(60 * 60);
            session.setAttribute("username", username);

            request.getRequestDispatcher("header.jsp").include(request, response);

            out.println("<h1>Logging in is successful</h1>");
            out.println("<p>Welcome " + username + ".</p>");

            out.println("<form action=\"personal_account\" method=\"get\" accept-charset=\"UTF-8\">");
            out.println("<input type=\"submit\" value=\"Sign in\">");
            out.println("</form>");


        } else {
            out.println("<head><title>Login error</title></head>");
            out.println("<body>");

            request.getRequestDispatcher("header.jsp").include(request, response);

            out.println("<h1>Login error</h1>");
            out.println("<p>Incorrect user name or password.</p>");

            out.println("<form action=\"login\" method=\"get\" accept-charset=\"UTF-8\">");
            out.println("<input type=\"submit\" value=\"Try again\">");
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
            Props props = new Props();
            conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            isValid = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TaskTableServlet.closeConnection(conn, stmt);
        }
        return isValid;
    }

}
