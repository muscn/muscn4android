package com.awecode.muscn.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.esewa.android.sdk.payment.ESewaPaymentActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

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
    @BindView(R.id.dateTextView)
    TextView mDateTextView;
    @BindView(R.id.firstTeamImageView)
    ImageView mFirstTeamImageView;
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
    @BindView(R.id.main_layout)
    LinearLayout mainLayout;
    @BindView(R.id.emptyFixtureLayout)
    LinearLayout emptyFixtureLayout;

    @BindView(R.id.timeTextView)
    TextView mTimeTextView;
    @BindView(R.id.dayTextView)
    TextView mDayTextView;
    @BindView(R.id.opponentTeamImageView)
    ImageView mOpponentTeamImageView;

    @BindView(R.id.liveScreeningImageView)
    ImageView mLiveScreeningImageView;
    @BindView(R.id.liveScreeningLayout)
    LinearLayout mLiveScreeningLayout;

    private CountDownTimer mCountDownTimer;
    private RealmAsyncTask mTransaction;
    String mLocation;
    String mLocationName;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {

    }

    @Override
    public int getLayout() {
//        return R.layout.fragment_home;
        return R.layout.fragment_home_new;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity.setCustomTitle(R.string.muscn);
        initializeCountDownTimer();
        setup_fixutres();
        if (Util.checkInternetConnection(mContext))
            requestFixturesList();
    }

    @OnClick(R.id.esewaButton)
    public void esweaButtonClicked(View view) {
        //check internet connection
        if (!Util.checkInternetConnection(mContext)) {
            noInternetConnectionDialog();
            return;
        }

        //start esewa payment
        startEsewaPayment();
    }

    private ESewaConfiguration mEsewConfiguration;
    private static final int REQUEST_CODE_PAYMENT = 112;

    /**
     * start esewa payment
     */
    private void startEsewaPayment() {
        //config esewa with client id and secret key first
        setupEsewaConfig();

        ESewaPayment eSewaPayment = new ESewaPayment("100", "Membership Registration", "MEMB-01", "");
        Intent intent = new Intent(mContext, ESewaPaymentActivity.class);
        intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION, mEsewConfiguration);
        intent.putExtra(ESewaPayment.ESEWA_PAYMENT, eSewaPayment);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            //payment success
            if (resultCode == RESULT_OK) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Log.i("Proof   of   Payment", s);
                toast("SUCCESSFUL   PAYMENT");
                //payment cancel by user
            } else if (resultCode == RESULT_CANCELED) {
                toast("It seems you cancelled the payment.");
                //invalid parameter passed to esewa sdk
            } else if (resultCode == ESewaPayment.RESULT_EXTRAS_INVALID) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Log.i("Proof   of   Payment", s);
            }
        }
    }


    /**
     * Config esewa with client ID and secret key
     */
    private void setupEsewaConfig() {
        if (mEsewConfiguration != null)
            return;
        mEsewConfiguration = new ESewaConfiguration().clientId(Constants.ESEWA_CLIENT_ID)
                .secretKey(Constants.ESEWA_SECRET_KEY)
                .environment(ESewaConfiguration.ENVIRONMENT_TEST);
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
            } else {
                showEmptyLayout();
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
            if (getTableDataCount(Result.class) < 1)
                showProgressView("Loading data...");
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

                            if (fixturesResponse.getResults().size() > 0) {
                                deleteFixturesAndSave(fixturesResponse);
                            } else
                                showEmptyLayout();
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
                    if (results != null && results.size() > 0) {
                        configureFixtureView();
                    } else
                        showEmptyLayout();

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
        Collection<Result> realmFixtures = null;
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
                List<Result> arrangedResults = filterPastDateFromFixture(results);

                if (arrangedResults.size() != 0) {
                    showMainLayout();//if fixture is present then mainlayout is shown

                    Result result = arrangedResults.get(0);

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
                    } else {
                        mFirstTeamNameTextView.setText(opponentName);
                        mSecondTeamNameTextView.setText(mContext.getString(R.string.manchester_united));
                    }
                    //populate imageview
                    Picasso.with(mContext)
                            .load(result.getOpponent().getCrest())
                            .placeholder(R.drawable.ic_placeholder_team)
                            .resize(0, getDimen(R.dimen.team_logo_size_new))
                            .into(mOpponentTeamImageView);
                    //configure  name and venue
                    mCompetitionNameTextView.setText(result.getCompetitionYear().getCompetition().getName());
                    mCompetitionVenueTextView.setText(result.getVenue());
                    if (result.getLiveScreening() != null) {
                        mLiveScreeningLayout.setVisibility(View.VISIBLE);
                        Picasso.with(mContext).load(result.getLiveScreening().getLogo()).into(mLiveScreeningImageView);
                        mLocation = result.getLiveScreening().getLocation();
                        mLocationName = result.getLiveScreening().getName();
                    } else
                        mLiveScreeningLayout.setVisibility(View.GONE);
                } else
                    showEmptyLayout();
            } else
                showEmptyLayout();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * main layout is shown and empty layout is hide if response is presence
     */
    private void showMainLayout() {
        mainLayout.setVisibility(View.VISIBLE);
        emptyFixtureLayout.setVisibility(View.GONE);
    }

    /**
     * main layout is hidden and empty layout is shown if response is nt presence but request is success
     */
    private void showEmptyLayout() {
        mainLayout.setVisibility(View.GONE);
        emptyFixtureLayout.setVisibility(View.VISIBLE);
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
//format day
                SimpleDateFormat targetDayFormat = new SimpleDateFormat("dd");
                String dayValue = targetDayFormat.format(date);

                //format new date format
                SimpleDateFormat targetDateFormat = new SimpleDateFormat("MMMM, EEEE");
                String newDateFormat = targetDateFormat.format(date);

                //format for hr min
                SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
                String displayValue = timeFormatter.format(date);

                mDayTextView.setText(dayValue);
                mDateTextView.setText(newDateFormat);
                mTimeTextView.setText(displayValue + " NPT");

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

    @OnClick(R.id.liveScreeningLayout)
    public void onClick() {
        try {
            if (mLocation != null) {
                String[] latlng = mLocation.split(",");
                Double dblLat = Double.parseDouble(latlng[0].trim());
                Double dblLng = Double.parseDouble(latlng[1].trim());
                String markerLabel;
                if (mLocationName != null)
                    markerLabel = mLocationName;
                else
                    markerLabel = "";

                openMap(dblLat, dblLng, markerLabel);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openMap(Double lat, Double lng, String mTitle) {
        String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + mTitle + ")";
        Util.openUrl(mContext, geoUri);
    }
}
