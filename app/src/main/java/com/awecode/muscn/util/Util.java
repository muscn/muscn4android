package com.awecode.muscn.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by munnadroid on 9/21/16.
 */
public class Util {
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getTwoDigitNumber(long number) {
        if (number >= 0 && number < 10) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

}
