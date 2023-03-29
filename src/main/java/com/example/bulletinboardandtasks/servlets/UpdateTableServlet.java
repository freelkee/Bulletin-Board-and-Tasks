package com.example.bulletinboardandtasks.servlets;

import com.example.bulletinboardandtasks.models.Props;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "com.example.bulletinboardandtasks.UpdateTableServlet", value = "/update_table")
public class UpdateTableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

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

            if (request.getParameter("update").equals("takeUp")) {
                // Create the SQL statement
                String sql = ("UPDATE tasks set assignee = ?  where id = ?");
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, (String) request.getSession().getAttribute("username"));
                stmt.setInt(2, Integer.parseInt(request.getParameter("taskId")));


                stmt.executeUpdate();

            } else if (request.getParameter("update").equals("rejection")) {
                // Create the SQL statement
                String sql = ("UPDATE tasks set assignee = ?  where id = ?");
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, "");
                stmt.setInt(2, Integer.parseInt(request.getParameter("taskId")));

                stmt.executeUpdate();


            } else if (request.getParameter("update").equals("done")) {

                String sql = ("UPDATE tasks set is_done = ?  where id = ?");
                stmt = conn.prepareStatement(sql);
                stmt.setBoolean(1, true);
                stmt.setInt(2, Integer.parseInt(request.getParameter("taskId")));


                stmt.executeUpdate();

                sql = ("UPDATE users set completed = completed + 1 where username = ?");
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, (String) request.getSession().getAttribute("username"));

                stmt.executeUpdate();
            }else if (request.getParameter("update").equals("deleteAnnouncement")) {

                String sql = ("DELETE from announcements where id = ?");
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(request.getParameter("taskId")));


                stmt.executeUpdate();

            }
        } catch (
                SQLException e) {
            e.printStackTrace();

        } finally {
            TaskTableServlet.closeConnection(conn, stmt);
        }
        response.sendRedirect("main.jsp");
    }
}
