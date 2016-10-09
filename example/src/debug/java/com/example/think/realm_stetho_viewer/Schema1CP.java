package com.example.think.realm_stetho_viewer;

import com.example.tools.RealmContentProvider;

import io.realm.RealmConfiguration;

/**
 * Created by THINK on 2016/10/8.
 */

public class Schema1CP extends RealmContentProvider {

    @Override
    public String getDbName() {
        return "realmDB1";
    }

    @Override
    public RealmConfiguration getConfiguration() {
        return RealmSchemas.SCHEMA_1;
    }

    @Override
    public String getAuthorities() {
        return "com.example.think.realm_stetho_viewer.schema1";
    }
}
