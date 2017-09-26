package com.awecode.muscn.fcm;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.awecode.muscn.MyApplication;
import com.awecode.muscn.model.registration.RegistrationPostData;
import com.awecode.muscn.model.registration.RegistrationResponse;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.prefs.Prefs;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by munnadroid on 9/26/17.
 */

public class FCMRegistrationService {
    public static void sendFCMRegistrationToken(Context context){
        try {
            final String deviceId = Settings.Secure.getString(context.getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            String refreshedToken = Prefs.getString(Constants.PREFS_REFRESH_TOKEN, "");
            if (!TextUtils.isEmpty(refreshedToken)) {
                RegistrationPostData mRegistrationPostData = new RegistrationPostData(deviceId, refreshedToken, Build.MODEL, Constants.DEVICE_TYPE);

                Observable<RegistrationResponse> call = ((MyApplication) context.getApplicationContext())
                        .getApiInterface().postRegistrationData(mRegistrationPostData);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
