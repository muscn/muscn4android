package com.awecode.muscn.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static String dateFormatter(String date) {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("dd MMM., yyyy", Locale.ENGLISH);
        try {
            Date originalDate = originalFormat.parse(date);
            date = targetFormat.format(originalDate);
            Log.v("Test", "date " + date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
