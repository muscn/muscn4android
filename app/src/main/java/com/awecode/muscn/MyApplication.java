package com.awecode.muscn;

import android.app.Application;

import com.awecode.muscn.util.prefs.Prefs;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by munnadroid on 11/19/16.
 */
public class MyApplication extends Application {

    private static MuscnApiInterface mApiInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize crashlytics
        Fabric.with(this, new Crashlytics());
        //initialize sharedpreference
        Prefs.initPrefs(this);
        //initialize realm db
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .schemaVersion(1)
                .name("muscn.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public MuscnApiInterface getApiInterface() {
        if (mApiInterface == null)
            mApiInterface = ServiceGenerator.createService(MuscnApiInterface.class);
        return mApiInterface;
    }
}
