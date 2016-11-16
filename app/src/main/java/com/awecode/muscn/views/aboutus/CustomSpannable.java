package com.awecode.muscn.views.aboutus;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
/**
 * Created by surensth on 11/16/16.
 */


public class CustomSpannable extends ClickableSpan {

    private boolean isUnderline = true;

    public CustomSpannable(boolean isUnderline) {
        this.isUnderline = isUnderline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {

        ds.setUnderlineText(isUnderline);
        ds.setColor(Color.parseColor("#4888F3"));    }

    @Override
    public void onClick(View widget) {

    }
}