package com.awecode.muscn.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
import android.widget.TextView;
import android.widget.Toast;

import com.awecode.muscn.views.aboutus.CustomSpannable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
        DateFormat targetFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);
        try {
            Date originalDate = originalFormat.parse(date);
            date = targetFormat.format(originalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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

}
