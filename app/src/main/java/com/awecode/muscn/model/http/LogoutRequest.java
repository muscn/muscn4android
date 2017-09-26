package com.awecode.muscn.model.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by munnadroid on 9/26/17.
 */

public class LogoutRequest {
    @SerializedName("reg_id")
    String fcmToken;

    public LogoutRequest(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
