package com.baculsoft.sample.realmmigration;

import android.app.Application;

import com.baculsoft.sample.realmmigration.realm.RealmMigrations;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Budi Oktaviyan Suryanto (budioktaviyans@gmail.com)
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        /* increment schema version whenever change in database and want to upgrade it */
        final RealmConfiguration configuration = new RealmConfiguration.Builder().name("sample.realm").schemaVersion(1).migration(new RealmMigrations()).build();
        Realm.setDefaultConfiguration(configuration);
        Realm.getInstance(configuration);
    }

    @Override
    public void onTerminate() {
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }
}