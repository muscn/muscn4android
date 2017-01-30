package com.awecode.muscn.fcm;

import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.awecode.muscn.model.registration.RegistrationPostData;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
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
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("token", "Refreshed token: " + refreshedToken);
        //token is dyvEWeAE34U:APA91bHLbxAJK95SU1opJAN_gtdk2J4qB2x_F1sbNxv4WkUAuyluzGBgavojC-IX-hoaePZlTXafLW4WFPWkflSEXbiwNo94vyw4Dr428zYK_ilfhe9u0dSs608oDe69BWfEJVeW2lc1
//       s3 token :   cFZHq_00ylE:APA91bESPGb4f_vqFrdZFjfPhS6eLufa8xphpzFVZeiyi5LsIFm0ABDQJm4j-UH2zgHj-1ihJ3cgS3XMqOWO7DSe-r6mRYPaauLD48mS7QkAxUshkVn4-uuYvsTZjPmX5Ci13ZYWkDtg

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
    }

    public void sendRegistrationToServer(String refreshedToken) {
        String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        RegistrationPostData mRegistrationPostData = new RegistrationPostData(deviceId, refreshedToken, Build.MODEL, true);

        MuscnApiInterface mApiInterface = ServiceGenerator.createService(MuscnApiInterface.class);
        Observable<Void> call = mApiInterface.postRegistrationData(mRegistrationPostData);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });

    }
}
