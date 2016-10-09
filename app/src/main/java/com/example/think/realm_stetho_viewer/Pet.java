package com.example.think.realm_stetho_viewer;

import io.realm.RealmObject;

/**
 * Created by THINK on 2016/10/8.
 */

public class Pet extends RealmObject {
    private String name;
    private int height;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
