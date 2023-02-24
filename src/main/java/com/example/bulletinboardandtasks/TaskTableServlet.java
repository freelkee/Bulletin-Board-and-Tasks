package com.example.bulletinboardandtasks;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/tasktable")
public class TaskTableServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String name = request.getParameter("name");
        String subtasks = request.getParameter("subtasks");
        String assignee = request.getParameter("assignee");
        Date deadline = Date.valueOf(request.getParameter("deadline"));
        String author = (String) request.getSession().getAttribute("username");

        Task task = new Task(name,subtasks,assignee,deadline,author);

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Connect to the database
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bulletin_board_and_tasks?useUnicode=true&charSet=UTF8",
                    "postgres"," ");

            // Create the SQL statement
            String sql = "INSERT INTO tasks (task_name, subtasks, assignee, deadline, author) VALUES (?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,  task.getTaskName());
            stmt.setString(2, task.getSubtaskName());
            stmt.setString(3, task.getAssignee());
            stmt.setDate(4, task.getDeadline());
            stmt.setString(5, task.getAuthor());

            // Execute the SQL statement
            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            // Close the statement and connection
            closeConnection(out, conn, stmt);
        }
        response.sendRedirect("main.jsp");
    }

    static void closeConnection(PrintWriter out, Connection conn, PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            out.println("<h3>SQL Exception:</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        out.println("<h2>Доска задач</h2>");
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/bulletin_board_and_tasks",
                "postgres", " ")) {

            String sql = "SELECT id, task_name, subtasks, assignee, deadline, author FROM tasks";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            List<Task> tasks = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("id");
                String taskName = result.getString("task_name");
                String subtaskName = result.getString("subtasks");
                String assignee = result.getString("assignee");
                Date deadline = result.getDate("deadline");
                String author = result.getString("author");
                tasks.add(new Task(id, taskName, subtaskName, assignee, deadline, author));
            }

            out.println("<table>");
            out.println("<tr>" +
                    "<th>ID</th>" +
                    "<th>Наименование</th>" +
                    "<th>Подзадачи</th>" +
                    "<th>Исполнитель</th>" +
                    "<th>Deadline</th>" +
                    "<th>Автор</th>" +
                    "</tr>");

            for (Task task : tasks) {
                out.println("<tr>");
                out.println("<td>" + task.getId() + "</td>");
                out.println("<td>" + task.getTaskName() + "</td>");
                out.println("<td>" + task.getSubtaskName() + "</td>");
                out.println("<td>" + task.getAssignee() + "</td>");
                out.println("<td>" + task.getDeadline() + "</td>");
                out.println("<td>" + task.getAuthor() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

        } catch (SQLException e) {
            out.println("<h3>SQL Exception:</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
        }

        out.println("</body></html>");
    }

    private static class Task {
        private final int id;
        private final String taskName;
        private final String subtaskName;
        private final String assignee;
        private final Date deadline;
        private final String author;

        public Task(int id, String taskName, String subtaskName, String assignee, Date deadline, String author) {
            this.id = id;
            this.taskName = taskName;
            this.subtaskName = subtaskName;
            this.assignee = assignee;
            this.deadline = deadline;
            this.author = author;
        }

        public Task(String taskName, String subtaskName, String assignee, Date deadline, String author) {
            this.id = 0;
            this.taskName = taskName;
            this.subtaskName = subtaskName;
            this.assignee = assignee;
            this.deadline = deadline;
            this.author = author;
        }

        public int getId() {
            return id;
        }

        public String getTaskName() {
            return taskName;
        }

        public String getSubtaskName() {
            return subtaskName;
        }

        public String getAssignee() {
            return assignee;
        }

        public Date getDeadline() {
            return deadline;
        }

        public String getAuthor() {
            return author;
        }

    }
}
