package com.example.bulletinboardandtasks;

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
        out.println("<title>Личный кабинет</title>");
        out.println("</head>");
        out.println("<body>");

        RequestDispatcher dispatcher = request.getRequestDispatcher("header.jsp");
        dispatcher.include(request, response);

        HttpSession session = request.getSession();
        if (session.getAttribute("auth") != null && session.getAttribute("auth").equals("true")) {

            out.println("<h1>Добро пожаловать в личный кабинет, "+ session.getAttribute("username") +"!</h1>");
            out.println("<p>Вот некоторая совершенно секретная информация, которую можете видеть только вы...</p>");

            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


            String sql = "SELECT completed FROM users WHERE username = ?";

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/bulletin_board_and_tasks?useUnicode=true&charSet=UTF8",
                        "postgres", " ");
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, (String) session.getAttribute("username"));
                ResultSet rs = stmt.executeQuery();
                rs.next();
                out.println("<p>Количество выполненных заданий: " + rs.getInt("completed") + "</p>");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the statement and connection
                TaskTableServlet.closeConnection(new PrintWriter(System.out), conn, stmt);
            }

        } else {
            out.println("<h1>Вы не авторизованы. Повторите попытку после входа в систему.</h1>");
            out.println("<a href=\"main.jsp\">Вернуться на главную страницу</a>");
        }
        out.println("</body>");
        out.println("</html>");
    }
}