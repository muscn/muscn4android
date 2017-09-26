package com.awecode.muscn;

import android.app.Application;

import com.awecode.muscn.util.prefs.Prefs;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.awecode.muscn.util.retrofit.feed.FeedApiInterface;
import com.awecode.muscn.util.retrofit.feed.FeedClient;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
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
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Realm.getInstance(config);
        //init fb sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public Realm getRealmInstance() {

        try {
            if (mRealm == null)
                mRealm = Realm.getDefaultInstance();
        } catch (RealmMigrationNeededException e) {
            e.printStackTrace();
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
