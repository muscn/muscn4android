package com.awecode.muscn.fcm;

import android.util.Log;

import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.prefs.Prefs;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

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
        FCMRegistrationService.sendFCMRegistrationToken(this);
    }
}
