package com.awecode.muscn.util.countdown_timer;

import com.awecode.muscn.model.CountDownTime;

/**
 * Created by munnadroid on 9/21/16.
 */
public class CountDownTimer {
    private static final String TAG = CountDownTimer.class.getSimpleName();

    public interface TimerListener {
        void onTick(long millisUntilFinished, CountDownTime timer);

        void onFinish();
    }

    private long mDays = 0;
    private long mHours = 0;
    private long mMinutes = 0;
    private long mSeconds = 0;
    private long mMilliSeconds = 0;
    private android.os.CountDownTimer mCountDownTimer;
    private TimerListener mListener;

    public void setOnTimerListener(TimerListener listener) {
        mListener = listener;
    }

    public void setTime(long milliSeconds) {
        mMilliSeconds = milliSeconds;
        initCounter();
        calculateTime(milliSeconds);
        startCountDown();
    }

    public void startCountDown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.start();
        }
    }

    private void initCounter() {
        mCountDownTimer = new android.os.CountDownTimer(mMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                calculateTime(millisUntilFinished);

            }

            @Override
            public void onFinish() {
                calculateTime(0);
                if (mListener != null) {
                    mListener.onFinish();
                }
            }
        };
    }

    private void calculateTime(long milliSeconds) {
        mSeconds = (milliSeconds / 1000);
        mMinutes = mSeconds / 60;
        mSeconds = mSeconds % 60;

        mHours = mMinutes / 60;
        mMinutes = mMinutes % 60;

        mDays = mHours / 24;
        mHours = mHours % 24;

        if (mListener != null) {
            mListener.onTick(milliSeconds, new CountDownTime(mDays, mHours, mMinutes, mSeconds));
        }
    }


}
