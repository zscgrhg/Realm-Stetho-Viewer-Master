package com.example.think.realm_stetho_viewer;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by THINK on 2016/10/9.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
