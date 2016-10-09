package com.example.tools;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmResults;

public abstract class RealmContentProvider extends ContentProvider {


    public  String getDbName(){
        String realmFileName = getConfiguration().getRealmFileName();
        return realmFileName;
    }
    public abstract RealmConfiguration getConfiguration();

    public Realm getRealm() {

        return Realm.getInstance(getConfiguration());
    }



    public  abstract String getAuthorities() ;

    private  final Map<String,Class<? extends RealmModel>> table_class=new HashMap<>();
    public RealmContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {

        String path = uri.getPath();
        Class<? extends RealmModel> modelClazz = table_class.get(path);
        if(modelClazz==null){
            throw new UnsupportedOperationException("Not yet implemented");
        }else {
            return "vnd.android.cursor.dir/vnd."+getAuthorities()+"."+path;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        Realm.init(getContext());
        Realm realm=getRealm() ;
        Set<Class<? extends RealmModel>> realmObjectClasses = realm.getConfiguration().getRealmObjectClasses();
        for (Class<? extends RealmModel> realmObjectClass : realmObjectClasses) {
            table_class.put("/class_"+realmObjectClass.getSimpleName(),realmObjectClass);
        }
        realm.close();
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Realm realm=getRealm();

        String path = uri.getPath();
        Class<? extends RealmModel> modelClazz = table_class.get(path);
        if(modelClazz==null){
            throw new UnsupportedOperationException("Not yet implemented");
        }
        RealmResults<? extends RealmModel> all = realm.where(modelClazz).findAll();
        all.load();
        List<? extends RealmModel> realmModels = realm.copyFromRealm(all,1);
        UselessRealmCursor realmCursor = new UselessRealmCursor(realmModels, modelClazz);
        realm.close();
        return realmCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
