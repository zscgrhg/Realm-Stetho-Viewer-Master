package com.example.tools;

import android.content.Context;

import com.facebook.stetho.inspector.database.ContentProviderDatabaseDriver;
import com.facebook.stetho.inspector.database.ContentProviderSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by THINK on 2016/10/9.
 * 因为 父类{@link ContentProviderDatabaseDriver#getDatabaseNames()}
 * 总是返回相同的name导致有多个数据库时chrome del tools无法正确显示,
 * 每个ContentProviderDbDriverWithDbName实例应该有全局唯一的dataBaseName
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
