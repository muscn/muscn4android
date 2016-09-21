package com.awecode.muscn.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.awecode.muscn.util.Util;

import butterknife.ButterKnife;

/**
 * Created by munnadroid on 9/21/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);
        mContext = this;
    }

    public void toast(String message) {
        Util.toast(mContext, message);
    }

    protected abstract int getLayoutResourceId();
}
