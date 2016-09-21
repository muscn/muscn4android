package com.awecode.muscn.views;

import android.os.Bundle;
import android.util.Log;

import com.awecode.muscn.R;
import com.awecode.muscn.model.CountDownTime;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.countdown_timer.CountDownTimer;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeActivity extends BaseActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestFixturesList();
    }

    private void requestFixturesList() {
        Observable<FixturesResponse> call = mApiInterface.getFixtures();
        call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FixturesResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.v(TAG, "fixture response complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG, "fixture response error: "+e.getMessage());
                    }

                    @Override
                    public void onNext(FixturesResponse fixturesResponse) {
                        Log.v(TAG, "fixture response: " + fixturesResponse.getResults().get(0).getDatetime());
                    }
                });
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
