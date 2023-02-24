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
    <title>Кнопка с формой</title>
    <meta charset="UTF-8">
</head>
<body>
<button onclick="showForm()">Добавить задание</button>
<div id="form" style="display: none;">
    <form action="tasktable" method="post" accept-charset="UTF-8">
        <label for="name">Наименование:</label>
        <input type="text" id="name" name="name"><br>

        <label for="subtasks">Подзадачи:</label>
        <textarea id="subtasks" name="subtasks"></textarea><br>

        <label for="assignee">Исполнитель:</label>
        <input type="text" id="assignee" name="assignee"><br>

        <label for="deadline">Deadline:</label>
        <input type="date" id="deadline" name="deadline"><br>

        <input type="submit" value="Отправить">
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






