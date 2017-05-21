package com.awecode.muscn.fcm;

import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.awecode.muscn.model.registration.RegistrationResponse;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.prefs.Prefs;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;

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
        //token is dzgtBrUy8ww:APA91bHJtiUeaooRQjtpRWkx3VTsZtC8Chz9ax4IeTUaA6btNS6hm7Sd54BPUeT-SGgnTXDlWvK7d3Wf64wt7QKaU7tfHS6_YsTkyzsKUW5880SnWQrFAdhR2mQTHcSrl5qF-ygqUpmZ
//       s3 token :   cFZHq_00ylE:APA91bESPGb4f_vqFrdZFjfPhS6eLufa8xphpzFVZeiyi5LsIFm0ABDQJm4j-UH2zgHj-1ihJ3cgS3XMqOWO7DSe-r6mRYPaauLD48mS7QkAxUshkVn4-uuYvsTZjPmX5Ci13ZYWkDtg

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
        Log.v(TAG, "sendRegistrationToServer: pref token "+refreshedToken);
//        RegistrationPostData mRegistrationPostData = new RegistrationPostData(deviceId, refreshedToken, Build.MODEL, Constants.DEVICE_TYPE);

        MuscnApiInterface mApiInterface = ServiceGenerator.createService(MuscnApiInterface.class);
//        Observable<RegistrationResponse> call = mApiInterface.postRegistrationData(mRegistrationPostData);// gave 400 error in release apk only
        Observable<RegistrationResponse> call = mApiInterface.postRegistrationData(deviceId,
                refreshedToken, Build.MODEL, Constants.DEVICE_TYPE);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegistrationResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.v(TAG, "data com");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG, "error here " + new Gson().toJson(e).toString());

                    }

                    @Override
                    public void onNext(RegistrationResponse registrationResponse) {
                        Log.v("happ", "data succcess my" + new Gson().toJson(registrationResponse).toString());
                        Prefs.putBoolean(Constants.PREFS_DEVICE_REGISTERED,true);
                    }
                });

    }
}
