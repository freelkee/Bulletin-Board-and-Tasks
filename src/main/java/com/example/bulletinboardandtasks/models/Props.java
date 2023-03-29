package com.example.bulletinboardandtasks.models;

public class Props {
    private String url = "jdbc:postgresql://localhost:5432/bulletin_board_and_tasks?useUnicode=true&charSet=UTF8";
    private String user = "postgres";
    private String password = " ";

    public Props() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
