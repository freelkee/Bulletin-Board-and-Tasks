package com.example.bulletinboardandtasks.models;

public class Announcement {
    private int id;
    private final String title;
    private final String author;
    private final String description;


    public Announcement(int id, String title, String author, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
    }
    public Announcement( String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }
}

