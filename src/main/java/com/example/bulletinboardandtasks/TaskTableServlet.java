package com.example.bulletinboardandtasks;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/tasktable")
public class TaskTableServlet extends HttpServlet {

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

        out.println("<title>Доска задач</title>");
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
        out.println("<h1>Доска задач</h1>");
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/bulletin_board_and_tasks",
                "postgres", " ")) {

            String sql = "SELECT task_name, subtasks, assignee, deadline FROM tasks";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            List<Task> tasks = new ArrayList<>();
            while (result.next()) {
                String taskName = result.getString("task_name");
                String subtaskName = result.getString("subtasks");
                String assignee = result.getString("assignee");
                Date deadline = result.getDate("deadline");
                tasks.add(new Task(taskName, subtaskName, assignee, deadline));
            }

            out.println("<table>");
            out.println("<tr><th>Наименование задачи</th><th>Подзадачи</th><th>Исполнитель</th><th>Deadline</th></tr>");
            for (Task task : tasks) {
                out.println("<tr>");
                out.println("<td>" + task.getTaskName() + "</td>");
                out.println("<td>" + task.getSubtaskName() + "</td>");
                out.println("<td>" + task.getAssignee() + "</td>");
                out.println("<td>" + task.getDeadline() + "</td>");
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
        private final String taskName;
        private final String subtaskName;
        private final String assignee;
        private final Date deadline;

        public Task(String taskName, String subtaskName, String assignee, Date deadline) {
            this.taskName = taskName;
            this.subtaskName = subtaskName;
            this.assignee = assignee;
            this.deadline = deadline;
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
    }
}
