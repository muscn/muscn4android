package com.awecode.muscn.util.retrofit.error;


import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by munnadroid on 9/24/17.
 */

public class ErrorUtils {
    public static String parseError(Throwable t) {
        if (t instanceof HttpException) {
            ResponseBody body = ((HttpException) t).response().errorBody();
            return parseErrorBody(body);
        } else {
            String errorMessage = t.getMessage();
            if (TextUtils.isEmpty(errorMessage))
                errorMessage = "Network error.Please try again.";
            return errorMessage;
        }

    }

    public static String parseErrorBody(ResponseBody errorBody) {


        String errorMessage = "Network error.Please try again.";
        try {
            String errorStr = errorBody.string();
            JSONObject object = new JSONObject(errorStr);
            if (object.has("detail"))
                errorMessage = object.getString("detail");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return errorMessage;
    }
}
