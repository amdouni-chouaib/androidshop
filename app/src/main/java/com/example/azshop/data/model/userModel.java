package com.example.azshop.data.model;

public class userModel {
    //declaring our attricutes

    public String id;
    public String fname;
    public String lname;
    public String email;
    public String phone;
//declaring our constructor for later use
    public userModel() {
    }

    public userModel(String id, String fname, String lname, String email, String phone) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
    }
//declaring getter and setters for later use
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
