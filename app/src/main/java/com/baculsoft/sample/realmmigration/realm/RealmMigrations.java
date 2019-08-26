package com.baculsoft.sample.realmmigration.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;


public class RealmMigrations implements RealmMigration {

    /* this is the migration class when you want to upgrade database */
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();

        if (oldVersion == 1) { // compare with old version
            final RealmObjectSchema userSchema = schema.get("UserData");
            userSchema.addField("gender", String.class);
        }
    }
}