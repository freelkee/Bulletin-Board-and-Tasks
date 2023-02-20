package com.example.bulletinboardandtasks;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "RegistrationPage", value = "/registration-page")
public class RegistrationServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>User Registration</title></head>");
        out.println("<body>");
        out.println("<h1>User Registration</h1>");
        out.println("<form action=\"registration-page\" accept-charset =\"uft-8\" method=\"post\">");
        out.println("Username: <input type=\"text\" name=\"username\" required><br>");
        out.println("Password: <input type=\"password\" name=\"password\" required><br>");
        out.println("<input type=\"submit\" value=\"Register\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if(saveUser(username,password)) {

            out.println("<html>");
            out.println("<head><title>Registration Successful</title></head>");
            out.println("<body>");
            out.println("<h1>Registration Successful</h1>");
            out.println("<p>Thank you for registering, " + username + ".</p>");
            out.println("</body>");
            out.println("</html>");
        }
        else{
            out.println("<html>");
            out.println("<head><title>Registration Failed</title></head>");
            out.println("<body>");
            out.println("<h1>Registration Failed</h1>");
            out.println("<p>Try using a different username.</p>");
            out.println("</body>");
            out.println("</html>");
        }
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
