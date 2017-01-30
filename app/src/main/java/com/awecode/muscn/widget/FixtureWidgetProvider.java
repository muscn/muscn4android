package com.awecode.muscn.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import com.awecode.muscn.R;
import com.awecode.muscn.model.CountDownTime;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.http.fixtures.Result;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.countdown_timer.CountDownTimer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by surensth on 1/29/17.
 */

public class FixtureWidgetProvider extends AppWidgetProvider {
    private CountDownTimer mCountDownTimer;
    private RemoteViews remoteViews;
    Context mContext;
    AppWidgetManager mAppWidgetManager;
    int[] mAppWidgetIds;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        mContext = context;
        mAppWidgetManager = appWidgetManager;
        mAppWidgetIds = appWidgetIds;
        remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.fixture_widget);

        initializeCountDownTimer();
        setup_fixutres();

        Intent intent = new Intent(context, FixtureWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 1000, pendingIntent);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.add(Calendar.SECOND, 1);
//        alarmManager.setRepeating(AlarmManager.RTC,calendar.getTimeInMillis(),1000,pendingIntent);



//        remoteViews.
//        remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
//        appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }

    /**
     * Load fixtures from db and show in view
     */
    private void setup_fixutres() {
        try {
            FixturesResponse fixturesResponse = FixturesResponse.get_results();
            Log.v("test", "fixturesResponse " + fixturesResponse.getResults().get(0).getOpponent().getName());

            if (fixturesResponse != null) {
                if (matchDateIsBeforeToday(fixturesResponse.getResults().get(0).getDatetime())) {
                    configureFixtureView(fixturesResponse.getResults().get(1));
                } else {
                    configureFixtureView(fixturesResponse.getResults().get(0));
                }
//                configureFixtureView(fixturesResponse.getResults().get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * compare whether the match date is before todays date or not, if match date is before today then returns true showing data in index 1 else returns false showing data in index 0 for upcoming match
     *
     * @param matchDate date of upcoming match
     * @return
     */
    private Boolean matchDateIsBeforeToday(String matchDate) {

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
            //configure broadcast channel name
            if (result.getBroadcastOn() != null)
                remoteViews.setTextViewText(R.id.liveTextView, "Live on " + result.getBroadcastOn());

            //configure countdown timer
            configureDateTime_CountDownTimer(result.getDatetime());

            //configure game between team names
            if (isHomeGame) {
                remoteViews.setTextViewText(R.id.ftTextView, mContext.getString(R.string.manchester_united));
                remoteViews.setTextViewText(R.id.stNameTextView, opponentName);

            } else {
                remoteViews.setTextViewText(R.id.ftTextView, opponentName);
                remoteViews.setTextViewText(R.id.stNameTextView, mContext.getString(R.string.manchester_united));

            }
            //configurevenue
            remoteViews.setTextViewText(R.id.gameVenueTextView, result.getVenue());
            mAppWidgetManager.updateAppWidget(mAppWidgetIds, remoteViews);

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
                SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd MMMM, EEEE");
                String newDateFormat = targetDateFormat.format(date);

                //format for hr min
                SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
                String displayValue = timeFormatter.format(date);

                remoteViews.setTextViewText(R.id.dateTimeValueTextView, newDateFormat + "\n" + displayValue);
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

//                mDaysTextView.setText(Util.getTwoDigitNumber(countDownTime.getDays()));
//                mHoursTextView.setText(Util.getTwoDigitNumber(countDownTime.getHours()));
//                mMinsTextView.setText(Util.getTwoDigitNumber(countDownTime.getMinutes()));
//                mSecsTextView.setText(Util.getTwoDigitNumber(countDownTime.getSeconds()));

                setup_time_label(countDownTime);

                Log.v("time","second"+countDownTime.getSeconds());
            }

            @Override
            public void onFinish() {
            }
        });
    }

    private void setup_time_label(CountDownTime time) {
        if (time.getDays() <= 1)
            remoteViews.setTextViewText(R.id.daysLabelNameTextView, "day");
        else
            remoteViews.setTextViewText(R.id.daysLabelNameTextView, "days");

        if (time.getHours() <= 1)
            remoteViews.setTextViewText(R.id.hoursLabelNameTextView, "hour");
        else
            remoteViews.setTextViewText(R.id.hoursLabelNameTextView, "hours");

        if (time.getMinutes() <= 1)
            remoteViews.setTextViewText(R.id.minsLabelNameTextView, "min");
        else
            remoteViews.setTextViewText(R.id.minsLabelNameTextView, "mins");

        if (time.getSeconds() <= 1)
            remoteViews.setTextViewText(R.id.secsLabelNameTextView, "sec");
        else
            remoteViews.setTextViewText(R.id.secsLabelNameTextView, "secs");


    }

}
