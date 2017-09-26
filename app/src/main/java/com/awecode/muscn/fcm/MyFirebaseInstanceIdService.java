package com.awecode.muscn.fcm;

import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.awecode.muscn.MyApplication;
import com.awecode.muscn.model.registration.RegistrationPostData;
import com.awecode.muscn.model.registration.RegistrationResponse;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.prefs.Prefs;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by surensth on 12/8/16.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseInstanceIdSer";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("token", "Refreshed token: " + refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        Prefs.putString(Constants.PREFS_REFRESH_TOKEN, refreshedToken);
        sendRegistrationToServer();
    }

    public void sendRegistrationToServer() {
        final String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String refreshedToken = Prefs.getString(Constants.PREFS_REFRESH_TOKEN, "");
        RegistrationPostData mRegistrationPostData = new RegistrationPostData(deviceId, refreshedToken, Build.MODEL, Constants.DEVICE_TYPE);

        MuscnApiInterface mApiInterface = ((MyApplication) this.getApplicationContext()).getApiInterface();
        Observable<RegistrationResponse> call = mApiInterface.postRegistrationData(mRegistrationPostData);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegistrationResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RegistrationResponse registrationResponse) {
                        Prefs.putBoolean(Constants.PREFS_DEVICE_REGISTERED, true);
                    }
                });

    }
}
