package com.example.think.realm_stetho_viewer;

import com.example.tools.IMPBuilder;
import com.facebook.stetho.InspectorModulesProvider;
import com.facebook.stetho.Stetho;

/**
 * Created by THINK on 2016/10/8.
 */

public class MyDebugApplication extends MyApplication {



    @Override
    public void onCreate() {
        super.onCreate();



        IMPBuilder impBuilder=new IMPBuilder(this);
        InspectorModulesProvider imp
                = impBuilder
                .addSchemas(new Schema1CP())
                .addSchemas(new Schema2CP())
                .build();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(imp)
                        .build());
    }
}
