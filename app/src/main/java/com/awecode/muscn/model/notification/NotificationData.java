package com.awecode.muscn.model.notification;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by surensth on 12/8/16.
 */

public class NotificationData implements Parcelable {
    String title;
    String message;
    String image;

    public NotificationData(String image, String message) {
        this.image = image;
        this.title = title;
        this.message = message;
    }


    protected NotificationData(Parcel in) {
        title = in.readString();
        message = in.readString();
        image = in.readString();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static final Creator<NotificationData> CREATOR = new Creator<NotificationData>() {
        @Override
        public NotificationData createFromParcel(Parcel in) {
            return new NotificationData(in);
        }

        @Override
        public NotificationData[] newArray(int size) {
            return new NotificationData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(image);
    }
}
