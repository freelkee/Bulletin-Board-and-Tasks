package com.example.bulletinboardandtasks.servlets;

import com.example.bulletinboardandtasks.models.Props;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "PersonalAccount", value = "/personal_account")
public class PersonalAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();


        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>My Account</title>");
        out.println("</head>");
        out.println("<body>");

        RequestDispatcher dispatcher = request.getRequestDispatcher("header.jsp");
        dispatcher.include(request, response);

        HttpSession session = request.getSession();

        if (session.getAttribute("auth") != null && session.getAttribute("auth").equals("true")) {

            authorizedUsersPage(out, session);

        } else {
            out.println("<h1>You are not logged in. Try again after logging in.</h1>");
            out.println("<a href=\"main.jsp\">Back to the home page</a>");
        }
        out.println("</body>");
        out.println("</html>");
    }

    private static void authorizedUsersPage(PrintWriter out, HttpSession session) {
        out.println("<h1>Welcome to your personal account " + session.getAttribute("username") + "!</h1>");
        out.println("<p>Here is some top secret information that only you can see...</p>");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        String sql = "SELECT completed FROM users WHERE username = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Props props = new Props();
            conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, (String) session.getAttribute("username"));
            ResultSet rs = stmt.executeQuery();
            rs.next();
            out.println("<p>Number of tasks completed: " + rs.getInt("completed") + "</p>");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TaskTableServlet.closeConnection(conn, stmt);
        }
    }
}