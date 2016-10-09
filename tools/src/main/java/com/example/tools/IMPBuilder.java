package com.example.tools;

import android.content.Context;
import android.net.Uri;

import com.facebook.stetho.InspectorModulesProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.inspector.database.ContentProviderDatabaseDriver;
import com.facebook.stetho.inspector.database.ContentProviderSchema;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.realm.RealmConfiguration;
import io.realm.RealmModel;

/**
 * Created by THINK on 2016/10/8.
 * 获取RealmConfiguration{@link RealmConfiguration}
 * 默认会将realm数据库文件名作为dataBaseName {@link ContentProviderDbDriverWithDbName}
 * 构建InspectorModulesProvider {@link InspectorModulesProvider}
 */

public class IMPBuilder {


    private Context context;

    private Map<String,ContentProviderSchema[]> dbSchemas=new HashMap<>();


    public IMPBuilder(Context context) {
        this.context = context;
    }

    public IMPBuilder addSchemas(RealmContentProvider contentProvider) {
        List<ContentProviderSchema> schemas = new ArrayList<>();
        RealmConfiguration configuration = contentProvider.getConfiguration();
        String auth = contentProvider.getAuthorities();

        Set<Class<? extends RealmModel>> realmObjectClasses = configuration.getRealmObjectClasses();


        for (Class<? extends RealmModel> realmObjectClass : realmObjectClasses) {
            String path = "/class_" + realmObjectClass.getSimpleName();
            ContentProviderSchema schema = new ContentProviderSchema.Builder()
                    .table(new ContentProviderSchema.Table.Builder()
                            .name(path.substring(1))
                            .uri(Uri.parse("content://" + auth + path))
                            .build()).build();
            schemas.add(schema);
        }
        ContentProviderSchema[] cps = schemas.toArray(new ContentProviderSchema[schemas.size()]);
        dbSchemas.put(contentProvider.getDbName(),cps);
        return this;

    }



    public InspectorModulesProvider build() {



        InspectorModulesProvider imp = new InspectorModulesProvider() {
            @Override
            public Iterable<ChromeDevtoolsDomain> get() {
                Stetho.DefaultInspectorModulesBuilder builder
                        = new Stetho.DefaultInspectorModulesBuilder(context);
                for (Map.Entry<String, ContentProviderSchema[]> dbSchema : dbSchemas.entrySet()) {
                    final ContentProviderDatabaseDriver databaseDriver =
                            new ContentProviderDbDriverWithDbName(dbSchema.getKey(),context, dbSchema.getValue());
                    builder.provideDatabaseDriver(databaseDriver);
                }
                return builder.finish();
            }
        };
        return imp;
    }
}
