package com.example.think.realm_stetho_viewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Random;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private Realm realm1;
    private Realm realm2;
    final Random random=new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm1=Realm.getInstance(RealmSchemas.SCHEMA_1);
        realm2=Realm.getInstance(RealmSchemas.SCHEMA_2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm1.close();
        realm2.close();
    }

    public void onClick(View view) {
        realm1.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.createObject(User.class); // Create a new object
                user.setName(Names.NAME[random.nextInt(687)]+"    "+Integer.toString(random.nextInt(9000)+1000));
                user.setAge(random.nextInt(100));
                Pet p=realm.createObject(Pet.class);
                p.setName("Pet"+Integer.toString(random.nextInt(300)));
                p.setColor("Color"+Integer.toString(random.nextInt(300)));
                p.setHeight(random.nextInt(300));
                Food food=realm.createObject(Food.class);
                food.setName("Food"+Integer.toString(random.nextInt(3000)));
                food.setPrice(random.nextInt(3000));
            }
        });

        Log.d("USER1",Long.toString(realm1.where(User.class).count()));
        realm2.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.createObject(User.class); // Create a new object
                user.setName(Names.NAME[random.nextInt(687)]+"    "+Integer.toString(random.nextInt(9000)+1000));
                user.setAge(random.nextInt(100));
                Pet p=realm.createObject(Pet.class);
                p.setName("Pet"+Integer.toString(random.nextInt(300)));
                p.setColor("Color"+Integer.toString(random.nextInt(300)));
                p.setHeight(random.nextInt(300));
                Food food=realm.createObject(Food.class);
                food.setName("Food"+Integer.toString(random.nextInt(3000)));
                food.setPrice(random.nextInt(3000));
            }
        });

        Log.d("USER2",Long.toString(realm2.where(User.class).count()));
    }


}
