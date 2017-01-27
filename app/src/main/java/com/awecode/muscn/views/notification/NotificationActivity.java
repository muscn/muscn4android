package com.awecode.muscn.views.notification;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.notification.NotificationData;
import com.awecode.muscn.util.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends Activity {
    @BindView(R.id.notificationTitle)
    TextView mNotificationTitleTextView;
    @BindView(R.id.notificationImageView)
    ImageView mNotificationImageView;
    @BindView(R.id.notificationMessageTextView)
    TextView mNotificationMessageTextView;
    private NotificationData mNotificationData;
    private String title, imageURl, message;
    DisplayMetrics metrics;
    int width = 0, height = 0;
    int requiredImageWidth, requiredImageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        //for screen sizes
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;
        width = metrics.widthPixels;
        requiredImageHeight = height / 2;
        requiredImageWidth = width;


        mNotificationData = (NotificationData) getIntent().getExtras().getParcelable(Constants.INTENT_NOTIFICATION_DATA);
        imageURl = mNotificationData.getImage();
        message = mNotificationData.getMessage();
        title = mNotificationData.getTitle();
        if (message != null)
            mNotificationMessageTextView.setText(message);
        if (imageURl != null)
            Picasso.with(getApplicationContext()).load(imageURl).resize(requiredImageWidth, requiredImageHeight).into(mNotificationImageView);
        if(title !=null)
            mNotificationTitleTextView.setText(title);
    }
}
