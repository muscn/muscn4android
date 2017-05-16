package com.awecode.muscn;

import android.app.Application;

import com.awecode.muscn.util.prefs.Prefs;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

/**
 * Created by munnadroid on 11/19/16.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize crashlytics
        Fabric.with(this, new Crashlytics());
        //initialize sharedpreference
        Prefs.initPrefs(this);
        //initialize realm db
        Realm.init(this);
    }
}
