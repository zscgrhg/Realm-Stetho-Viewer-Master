package com.example.think.realm_stetho_viewer;

import io.realm.RealmObject;

/**
 * Created by THINK on 2016/10/7.
 */

public class User extends RealmObject {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
