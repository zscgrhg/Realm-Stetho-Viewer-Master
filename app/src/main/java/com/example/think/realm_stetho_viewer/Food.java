package com.example.think.realm_stetho_viewer;

import io.realm.RealmObject;

/**
 * Created by THINK on 2016/10/8.
 */

public class Food extends RealmObject {
    private String name;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
