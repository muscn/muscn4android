package com.awecode.muscn.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
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

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by surensth on 12/7/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    String title, message, image, type;
    private NotificationData notificationData;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData() != null) {
            try {
                Map<String, String> maps = remoteMessage.getData();
                JSONObject object = new JSONObject(maps);
                if (object.has("message"))
                    message = object.getString("message");
                if (object.has("image"))
                    image = object.getString("image");
                if (object.has("title"))
                    title = object.getString("title");

                if (title != null && message != null && image != null)
                    notificationData = new NotificationData(title, message, image);
                else
                    notificationData = new NotificationData(title, message);

//                NotificationData notificationData = new NotificationData(image, message);
                createNotification(notificationData);

            } catch (Exception e) {
                e.printStackTrace();
                Log.v(TAG, "data not found, json exception" + e.getLocalizedMessage());

            }
        }
//        else
//            sendNotification(remoteMessage.getNotification().getBody());
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

    /**
     * creates notification
     *
     * @param notificationData is data to create notification, notification may be either title and message or title, message and image
     */
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

            if (notificationData.getImage() != null)
                mBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(getBitmapfromUrl(image)))/*Notification with Image*/
                        .setContentText(notificationData.getMessage());


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

    /*
  *To get a Bitmap image from the URL received
  * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}