package com.example.bulletinboardandtasks.models;

import java.sql.Date;

public class Task {
    private Integer id;
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
