package com.awecode.muscn.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by surensth on 12/8/16.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("test", "Refreshed token: " + refreshedToken);
        //token is dyvEWeAE34U:APA91bHLbxAJK95SU1opJAN_gtdk2J4qB2x_F1sbNxv4WkUAuyluzGBgavojC-IX-hoaePZlTXafLW4WFPWkflSEXbiwNo94vyw4Dr428zYK_ilfhe9u0dSs608oDe69BWfEJVeW2lc1
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
    }
}
