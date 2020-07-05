package ru.nehodov.retrofitexample.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable {

    private int id;
    private Integer userId;
    private String title;

    @SerializedName("body")
    private String text;

    public Post(Integer userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }
}
