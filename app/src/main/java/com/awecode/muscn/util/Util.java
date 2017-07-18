package com.awecode.muscn.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.awecode.muscn.BuildConfig;
import com.awecode.muscn.model.http.api_error.APIError;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.awecode.muscn.views.aboutus.CustomSpannable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by munnadroid on 9/21/16.
 */
public class Util {


    public static String getAppVersion() {
        String versionCode = "0.0";
        try {
            versionCode = BuildConfig.VERSION_NAME;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

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
        originalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormat targetFormat = new SimpleDateFormat("EEE dd MMM ''yy", Locale.ENGLISH);
        try {
            Date originalDate = originalFormat.parse(date);
            date = targetFormat.format(originalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String commonDateFormatter(String date, String obtainedFormat) {
        String requiredFormat = "HH:mm EEE dd MMM ''yy";
        SimpleDateFormat format = new SimpleDateFormat(obtainedFormat);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat(requiredFormat);
        String time = format.format(newDate);
        return time;
    }

    public static String dateFormatter(String date, String obtainedFormat, String requiredFormat) {
        SimpleDateFormat format = new SimpleDateFormat(obtainedFormat);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat(requiredFormat);
        String time = format.format(newDate);
        return time;
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * This is function to make paragraph with read more and read less option
     *
     * @param tv         textview to show text
     * @param maxLine    number of lines to show initially
     * @param expandText
     * @param viewMore
     */
    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {
        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0,
                            lineEndIndex - expandText.length() + 1)
                            + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(tv.getText()
                                            .toString(), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0,
                            lineEndIndex - expandText.length() + 1)
                            + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(tv.getText()
                                            .toString(), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(
                            tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex)
                            + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(tv.getText()
                                            .toString(), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(
            final String strSpanned, final TextView tv, final int maxLine,
            final String spanableText, final boolean viewMore) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (strSpanned.contains(spanableText)) {
            ssb.setSpan(
                    new CustomSpannable(true) {

                        @Override
                        public void onClick(View widget) {

                            if (viewMore) {
                                tv.setLayoutParams(tv.getLayoutParams());
                                tv.setText(tv.getTag().toString(),
                                        TextView.BufferType.SPANNABLE);
                                tv.invalidate();
                                makeTextViewResizable(tv, -3, "...Read Less",
                                        false);
                            } else {
                                tv.setLayoutParams(tv.getLayoutParams());
                                tv.setText(tv.getTag().toString(),
                                        TextView.BufferType.SPANNABLE);
                                tv.invalidate();
                                makeTextViewResizable(tv, 3, "...Read More",
                                        true);
                            }

                        }
                    }, strSpanned.indexOf(spanableText),
                    strSpanned.indexOf(spanableText) + spanableText.length(), 0);
        }
        return ssb;
    }

    public static Spannable getHtmlText(String string) {
        Spanned spanned;
        Spannable spannable;
        if (Build.VERSION.SDK_INT >= 24) {
            spanned = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);// for 24 api and more
        } else {
            spanned = Html.fromHtml(string); // or for older api
        }
        spannable = new SpannableString(spanned);
        return spannable;
    }

    public static Spannable removeUnderline(String string) {
        Spannable s = getHtmlText(string);
        for (URLSpan u : s.getSpans(0, s.length(), URLSpan.class)) {
            s.setSpan(new UnderlineSpan() {
                public void updateDrawState(TextPaint tp) {
                    tp.setUnderlineText(false);
                }
            }, s.getSpanStart(u), s.getSpanEnd(u), 0);
        }
        return s;
    }

    /**
     * compare whether the match date is before todays date or not, if match date is before today then returns true showing data in index 1 else returns false showing data in index 0 for upcoming match
     *
     * @param matchDate date of upcoming match
     * @return
     */
    public static Boolean matchDateIsBeforeToday(String matchDate) {

        Calendar c = Calendar.getInstance();
        Date today = c.getTime();

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        myFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        //set time for countdown
        Date match_date = null;
        try {
            match_date = myFormat.parse(matchDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (match_date.before(today)) {
            return true;
        } else {
            return false;
        }
    }

    public static ScaleInAnimationAdapter getAnimationAdapter(RecyclerView.Adapter<?> adapter) {
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        return new ScaleInAnimationAdapter(alphaAdapter);
    }

    /**
     * if game is home and event has team home then player is from manutd
     * but if game is away and event has team away then player is from manutd
     *
     * @param isHomeGame home game or not
     * @param team       team in event score
     * @return
     */
    public static String getPlayerTeamName(Boolean isHomeGame, String team) {
        if (isHomeGame) {
            if (team.equalsIgnoreCase("home")) {
                return "manchester united";
            } else {
                return "away";
            }
        } else {
            if (team.equalsIgnoreCase("away")) {
                return "manchester united";
            } else {
                return "away";
            }
        }

    }

    /**
     * parse error and set error messages in APIError class and returns this class
     *
     * @param throwable
     * @return
     */
    public static APIError parseError(Throwable throwable) {
        Converter<ResponseBody, APIError> errorConverter =
                ServiceGenerator.retrofit.responseBodyConverter(APIError.class, new Annotation[0]);
        // Convert the error body into our Error type.
        APIError error = null;
        try {
            error = errorConverter.convert(((HttpException) throwable).response().errorBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return error;
    }

    public static String twoDigitFormat(String number) {
        return String.format("%02d", number);
    }

    public static String twoDigitFormat(int number) {
        return String.format("%02d", number);
    }
}
