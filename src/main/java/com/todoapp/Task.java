package com.todoapp;

/**
 * Represents a single to-do item.
 */
public class Task {

    private final int id;
    private final String description;
    private boolean done;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.done = false;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void markDone() {
        this.done = true;
    }

    @Override
    public String toString() {
        String status = done ? "[x]" : "[ ]";
        return String.format("%s %d. %s", status, id, description);
    }
}
