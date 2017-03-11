package com.awecode.muscn.views.recentresults;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.resultdetails.ResultDetailsResponse;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.awecode.muscn.views.HomeActivity;
import com.awecode.muscn.views.MasterFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by surensth on 3/9/17.
 */

public class ResultDetailsFragment extends MasterFragment {

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
    private int resultId = 0;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.toolbar_layout)
    LinearLayout nToolbarLayout;

    public static ResultDetailsFragment newInstance(int resultId) {
        ResultDetailsFragment fragment = new ResultDetailsFragment();
        fragment.setData(resultId);
        return fragment;
    }

    public ResultDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    nToolbarLayout.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    nToolbarLayout.setVisibility(View.GONE);

//                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });


//        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//                nToolbarLayout.setAlpha(Math.abs(verticalOffset / (float)
//                        appBarLayout.getTotalScrollRange()));
//            }
//        });

        if (Util.checkInternetConnection(mContext)){
//            if (resultId != 0)
                requestResultDetails();
//            else {
//                //no detail
            }
        else {
            ((HomeActivity) mContext).noInternetConnectionDialog(mContext);
        }
    }

    private void requestResultDetails() {
        showProgressView(getString(R.string.loading_fixtures));
        mApiInterface = ServiceGenerator.createService(MuscnApiInterface.class);
        Observable<ResultDetailsResponse> call = mApiInterface.getResultDetails(resultId);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultDetailsResponse>() {
                    @Override
                    public void onCompleted() {
                        mActivity.showContentView();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("tei","id sss"+e.getMessage());
                        Log.v("tei","id sss"+e.getStackTrace());

                        mActivity.noInternetConnectionDialog(mContext);

                    }

                    @Override
                    public void onNext(ResultDetailsResponse response) {
                        Log.v("tei","id sss"+new Gson().toJson(response).toString());


                        populateUI(response);
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


        }
        else{
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
    }


    private void setData(int resultId) {
        this.resultId = resultId;
    }

}
