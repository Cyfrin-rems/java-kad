package com.todoapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Holds and manipulates the list of tasks.
 * Shared as a single instance across requests via the servlet (see TodoServlet).
 */
public class TaskManager {

    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public synchronized Task addTask(String description) {
        Task task = new Task(nextId++, description);
        tasks.add(task);
        return task;
    }

    public synchronized boolean markDone(int id) {
        Optional<Task> found = findById(id);
        found.ifPresent(Task::markDone);
        return found.isPresent();
    }

    public synchronized boolean deleteTask(int id) {
        return tasks.removeIf(t -> t.getId() == id);
    }

    public synchronized List<Task> getAllTasks() {
        return List.copyOf(tasks);
    }

    public synchronized int countPending() {
        return (int) tasks.stream().filter(t -> !t.isDone()).count();
    }

    private Optional<Task> findById(int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst();
    }
}
