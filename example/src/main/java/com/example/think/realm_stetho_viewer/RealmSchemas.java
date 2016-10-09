package com.example.think.realm_stetho_viewer;

import io.realm.RealmConfiguration;

/**
 * Created by THINK on 2016/10/8.
 */

public class RealmSchemas {
    public static final RealmConfiguration SCHEMA_1=new RealmConfiguration.Builder()
            .name("schema1.realm")
            .schemaVersion(2)
            .deleteRealmIfMigrationNeeded()
            .build();
    public static final RealmConfiguration SCHEMA_2=new RealmConfiguration.Builder()
            .name("schema2.realm")
            .schemaVersion(3)
            .deleteRealmIfMigrationNeeded()
            .build();
    public static final RealmConfiguration SCHEMA_3=new RealmConfiguration.Builder()
            .name("schema3.realm")
            .schemaVersion(4)
            .deleteRealmIfMigrationNeeded()
            .build();
}
