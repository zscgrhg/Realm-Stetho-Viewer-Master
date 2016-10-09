# Realm-Stetho-Viewer-Master
android realm browser

####android开发使用chrome浏览器开发者工具查看 realm 数据

###首先引入Stetho 
[Stetho官网](http://facebook.github.io/stetho/)

[配置只在debug模式时启用Stetho](http://littlerobots.nl/blog/stetho-for-android-debug-builds-only/)

gradle
```
  repositories {
      jcenter()
      maven {
          url 'https://github.com/zscgrhg/Realm-Stetho-Viewer-Master/raw/master/maven-repo'
      }
  }

 dependencies { 
    compile 'com.facebook.stetho:stetho:1.4.1' 
    debugCompile 'com.zscgrhg:tools:1.1'
  } 
```

然后我们需要把想导出的real数据库注册成ContentProvider
假如我有如下realm配置:
```
public class RealmSchemas {
    public static final RealmConfiguration SCHEMA_1=new RealmConfiguration.Builder()
            .name("schema1.realm")
            .schemaVersion(2)
            .deleteRealmIfMigrationNeeded()
            .build();
}
```

简单注册1个ContentProvider即可

```
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
```

debug清单文件
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.example.think.realm_stetho_viewer">

    <application
        android:name=".MyDebugApplication"
        tools:replace="android:name">
        <provider
            android:name=".Schema1CP"
            android:authorities="com.example.think.realm_stetho_viewer.schema1"
            android:enabled="true"
            android:exported="true"
            android:label="hello"
            ></provider>
    </application>

</manifest>
```

debug Application类初始化Stetho

```
public class MyDebugApplication extends MyApplication {



    @Override
    public void onCreate() {
        super.onCreate();



        IMPBuilder impBuilder=new IMPBuilder(this);
        InspectorModulesProvider imp
                = impBuilder
                .addSchemas(new Schema1CP())
                .build();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(imp)
                        .build());
    }
}

```

启动模拟器，运行app

打开chrome浏览器,地址栏键入chrome://inspect/#devices

找到连接的设备,点inspect连接打开inspector UI 窗口
国内用户第一次打开时需要翻墙(需要下载google js库),否则你只会看到一个白板窗口

在 Web SQL 标签下面就可以看到realm数据库内容了

![chrome截图1](https://github.com/zscgrhg/Realm-Stetho-Viewer-Master/blob/master/chrome1.png)
![chrome截图2](https://github.com/zscgrhg/Realm-Stetho-Viewer-Master/blob/master/chrome3.png)

 


