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

import static com.example.bulletinboardandtasks.servlets.TaskTableServlet.closeConnection;

@WebServlet("/announcementtable")
public class AnnouncementTableServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/html;charset=UTF-8");

        TaskTableServlet.htmlHeader(out);
        out.println("<h2>Доска объявлений</h2>");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bulletin_board_and_tasks?useUnicode=true&charSet=UTF8",
                    "postgres", " ");

            String sql = "SELECT id, title, author, description FROM announcements ORDER BY id DESC";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

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
                if (announcement.getAuthor().equals(request.getSession().getAttribute("username"))) {
                    out.println("<td>");

                    out.println("<form action=\"update_table?update=deleteAnnouncement&taskId=" +
                            announcement.getId() + "\" method=\"post\" accept-charset=\"UTF-8\">");
                    out.println("<input type=\"submit\" value=\"Удалить\">");
                    out.println("</form>");

                    out.println("</td>");
                } else
                    out.println("<td>" + announcement.getAuthor() + "</td>");

                out.println("<td>" + announcement.getDescription() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn, stmt);
        }
        out.println("<br>");
        if (request.getSession().getAttribute("auth").equals("true")) {
            request.getRequestDispatcher("announcementButton.jsp").include(request, response);
        }
        out.println("</body>");
        out.println("</html>");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String author = (String) request.getSession().getAttribute("username");
        String description = request.getParameter("description");

        Announcement announcement = new Announcement(title, author, description);

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Connect to the database
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bulletin_board_and_tasks?useUnicode=true&charSet=UTF8",
                    "postgres", " ");

            String sql = "INSERT INTO announcements (title, author, description) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, announcement.getTitle());
            stmt.setString(2, announcement.getAuthor());
            stmt.setString(3, announcement.getDescription());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Close the statement and connection
            closeConnection(conn, stmt);
        }
        response.sendRedirect("main.jsp");
    }
}

