package com.awecode.muscn.util.prefs;

import com.awecode.muscn.model.http.signin.SignInSuccessData;
import com.awecode.muscn.util.Constants;
import com.google.gson.Gson;

/**
 * Created by munnadroid on 7/18/17.
 */

public class PrefsHelper {

    public static void saveLoginStatus(boolean status) {
        Prefs.putBoolean(Constants.PREFS_LOGIN_STATUS, status);
    }

    public static boolean getLoginStatus() {
        return Prefs.getBoolean(Constants.PREFS_LOGIN_STATUS, false);
    }

    public static void saveLoginToken(String token) {
        Prefs.putString(Constants.PREFS_LOGIN_TOKEN, token);
    }

    public static String getLoginToken() {
        return Prefs.getString(Constants.PREFS_LOGIN_TOKEN, "");
    }

    public static void saveLoginResponse(SignInSuccessData signInSuccessData) {
        Prefs.putString(Constants.PREFS_LOGIN_RESPONSE, new Gson().toJson(signInSuccessData));
    }

    public static SignInSuccessData getLoginResponse() {
        return new Gson().fromJson(Prefs.getString(Constants.PREFS_LOGIN_RESPONSE, ""), SignInSuccessData.class);
    }
}
