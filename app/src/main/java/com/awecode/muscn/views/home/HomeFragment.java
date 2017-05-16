package com.awecode.muscn.views.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.CountDownTime;
import com.awecode.muscn.model.http.fixtures.Competition;
import com.awecode.muscn.model.http.fixtures.CompetitionYear;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.http.fixtures.Opponent;
import com.awecode.muscn.model.http.fixtures.Result;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.countdown_timer.CountDownTimer;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.awecode.muscn.views.MasterFragment;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by surensth on 9/23/16.
 */

public class
HomeFragment extends MasterFragment {
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
    private RealmAsyncTask mTransaction;
    private Collection<Result> realmFixtures = null;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {

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
            RealmResults<Result> results = mRealm.where(Result.class).findAll();
            if (results != null
                    && results.size() > 0) {
                mActivity.showContentView();
                //convert realms results into arraylist and show first index data in view
                configureFixtureView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * fetch manutd upcoming fixture list
     */
    public void requestFixturesList() {
        try {
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
                            //delete previous saved data and save new fixtures
                            deleteFixturesAndSave(fixturesResponse);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * //configureFixtureView(fixturesResponse.getResults().get(0));
     * <p>
     * First delete current existing fixture data
     * then save new data in realm table
     */
    private void deleteFixturesAndSave(final FixturesResponse fixturesResponse) {
        try {
            //delete fixture results first
            mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(Result.class);
                    realm.delete(Opponent.class);
                    realm.delete(CompetitionYear.class);
                    realm.delete(Competition.class);

                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    //now save fixture data
                    List<Result> results = saveFixtures(fixturesResponse);
                    if (results != null && results.size() > 0)
                        configureFixtureView();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete Result, competition, competition year, opponent
     */




    /**
     * save all fixtures
     *
     * @param fixturesResponse
     */
    private List<Result> saveFixtures(final FixturesResponse fixturesResponse) {
        List<Result> results = fixturesResponse.getResults();
        if (results != null && results.size() > 0) {
            List<Result> arrangedResults = filterPastDateFromFixture(results);
            mRealm.beginTransaction();
            realmFixtures = mRealm.copyToRealm(arrangedResults);
            mRealm.commitTransaction();
        }
        return new ArrayList<Result>(realmFixtures);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTransaction != null && !mTransaction.isCancelled())
            mTransaction.cancel();

    }

    private List<Result> filterPastDateFromFixture(List<Result> results) {
        List<Result> filteredResults = new ArrayList<>();
        for (Result fixture : results)
            if (!Util.matchDateIsBeforeToday(fixture.getDatetime()))
                filteredResults.add(fixture);

        return filteredResults;
    }

    /**
     * populate opponent name, date time
     * broadcast tv channel name,
     * start countdown timer
     */
    private void configureFixtureView() {

        try {
            List<Result> results = deletePastFixtureTable();
            if (results != null
                    && results.size() > 0) {
                Result result = results.get(0);

                mActivity.setCustomTitle(R.string.app_name);

                String opponentName = result.getOpponent().getName();
                Boolean isHomeGame = result.getIsHomeGame();
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
                    mSecondTeamNameTextView.setText(mContext.getString(R.string.manchester_united));
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
            }

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
                setup_fixutres();
            }
        });
    }


    private void setup_time_label(CountDownTime time) {
        if (time.getDays() == 1)
            mDaysLabelTextView.setText("day");
        else
            mDaysLabelTextView.setText("days");

        if (time.getHours() == 1)
            mHoursLabelTextView.setText("hour");
        else
            mHoursLabelTextView.setText("hours");

        if (time.getMinutes() == 1)
            mMinsLabelTextView.setText("min");
        else
            mMinsLabelTextView.setText("mins");


        if (time.getSeconds() == 1)
            mSecsLabelTextView.setText("sec");
        else
            mSecsLabelTextView.setText("secs");


    }


}
