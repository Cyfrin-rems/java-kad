<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.todoapp.Task" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>To-Do List</title>
    <style>
        body { font-family: sans-serif; max-width: 480px; margin: 40px auto; }
        li.done { text-decoration: line-through; color: #888; }
        .message { color: #2a6; margin-bottom: 12px; }
        form.inline { display: inline; }
        input[type=text] { padding: 4px; }
    </style>
</head>
<body>
    <h1>To-Do List</h1>

    <% String message = (String) request.getAttribute("message"); %>
    <% if (message != null) { %>
        <p class="message"><%= message %></p>
    <% } %>

    <form method="post" action="todo">
        <input type="hidden" name="action" value="add">
        <input type="text" name="description" placeholder="New task" required>
        <button type="submit">Add</button>
    </form>

    <ul>
        <%
            @SuppressWarnings("unchecked")
            List<Task> tasks = (List<Task>) request.getAttribute("tasks");
            for (Task task : tasks) {
        %>
        <li class="<%= task.isDone() ? "done" : "" %>">
            <%= task.getId() %>. <%= task.getDescription() %>

            <% if (!task.isDone()) { %>
            <form class="inline" method="post" action="todo">
                <input type="hidden" name="action" value="done">
                <input type="hidden" name="id" value="<%= task.getId() %>">
                <button type="submit">Done</button>
            </form>
            <% } %>

            <form class="inline" method="post" action="todo">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="<%= task.getId() %>">
                <button type="submit">Delete</button>
            </form>
        </li>
        <% } %>
    </ul>

    <p><%= request.getAttribute("pending") %> pending task(s).</p>
</body>
</html>
last
