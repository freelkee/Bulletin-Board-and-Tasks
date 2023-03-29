package com.example.bulletinboardandtasks.servlets;


import com.example.bulletinboardandtasks.models.Props;

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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>User registration</title></head>");
        out.println("<body>");

        request.getRequestDispatcher("header.jsp").include(request, response);

        out.println("<h1>User registration</h1>");
        out.println("<form action=\"registration\" method=\"post\" accept-charset=\"UTF-8\">");
        out.println("Username: <input type=\"text\" name=\"username\" required><br>");
        out.println("Password: <input type=\"password\" name=\"password\" required><br>");
        out.println("<input type=\"submit\" value=\"Sign up\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        PrintWriter out = response.getWriter();
        out.println("<html>");

        if(saveUser(username,password)) {
            out.println("<head><title>Registration was successful</title></head>");
            out.println("<body>");

            request.getRequestDispatcher("header.jsp").include(request, response);

            out.println("<h1>Registration was successful</h1>");
            out.println("<p>Thank you for registering, " + username + ".</p>");

            out.println("<form action=\"login\" method=\"get\" accept-charset=\"UTF-8\">");
            out.println("<input type=\"submit\" value=\"Sign in\">");
            out.println("</form>");
        }
        else{
            out.println("<head><title>Registration error</title></head>");
            out.println("<body>");

            request.getRequestDispatcher("header.jsp").include(request, response);

            out.println("<h1>Registration error</h1>");
            out.println("<p>Try using a different username.</p>");

            out.println("<form action=\"registration\" method=\"get\" " +
                    "accept-charset=\"UTF-8\">");
            out.println("<input type=\"submit\" value=\"Try again\">");
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
            Props props = new Props();
            conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());

            // Create the SQL statement
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the SQL statement
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            TaskTableServlet.closeConnection(conn, stmt);
        }
    }
}
