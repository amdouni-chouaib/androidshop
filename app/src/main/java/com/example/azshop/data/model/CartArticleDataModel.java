package com.example.azshop.data.model;

public class CartArticleDataModel {
    //declaring our attributes
    public String id;
    public String imagPath;
    public String title;
    public String description;
    public String type;
    public Float price;
    public String gender;
    public String size;
    public String userId;
    //decalring out constructer for later use
    public CartArticleDataModel() {
    }

    public CartArticleDataModel(String id, String imagPath, String title, String description, String type, Float price, String gender, String size, String userId) {
        this.id = id;
        this.imagPath = imagPath;
        this.title = title;
        this.description = description;
        this.type = type;
        this.price = price;
        this.gender = gender;
        this.size = size;
        this.userId = userId;
    }
        //declaring out getter and setters  for later use
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagPath() {
        return imagPath;
    }

    public void setImagPath(String imagPath) {
        this.imagPath = imagPath;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        gender = gender;
    }

}

