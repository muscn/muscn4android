//package com.awecode.muscn.views.matchweekfixtures;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.awecode.muscn.R;
//import com.awecode.muscn.model.http.eplmatchweekdetails.EplMatchWeekFixtureDetailResponse;
//import com.awecode.muscn.util.Util;
//import com.awecode.muscn.util.retrofit.MuscnApiInterface;
//import com.awecode.muscn.util.retrofit.ServiceGenerator;
//import com.awecode.muscn.views.HomeActivity;
//import com.awecode.muscn.views.MasterFragment;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import rx.Observable;
//import rx.Observer;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
///**
// * Created by surensth on 3/8/17.
// */
//
//public class EplMatchWeekFixtureDetailsFragment extends MasterFragment {
//    @BindView(R.id.teamsTextView)
//    TextView teamsTextView;
//    @BindView(R.id.leagueNameTextView)
//    TextView leagueNameTextView;
//    @BindView(R.id.venueTextView)
//    TextView venueTextView;
//    @BindView(R.id.dateTextView)
//    TextView dateTextView;
//    @BindView(R.id.kickOffTextView)
//    TextView kickOffTextView;
//    private String fixtureId;
//
//    public EplMatchWeekFixtureDetailsFragment() {
//    }
//
//    public static EplMatchWeekFixtureDetailsFragment newInstance(String fixtureId) {
//        EplMatchWeekFixtureDetailsFragment fragment = new EplMatchWeekFixtureDetailsFragment();
//        fragment.setData(fixtureId);
//        return fragment;
//    }
//
//    private void setData(String fixtureId) {
//        this.fixtureId = fixtureId;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_epl_matchweek_fixture_detail, container, false);
//        ButterKnife.bind(this, view);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if (Util.checkInternetConnection(mContext))
//            if (fixtureId != null)
//                requestMatchWeekDetails();
//            else {
//                //no detail
//            }
//        else {
//            ((HomeActivity) mContext).noInternetConnectionDialog(mContext);
//        }
//    }
//
//    private void requestMatchWeekDetails() {
//        showProgressView(getString(R.string.loading_fixtures));
//        mApiInterface = ServiceGenerator.createService(MuscnApiInterface.class);
//        Observable<EplMatchWeekFixtureDetailResponse> call = mApiInterface.getEplMatchweekFixtureDetails(fixtureId);
//        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<EplMatchWeekFixtureDetailResponse>() {
//                    @Override
//                    public void onCompleted() {
//                        mActivity.showContentView();
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mActivity.noInternetConnectionDialog(mContext);
//
//                    }
//
//                    @Override
//                    public void onNext(EplMatchWeekFixtureDetailResponse response) {
//                        populateUI(response);
//                    }
//                });
//    }
//
//    private void populateUI(EplMatchWeekFixtureDetailResponse response) {
//        teamsTextView.setText(response.getOpponent().getName() + " vs " + "Manchester United");
//        leagueNameTextView.setText("League : " + response.getCompetitionYear().getCompetition().getName());
//        venueTextView.setText("Venue : " + response.getVenue());
//        dateTextView.setText("Date : " + Util.commonDateFormatter(response.getDatetime(), "yyyy-MM-dd'T'hh:mm:ss'Z'"));
////        kickOffTextView.setText();
//    }
//}
