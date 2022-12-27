package com.example.azshop.data.model;

public class MenuDataModel {
    //declaring out attricutes
    public String name;
    public int res;
//declaring our constructor
    public MenuDataModel(String name, int res) {
        this.name = name;
        this.res = res;
    }
//declaring getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }
}
