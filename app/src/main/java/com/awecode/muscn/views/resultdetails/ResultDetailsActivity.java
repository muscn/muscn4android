package com.awecode.muscn.views.resultdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.resultdetails.ResultDetailsResponse;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.awecode.muscn.views.BaseActivity;
import com.awecode.muscn.views.recent_results.ResultDetailsFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ResultDetailsActivity extends BaseActivity {
    @BindView(R.id.firstTeamImageView)
    ImageView firstTeamImageView;
    @BindView(R.id.firstTeamNameTextView)
    TextView firstTeamNameTextView;
    @BindView(R.id.firstTeamScoreTextView)
    TextView firstTeamScoreTextView;
    @BindView(R.id.secondTeamImageView)
    ImageView secondTeamImageView;
    @BindView(R.id.secondTeamNameTextView)
    TextView secondTeamNameTextView;
    @BindView(R.id.secondTeamScoreTextView)
    TextView secondTeamScoreTextView;
    @BindView(R.id.toolbarFirstTeamImageView)
    ImageView toolbarFirstTeamImageView;
    @BindView(R.id.toolbarFirstTeamScore)
    TextView toolbarFirstTeamScore;
    @BindView(R.id.toolbarSecondTeamScore)
    TextView toolbarSecondTeamScore;
    @BindView(R.id.toolbarSecondTeamImageView)
    ImageView toolbarSecondTeamImageView;
    @BindView(R.id.toolbar_layout)
    LinearLayout toolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.backArrowImageView)
    ImageView backArrowImageView;

    private int resultId;
    public ResultDetailsResponse mResponse;
    private RealmAsyncTask mTransaction;


    private static final String TAG = ResultDetailsActivity.class.getSimpleName().toString();

    @Override
    public void onErrorViewClicked() {
        if (Util.checkInternetConnection(mContext)) {
            requestResultDetails();
        } else {
            noInternetConnectionDialog(mContext);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        collapsingToolbarSetUp();

        Intent intent = getIntent();
        resultId = intent.getIntExtra(Constants.ID, 0);

        checkInternetConnection();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_result_details;
    }


    private void requestResultDetails() {
        int count = (int) mRealm.where(ResultDetailsResponse.class)
                .equalTo("id", resultId).count();

        if (count < 1)
            showProgressView(getString(R.string.loading_results));
        else
            getDataFromDbAndPopulateUI();

        mApiInterface = ServiceGenerator.createService(MuscnApiInterface.class);
        Observable<ResultDetailsResponse> call = mApiInterface.getResultDetails(resultId);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultDetailsResponse>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        noInternetConnectionDialog(mContext);
                    }

                    @Override
                    public void onNext(ResultDetailsResponse response) {
                        Log.v(TAG, "response string is" + new Gson().toJson(response).toString());
                        mResponse = response;
                        if (response != null)
                            deleteDataFromDBAndSave(response);
                    }
                });
    }

    private void populateUI(ResultDetailsResponse response) {
        if (response.getIsHomeGame()) {
            firstTeamNameTextView.setText(getString(R.string.manchester_united));
            firstTeamScoreTextView.setText(response.getMufcScore().toString());
            toolbarFirstTeamScore.setText(response.getMufcScore().toString());
            Picasso.with(mContext).load(R.drawable.logo_manutd).into(firstTeamImageView);
            Picasso.with(mContext).load(R.drawable.logo_manutd).into(toolbarFirstTeamImageView);

            secondTeamNameTextView.setText(response.getOpponentName());
            secondTeamScoreTextView.setText(response.getOpponentScore().toString());
            toolbarSecondTeamScore.setText(response.getOpponentScore().toString());
            Picasso.with(mContext).load("http://manutd.org.np/" + response.getOpponentCrest()).into(secondTeamImageView);
            Picasso.with(mContext).load("http://manutd.org.np/" + response.getOpponentCrest()).into(toolbarSecondTeamImageView);
//            setUpData(response);

        } else {
            secondTeamNameTextView.setText(getString(R.string.manchester_united));
            secondTeamScoreTextView.setText(response.getMufcScore().toString());
            toolbarSecondTeamScore.setText(response.getMufcScore().toString());
            Picasso.with(mContext).load(R.drawable.logo_manutd).into(secondTeamImageView);
            Picasso.with(mContext).load(R.drawable.logo_manutd).into(toolbarSecondTeamImageView);

            firstTeamNameTextView.setText(response.getOpponentName());
            firstTeamScoreTextView.setText(response.getOpponentScore().toString());
            toolbarFirstTeamScore.setText(response.getOpponentScore().toString());
            Picasso.with(mContext).load("http://manutd.org.np/" + response.getOpponentCrest()).into(firstTeamImageView);
            Picasso.with(mContext).load("http://manutd.org.np/" + response.getOpponentCrest()).into(toolbarFirstTeamImageView);
        }
        openFragmentNoHistoryResult(ResultDetailsFragment.newInstance(response));
    }

    private void openFragmentNoHistoryResult(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.containerNew,
                fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commitAllowingStateLoss();
    }

    private void collapsingToolbarSetUp() {
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                toolbarLayout.setAlpha(Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange()));

//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    toolbarLayout.setVisibility(View.VISIBLE);
////                    appBarLayout.setExpanded(false);
//                    isShow = true;
//                } else if (isShow) {
//                    toolbarLayout.setVisibility(View.GONE);
//
//                    isShow = false;
//                }
            }
        });
    }

    /**
     * check internet, initialize request
     * if no internet, show message incase of empty db,
     * show saved data incase of data in db
     */
    private void checkInternetConnection() {
        if (Util.checkInternetConnection(mContext))
            requestResultDetails();
        else {
            int count = (int) mRealm.where(ResultDetailsResponse.class)
                    .equalTo("id", resultId).count();

            if (count < 1)
                noInternetConnectionDialog(mContext);
            else
                getDataFromDbAndPopulateUI();
        }
    }

    /**
     * if response exist in db then get and populate ui
     */
    private void getDataFromDbAndPopulateUI() {
        ResultDetailsResponse resultDetailsResponse = mRealm.where(ResultDetailsResponse.class).equalTo("id", resultId).findFirst();
        if (resultDetailsResponse != null)
            populateUI(resultDetailsResponse);
    }

    /**
     * first delete the existing  data from db
     */
    private void deleteDataFromDBAndSave(final ResultDetailsResponse response) {

        try {
            //delete fixture results first
            mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    ResultDetailsResponse resultDetailsResponse = realm.where(ResultDetailsResponse.class).equalTo("id", resultId).findFirst();
                    if (resultDetailsResponse != null)
                        resultDetailsResponse.deleteFromRealm();
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    //now save fixture data
                    saveResponseData(response);
                    getDataFromDbAndPopulateUI();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * save data in db
     *
     * @param response
     * @return
     */
    private void saveResponseData(ResultDetailsResponse response) {

        if (response != null) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(response);
            mRealm.commitTransaction();
        }
    }

    @OnClick(R.id.backArrowImageView)
    public void onArrowClick() {
        finish();
    }

    @Override
    public void onBackPressed() {
        onArrowClick();
    }
}
