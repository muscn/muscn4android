package com.awecode.muscn.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.awecode.muscn.R;
import com.awecode.muscn.model.CountDownTime;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.http.fixtures.Result;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.countdown_timer.CountDownTimer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by surensth on 2/2/17.
 */

public class UpdateWidgetService extends Service {
    RemoteViews remoteViews;
//    Context mContext = this;
    private CountDownTimer mCountDownTimer;
    AppWidgetManager manager;
    ComponentName widget;
    int[] mAppWidgetIds;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
         remoteViews = buildUpdate(this);
         widget = new ComponentName(this, FixtureWidgetProvider.class);
         manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(widget, remoteViews);
        mAppWidgetIds = manager.getAppWidgetIds(widget);
        Log.v("ids","app widget ids "+mAppWidgetIds[0]);
    }

    private RemoteViews buildUpdate(Context context) {
         remoteViews = new RemoteViews(context.getPackageName(), R.layout.fixture_widget);
        //clock data
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        remoteViews.setTextViewText(R.id.hourTextView,String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
//        remoteViews.setTextViewText(R.id.minuteTextView,String.valueOf(calendar.get(Calendar.MINUTE)));
//        remoteViews.setTextViewText(R.id.secondTextView,String.valueOf(calendar.get(Calendar.SECOND)));

        initializeCountDownTimer();
        setup_fixutres();

        return remoteViews;
    }


    /**
     * Load fixtures from db and show in view
     */
    private void setup_fixutres() {
        if (FixturesResponse.get_results() != null) {
            FixturesResponse fixturesResponse = filterPastDateFromFixture(FixturesResponse.get_results());
            configureFixtureView(fixturesResponse.getResults().get(0));
        }

    }

    private FixturesResponse filterPastDateFromFixture(FixturesResponse fixturesResponse) {
        List<Result> filteredResults = new ArrayList<>();
        for (Result fixture : fixturesResponse.getResults())
            if (!Util.matchDateIsBeforeToday(fixture.getDatetime()))
                filteredResults.add(fixture);


        fixturesResponse.setResults(filteredResults);
        return fixturesResponse;
    }
    /**
     * populate opponent name, date time
     * broadcast tv channel name,
     * start countdown timer
     *
     * @param result
     */
    private void configureFixtureView(Result result) {
        try {

            String opponentName = result.getOpponent().getName();
            Boolean isHomeGame = result.getHomeGame();
            //configure broadcast channel namemContext
            if (result.getBroadcastOn() != null) {
                remoteViews.setTextViewText(R.id.liveTextView, "Live on " + result.getBroadcastOn());
                remoteViews.setViewVisibility(R.id.dateLiveDividerImageView, View.VISIBLE);
            } else {
                remoteViews.setTextViewText(R.id.liveTextView, "");
                remoteViews.setViewVisibility(R.id.dateLiveDividerImageView, View.GONE);
            }


            //configure countdown timer
            configureDateTime_CountDownTimer(result.getDatetime());

            //configure game between team names
            if (isHomeGame)
                remoteViews.setTextViewText(R.id.ftTextView, this.getString(R.string.manchester_united) + " V " + opponentName);
            else
                remoteViews.setTextViewText(R.id.ftTextView, opponentName + " V " + this.getString(R.string.manchester_united));

            //configurevenue
            remoteViews.setTextViewText(R.id.gameVenueTextView, result.getVenue());
//            mAppWidgetManager.updateAppWidget(mAppWidgetIds, remoteViews);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * start countdown timer and
     * format datetime to newformat, shown in view
     *
     * @param dateStr
     */
    private void configureDateTime_CountDownTimer(String dateStr) {
//        dateStr = "2016-09-26T23:45:00Z";
        Log.v("test", "date utc " + dateStr);
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            myFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            //set time for countdown
            Date date = myFormat.parse(dateStr);
            long timeDiff = date.getTime() - new Date().getTime();
            mCountDownTimer.setTime(timeDiff);

            try {
                //format new date format
                SimpleDateFormat targetDateFormat = new SimpleDateFormat("kk:mm EEE, dd MMM");
                String newDateFormat = targetDateFormat.format(date);
                remoteViews.setTextViewText(R.id.dateTimeValueTextView,/* displayValue + "\n" + */newDateFormat);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * init the countdown timer parameter and set listeners
     */
    private void initializeCountDownTimer() {
        mCountDownTimer = new CountDownTimer();
        mCountDownTimer.setOnTimerListener(new CountDownTimer.TimerListener() {
            @Override
            public void onTick(long millisUntilFinished, CountDownTime countDownTime) {
                remoteViews.setTextViewText(R.id.daysValueTextView, Util.getTwoDigitNumber(countDownTime.getDays()));
                remoteViews.setTextViewText(R.id.hoursValueTextView, Util.getTwoDigitNumber(countDownTime.getHours()));
                remoteViews.setTextViewText(R.id.minsValueTextView, Util.getTwoDigitNumber(countDownTime.getMinutes()));
                remoteViews.setTextViewText(R.id.secsValueTextView, Util.getTwoDigitNumber(countDownTime.getSeconds()));
                manager.updateAppWidget(mAppWidgetIds,remoteViews);

                setup_time_label(countDownTime);

                Log.v("time in service ", "current sec" + countDownTime.getSeconds());
            }

            @Override
            public void onFinish() {
                setup_fixutres();
            }
        });
    }

    private void setup_time_label(CountDownTime time) {
        if (time.getDays() == 1)
            remoteViews.setTextViewText(R.id.daysLabelNameTextView, "day");
        else
            remoteViews.setTextViewText(R.id.daysLabelNameTextView, "days");

        if (time.getHours() == 1)
            remoteViews.setTextViewText(R.id.hoursLabelNameTextView, "hour");
        else
            remoteViews.setTextViewText(R.id.hoursLabelNameTextView, "hours");

        if (time.getMinutes() == 1)
            remoteViews.setTextViewText(R.id.minsLabelNameTextView, "min");
        else
            remoteViews.setTextViewText(R.id.minsLabelNameTextView, "mins");

        if (time.getSeconds() == 1)
            remoteViews.setTextViewText(R.id.secsLabelNameTextView, "sec");
        else
            remoteViews.setTextViewText(R.id.secsLabelNameTextView, "secs");
    }

}