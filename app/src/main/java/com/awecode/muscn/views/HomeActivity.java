package com.awecode.muscn.views;

import android.os.Bundle;
import android.util.Log;

import com.awecode.muscn.R;
import com.awecode.muscn.model.CountDownTime;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.countdown_timer.CountDownTimer;

public class HomeActivity extends BaseActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureCountDownTimer();

    }

    private void configureCountDownTimer() {
        CountDownTimer timer = new CountDownTimer();
        timer.setOnTimerListener(new CountDownTimer.TimerListener() {
            @Override
            public void onTick(long millisUntilFinished, CountDownTime countDownTime) {
                Log.v(TAG, "time ticked val: " + Util.getTwoDigitNumber(countDownTime.getDays()) + ":" + Util.getTwoDigitNumber(countDownTime.getHours()) + ":" + Util.getTwoDigitNumber(countDownTime.getMinutes())
                        + ":" + Util.getTwoDigitNumber(countDownTime.getSeconds()));
            }

            @Override
            public void onFinish() {
            }

        });
        timer.setTime(500000000);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
