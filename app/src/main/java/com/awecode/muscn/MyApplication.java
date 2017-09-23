package com.awecode.muscn;

import android.app.Application;

import com.awecode.muscn.util.prefs.Prefs;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.awecode.muscn.util.retrofit.feed.FeedApiInterface;
import com.awecode.muscn.util.retrofit.feed.FeedClient;
import com.crashlytics.android.Crashlytics;

import java.io.FileNotFoundException;

import io.fabric.sdk.android.Fabric;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by munnadroid on 11/19/16.
 */
public class MyApplication extends Application {

    private static MuscnApiInterface mApiInterface;
    private static FeedApiInterface mFeedApiInterface;
    public Realm mRealm;
    private RealmConfiguration config;

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize crashlytics
        Fabric.with(this, new Crashlytics());
        //initialize sharedpreference
        Prefs.initPrefs(this);
        //initialize realm db
        Realm.init(this);
        config = new RealmConfiguration
                .Builder()
                .schemaVersion(3)
                .name("muscn.realm")
                .build();
        Realm.setDefaultConfiguration(config);
        getRealmInstance();
    }

    public Realm getRealmInstance() {

        try {
            if (mRealm == null)
                mRealm = Realm.getDefaultInstance();
        } catch (RealmMigrationNeededException e) {
            e.printStackTrace();
            try {
                Realm.migrateRealm(config, new RealmMigration() {
                    @Override
                    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                        // DynamicRealm exposes an editable schema
                        RealmSchema schema = realm.getSchema();

                        if (oldVersion == 1) {

                            schema.create("Item")
                                    .addField("title", String.class)
                                    .addField("link", String.class)
                                    .addField("description", String.class)
                                    .addField("pubDate", String.class);

                            oldVersion++;
                        }
                        if (oldVersion == 2) {

                            RealmObjectSchema livscreenSchema = schema.create("LiveScreening")
                                    .addField("id", Integer.class)
                                    .addField("name", String.class)
                                    .addField("slug", String.class)
                                    .addField("logo", String.class)
                                    .addField("about", String.class)
                                    .addField("privileges", String.class)
                                    .addField("url", String.class)
                                    .addField("active", Boolean.class)
                                    .addField("location", String.class)
                                    .addField("order", Integer.class);

                            RealmObjectSchema partnersResultSchema = schema.create("PartnersResult")
                                    .addField("id", Integer.class)
                                    .addField("name", String.class)
                                    .addField("partnership", String.class)
                                    .addField("slug", String.class)
                                    .addField("logo", String.class)
                                    .addField("about", String.class)
                                    .addField("privileges", String.class)
                                    .addField("url", String.class)
                                    .addField("active", Boolean.class)
                                    .addField("location", Integer.class)
                                    .addField("order", Integer.class);

                            schema.get("Result").addRealmObjectField("liveScreening", livscreenSchema)
                                    .addRealmObjectField("PartnersResult", partnersResultSchema);
                            oldVersion++;
                        }

                    }
                });
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }

        return mRealm;
    }

    public MuscnApiInterface getApiInterface() {
        if (mApiInterface == null)
            mApiInterface = ServiceGenerator.createService(MuscnApiInterface.class);
        return mApiInterface;
    }

    public FeedApiInterface getFeedApiInterface() {
        if (mFeedApiInterface == null)
            mFeedApiInterface = FeedClient.createService(FeedApiInterface.class);
        return mFeedApiInterface;
    }
}
