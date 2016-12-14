package com.awecode.muscn;

import com.crashlytics.android.Crashlytics;
import com.orm.SugarApp;

import io.fabric.sdk.android.Fabric;

/**
 * Created by munnadroid on 11/19/16.
 */
public class MyApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }
}
