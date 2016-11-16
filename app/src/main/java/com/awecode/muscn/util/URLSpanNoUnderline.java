package com.awecode.muscn.util;

import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * Created by surensth on 11/16/16.
 */

public class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String url) {
        super(url);
    }
    @Override public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }
}