<%--
  Created by IntelliJ IDEA.
  User: freelkee
  Date: 24.02.2023
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Button</title>
    <meta charset="UTF-8">
</head>
<body>
<button onclick="showForm()">Add an Assignment</button>
<div id="form" style="display: none;">
    <form action="tasktable" method="post" accept-charset="UTF-8">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br>

        <label for="subtasks">Subtasks:</label>
        <textarea id="subtasks" name="subtasks"></textarea><br>

        <label for="assignee">Assignee:</label>
        <input type="text" id="assignee" name="assignee"><br>

        <label for="deadline">Deadline:</label>
        <input type="date" id="deadline" name="deadline" required><br>

        <input type="submit" value="Create">
    </form>
</div>
<script>
    function showForm() {
        var form = document.getElementById("form");
        if (form.style.display === "none") {
            form.style.display = "block";
        } else {
            form.style.display = "none";
        }
    }
</script>
</body>
</html>






