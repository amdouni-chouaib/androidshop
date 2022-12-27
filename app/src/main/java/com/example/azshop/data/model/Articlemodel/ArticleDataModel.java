package com.example.azshop.data.model.Articlemodel;
public class ArticleDataModel {
    //declaring attributes
    public String id;
    public String imagPath;
    public String title;
    public String description;
    public String type;
    public Float price;
    public String gender;
    public String date;
    public String userId;

//making our constructor for later use
    public ArticleDataModel() {
    }

    public ArticleDataModel(
            String id,
            String imagPath,
            String title,
            String description,
            String type,
            float price,
            String gender, String date,
            String userId) {
        this.id = id;
        this.imagPath = imagPath;
        this.title = title;
        this.description = description;
        this.type = type;
        this.price = price;
        this.gender = gender;
        this.date = date;
        this.userId = userId;


    }
//making getter and setter for our metthods for later use
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
