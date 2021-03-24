package com.erlite.feedhub.models;

public class Posts {

    private String title;
    private String description;
    private String category;
    private String img_url;
    private String date;
    private String content;
    private String id;


    public Posts() {
    }

    public Posts(String title, String description, String category, String img_url, String date) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.img_url = img_url;
        this.date = date;
        this.content = content;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
