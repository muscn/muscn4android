package com.awecode.muscn.model.http.signup;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by surensth on 5/23/17.
 */
@Getter
@Setter
public class SignUpPostData implements Parcelable {
    @SerializedName("full_name")
    String fullName;
    String email;
    String password;
    String mobile;

    String provider;
    @SerializedName("token")
    String fbToken;

    @SerializedName("social")
    SignUpPostData socialData;

    public SignUpPostData() {
    }

    public SignUpPostData(String fullName, String email, String provider, String fbToken) {
        this.fullName = fullName;
        this.email = email;
        this.provider = provider;
        this.fbToken = fbToken;
    }

    public SignUpPostData(String provider, String fbToken) {
        this.provider = provider;
        this.fbToken = fbToken;
    }

    public SignUpPostData(String fullName, String email, String fbToken) {
        this.fullName = fullName;
        this.email = email;
        this.fbToken = fbToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullName);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.mobile);
        dest.writeString(this.provider);
        dest.writeString(this.fbToken);
    }

    protected SignUpPostData(Parcel in) {
        this.fullName = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.mobile = in.readString();
        this.provider = in.readString();
        this.fbToken = in.readString();
    }

    public static final Parcelable.Creator<SignUpPostData> CREATOR = new Parcelable.Creator<SignUpPostData>() {
        @Override
        public SignUpPostData createFromParcel(Parcel source) {
            return new SignUpPostData(source);
        }

        @Override
        public SignUpPostData[] newArray(int size) {
            return new SignUpPostData[size];
        }
    };
}
