package com.example.bulletinboardandtasks;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "Login", value = "/login")

public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>User Login</title></head>");
        out.println("<body>");
        out.println("<h1>User Login</h1>");
        out.println("<form action=\"login\" method=\"post\">");
        out.println("Username: <input type=\"text\" name=\"username\"><br>");
        out.println("Password: <input type=\"password\" name=\"password\"><br>");
        out.println("<input type=\"submit\" value=\"Login\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // check if the username and password are valid
        boolean isValid = isValidUser(username, password);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");

        if (isValid) {
            out.println("<head><title>Login Successful</title></head>");
            out.println("<body>");
            out.println("<h1>Login Successful</h1>");
            out.println("<p>Welcome back, " + username + ".</p>");

            Cookie authCookie = new Cookie("auth", "true");
            authCookie.setMaxAge(60 * 60 * 24); // устанавливаем срок действия cookie на 1 день
            response.addCookie(authCookie);

            out.println("<form action=\"secure.jsp\" method=\"get\">");
            out.println("<input type=\"submit\" value=\"Ok\">");
            out.println("</form>");


        } else {
            out.println("<head><title>Login Failed</title></head>");
            out.println("<body>");
            out.println("<h1>Login Failed</h1>");
            out.println("<p>Invalid username or password.</p>");

            out.println("<form action=\"login\" method=\"get\">");
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
