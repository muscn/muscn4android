package com.awecode.muscn.views.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.CountDownTime;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.http.fixtures.Result;
import com.awecode.muscn.model.listener.FixturesApiListener;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.countdown_timer.CountDownTimer;
import com.awecode.muscn.util.prefs.Prefs;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.awecode.muscn.views.MasterFragment;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by surensth on 9/23/16.
 */

public class HomeFragment extends MasterFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.daysTextView)
    TextView mDaysTextView;
    @BindView(R.id.hoursTextView)
    TextView mHoursTextView;
    @BindView(R.id.minsTextView)
    TextView mMinsTextView;
    @BindView(R.id.secsTextView)
    TextView mSecsTextView;
    @BindView(R.id.firstTeamNameTextView)
    TextView mFirstTeamNameTextView;
    @BindView(R.id.secondTeamNameTextView)
    TextView mSecondTeamNameTextView;
    @BindView(R.id.competitionVenueTextView)
    TextView mCompetitionVenueTextView;
    @BindView(R.id.competitionNameTextView)
    TextView mCompetitionNameTextView;
    @BindView(R.id.broadCastChannelTextView)
    TextView mBroadCastChannelTextView;
    @BindView(R.id.dateTimeTextView)
    TextView mDateTimeTextView;
    @BindView(R.id.firstTeamImageView)
    ImageView mFirstTeamImageView;
    @BindView(R.id.secondTeamImageView)
    ImageView mSecondTeamImageView;
    @BindView(R.id.hoursLabelTextView)
    TextView mHoursLabelTextView;
    @BindView(R.id.minsLabelTextView)
    TextView mMinsLabelTextView;
    @BindView(R.id.secsLabelTextView)
    TextView mSecsLabelTextView;
    @BindView(R.id.daysLabelTextView)
    TextView mDaysLabelTextView;
    @BindView(R.id.liveOnTextView)
    TextView liveOnTextView;


    private CountDownTimer mCountDownTimer;

    public FixturesResponse fixturesResponse;
    public FixturesApiListener fixturesApiListener;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fixturesApiListener = (FixturesApiListener) this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeCountDownTimer();
        setup_fixutres();
        if (Util.checkInternetConnection(mContext))
            requestFixturesList();
    }


    /**
     * Load fixtures from db and show in view
     */
    private void setup_fixutres() {
        try {
            FixturesResponse fixturesResponse = FixturesResponse.get_results();
            if (fixturesResponse != null)
                configureFixtureView(fixturesResponse.getResults().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * fetch manutd upcoming fixture list
     */
    public void requestFixturesList() {
        try {
            if (FixturesResponse.get_results() == null)
                showProgressView(getString(R.string.loading_fixtures));
            Observable<FixturesResponse> call = ServiceGenerator.createService(MuscnApiInterface.class).getFixtures();
            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<FixturesResponse>() {
                        @Override
                        public void onCompleted() {
                            mActivity.showContentView();

                        }

                        @Override
                        public void onError(Throwable e) {
                            mActivity.showContentView();
                        }

                        @Override
                        public void onNext(FixturesResponse fixturesResponse) {
                            mActivity.showContentView();
                            fixturesApiListener.onCallFixtures(fixturesResponse);
                            save_fixtures(fixturesResponse);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save_fixtures(FixturesResponse fixturesResponse) {
        try {
            //first delete the all data from table
            try {
                Prefs.remove(Constants.PREFS_FIXTURES);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                FixturesResponse.save_fixtures(fixturesResponse);
                configureFixtureView(fixturesResponse.getResults().get(0));

            } catch (Exception e) {
                e.printStackTrace();
            }
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
            mActivity.setCustomTitle(R.string.app_name);

            String opponentName = result.getOpponent().getName();
            Boolean isHomeGame = result.getHomeGame();
            //configure broadcast channel name
            configureBroadCastChannelView(result.getBroadcastOn());
            if (TextUtils.isEmpty(result.getBroadcastOn()))
                liveOnTextView.setVisibility(View.GONE);

            //configure countdown timer
            configureDateTime_CountDownTimer(result.getDatetime());

            //configure game between team names
            if (isHomeGame) {
                mFirstTeamNameTextView.setText(getString(R.string.manchester_united));
                mSecondTeamNameTextView.setText(opponentName);
                //populate imageview
                Picasso.with(mContext)
                        .load((String) result.getOpponent().getCrest())
                        .placeholder(R.drawable.ic_placeholder_team)
                        .resize(getDimen(R.dimen.team_logo_size), getDimen(R.dimen.team_logo_size))
                        .into(mSecondTeamImageView);

                Picasso.with(mContext)
                        .load(Constants.URL_MANUTD_LOGO)
                        .placeholder(R.drawable.logo_manutd)
                        .resize(getDimen(R.dimen.team_logo_size), getDimen(R.dimen.team_logo_size))
                        .into(mFirstTeamImageView);
            } else {
                mFirstTeamNameTextView.setText(opponentName);
                mSecondTeamNameTextView.setText(getString(R.string.manchester_united));
                //populate imageview
                Picasso.with(mContext)
                        .load((String) result.getOpponent().getCrest())
                        .placeholder(R.drawable.ic_placeholder_team)
                        .resize(getDimen(R.dimen.team_logo_size), getDimen(R.dimen.team_logo_size))
                        .into(mFirstTeamImageView);

                Picasso.with(mContext)
                        .load(Constants.URL_MANUTD_LOGO)
                        .placeholder(R.drawable.logo_manutd)
                        .resize(getDimen(R.dimen.team_logo_size), getDimen(R.dimen.team_logo_size))
                        .into(mSecondTeamImageView);
            }
            //configure  name and venue
            mCompetitionNameTextView.setText(result.getCompetitionYear().getCompetition().getName());
            mCompetitionVenueTextView.setText(result.getVenue());


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

                mDateTimeTextView.setText(newDateFormat + "\n" + displayValue);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * configure broadcast tv channel name
     *
     * @param broadCastOn
     */
    private void configureBroadCastChannelView(Object broadCastOn) {
        if (broadCastOn != null && !TextUtils.isEmpty(String.valueOf(broadCastOn)))
            mBroadCastChannelTextView.setText(String.valueOf(broadCastOn));
        else
            mBroadCastChannelTextView.setVisibility(View.GONE);
    }

    /**
     * init the countdown timer parameter and set listeners
     */
    private void initializeCountDownTimer() {
        mCountDownTimer = new CountDownTimer();
        mCountDownTimer.setOnTimerListener(new CountDownTimer.TimerListener() {
            @Override
            public void onTick(long millisUntilFinished, CountDownTime countDownTime) {

                mDaysTextView.setText(Util.getTwoDigitNumber(countDownTime.getDays()));
                mHoursTextView.setText(Util.getTwoDigitNumber(countDownTime.getHours()));
                mMinsTextView.setText(Util.getTwoDigitNumber(countDownTime.getMinutes()));
                mSecsTextView.setText(Util.getTwoDigitNumber(countDownTime.getSeconds()));

                setup_time_label(countDownTime);
            }

            @Override
            public void onFinish() {
            }
        });
    }


    private void setup_time_label(CountDownTime time) {
        if (time.getDays() <= 1)
            mDaysLabelTextView.setText("day");
        else
            mDaysLabelTextView.setText("days");

        if (time.getHours() <= 1)
            mHoursLabelTextView.setText("hour");
        else
            mHoursLabelTextView.setText("hours");

        if (time.getMinutes() <= 1)
            mMinsLabelTextView.setText("min");
        else
            mMinsLabelTextView.setText("mins");


        if (time.getSeconds() <= 1)
            mSecsLabelTextView.setText("sec");
        else
            mSecsLabelTextView.setText("secs");


    }


}
