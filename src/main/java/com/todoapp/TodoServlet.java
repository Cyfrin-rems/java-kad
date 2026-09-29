package com.todoapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Web front for the to-do list.
 *
 * GET  /todo          -> shows the current list (index.jsp)
 * POST /todo?action=add    &description=...
 * POST /todo?action=done   &id=...
 * POST /todo?action=delete &id=...
 */
@WebServlet("/todo")
public class TodoServlet extends HttpServlet {

    // One shared instance for the whole app (in-memory, resets on redeploy).
    private final TaskManager manager = new TaskManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        showList(request, response, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String message = null;

        if ("add".equals(action)) {
            String description = request.getParameter("description");
            if (description == null || description.isBlank()) {
                message = "Description cannot be empty.";
            } else {
                Task task = manager.addTask(description.trim());
                message = "Added: " + task.getDescription();
            }
        } else if ("done".equals(action)) {
            int id = parseId(request.getParameter("id"));
            message = manager.markDone(id) ? "Marked task " + id + " done." : "No task with that id.";
        } else if ("delete".equals(action)) {
            int id = parseId(request.getParameter("id"));
            message = manager.deleteTask(id) ? "Deleted task " + id + "." : "No task with that id.";
        } else {
            message = "Unknown action.";
        }

        showList(request, response, message);
    }

    private int parseId(String raw) {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    private void showList(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("tasks", manager.getAllTasks());
        request.setAttribute("pending", manager.countPending());
        request.setAttribute("message", message);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
