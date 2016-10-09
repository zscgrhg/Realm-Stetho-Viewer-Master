package com.example.tools;

import android.content.Context;

import com.facebook.stetho.inspector.database.ContentProviderDatabaseDriver;
import com.facebook.stetho.inspector.database.ContentProviderSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by THINK on 2016/10/9.
 */

public class ContentProviderDbDriverWithDbName extends ContentProviderDatabaseDriver {
    private List<String> mDatabaseNames=new ArrayList<>();


    public ContentProviderDbDriverWithDbName(String dataBaseName, Context context, ContentProviderSchema... contentProviderSchemas) {
        super(context, contentProviderSchemas);
        mDatabaseNames.add(dataBaseName);
    }

    @Override
    public List<String> getDatabaseNames() {
        return mDatabaseNames;
    }
}
