package com.awecode.muscn.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.awecode.muscn.R;
import com.awecode.muscn.model.notification.NotificationData;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.views.HomeActivity;
import com.awecode.muscn.views.notification.NotificationActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by surensth on 12/7/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    String title, message, image, type;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.v(TAG, "message received");
        if (remoteMessage.getData() != null) {
            try {
                Map<String, String> maps = remoteMessage.getData();
                JSONObject object = new JSONObject(maps);
                message = object.getString("message");
                image = object.getString("image");
                NotificationData notificationData = new NotificationData(image,message);
                createNotification(notificationData);

            } catch (Exception e) {
                e.printStackTrace();
                Log.v(TAG, "data not found, json exception" + e.getLocalizedMessage());

            }
        } else
            sendNotification(remoteMessage.getNotification().getBody());
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    // Creates notification based on title and body received
    private void createNotification(NotificationData notificationData) {
        try {
            Context context = getBaseContext();

            Intent intent = new Intent(this, NotificationActivity.class);
            intent.putExtra(Constants.INTENT_NOTIFICATION_DATA, notificationData);

            // Next, let's turn this into a PendingIntent using
            // public static PendingIntent getActivity(Context context, int requestCode,
            // Intent intent, int flags)
            int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
            int flags = PendingIntent.FLAG_UPDATE_CURRENT; // cancel old intent and create new one
            PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, flags);
            // Now we can attach this to the notification using setContentIntent

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

            Resources res = this.getResources();
            mBuilder.setStyle(new NotificationCompat.BigTextStyle(mBuilder)
                    .bigText(notificationData.getMessage())
                    .setBigContentTitle(notificationData.getTitle()))
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(notificationData.getMessage())
                    .setContentIntent(pIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setAutoCancel(false);

            // Hide the notification after its selected
            mBuilder.setAutoCancel(true);

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // mId allows you to update the notification later on.
            mNotificationManager.notify(requestID, mBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}