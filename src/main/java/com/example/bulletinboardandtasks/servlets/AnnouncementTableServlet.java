package com.example.bulletinboardandtasks.servlets;

import com.example.bulletinboardandtasks.models.Announcement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/announcementtable")
public class AnnouncementTableServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<style>");
        out.println("table {");
        out.println("border-collapse: collapse;");
        out.println("width: 100%;");
        out.println("}");
        out.println("th, td {");
        out.println("text-align: left;");
        out.println("padding: 8px;");
        out.println("}");
        out.println("tr:nth-child(even){background-color: #f2f2f2}");
        out.println("th {");
        out.println("background-color: #4CAF50;");
        out.println("color: white;");
        out.println("}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<h2>Объявления</h2>");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bulletin_board_and_tasks?useUnicode=true&charSet=UTF8",
                    "postgres", " ");

            String sql = "SELECT id, title, author, description FROM announcements ORDER BY id DESC";
            stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery(sql);

            List<Announcement> announcements = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String author = result.getString("author");
                String description = result.getString("description");
                announcements.add(new Announcement(id, title, author, description));
            }

            out.println("<table>");
            out.println("<tr>" +
                    "<th>ID</th>" +
                    "<th>Заголовок</th>" +
                    "<th>Автор</th>" +
                    "<th>Описание</th>" +
                    "</tr>");

            for (Announcement announcement : announcements) {
                out.println("<tr>");
                out.println("<td>" + announcement.getId() + "</td>");
                out.println("<td>" + announcement.getTitle() + "</td>");
                out.println("<td>" + announcement.getAuthor() + "</td>");
                out.println("<td>" + announcement.getDescription() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            if (request.getSession().getAttribute("auth").equals("true")) {
                out.println("<h2>Создать новое объявление</h2>");
                out.println("<form action=\"announcementtable\" method=\"post\">");
                out.println("<label for=\"title\">Заголовок:</label><br>");
                out.println("<input type=\"text\" id=\"title\" name=\"title\"><br>");
                out.println("<label for=\"author\">Автор:</label><br>");
                out.println("<input type=\"text\" id=\"author\" name=\"author\"><br>");
                out.println("<label for=\"description\">Описание:</label><br>");
                out.println("<textarea id=\"description\" name=\"description\" rows=\"4\" cols=\"50\"></textarea><br><br>");
                out.println("<input type=\"submit\" value=\"Создать\">");
                out.println("</form>");
            }

            out.println("</body>");
            out.println("</html>");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            // Close the statement and connection
            TaskTableServlet.closeConnection(conn, stmt);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String description = request.getParameter("description");

        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/bulletin_board_and_tasks",
                "postgres", " ")) {

            String sql = "INSERT INTO announcements (title, author, description) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, description);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/announcementtable");
    }
}

