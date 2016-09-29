package com.awecode.muscn.views.matchweekfixtures;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.MatchResultExpandableRecyclerviewAdapter;
import com.awecode.muscn.model.Item;
import com.awecode.muscn.model.http.eplmatchweek.EplMatchweekFixturesResponse;
import com.awecode.muscn.model.http.recentresults.RecentResultsResponse;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.awecode.muscn.views.MasterFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by suresh on 9/28/16.
 */

public class MatchWeekFixtureFragment extends MasterFragment {

    @BindView(R.id.matchFixtures)
    RecyclerView matchFixtures;

//    private MatchFixutreRecyclerviewAdapter mMatchFixutreRecyclerviewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fixture, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setCustomTitle(R.string.epl_matchweek);
        showProgressView(getString(R.string.loading_fixtures));
//        requestEplMatchResults();
    }

    /**
     * Setup match fixture recyclerview to show the matchweek mixtures
     */
//    public void setupMatchFixtureRecylerView(EplMatchweekFixturesResponse eplMatchweekFixturesResponse){
//        matchFixtures.setHasFixedSize(true);
//        matchFixtures.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mMatchFixutreRecyclerviewAdapter = new MatchFixutreRecyclerviewAdapter(getActivity(),eplMatchweekFixturesResponse);
//        matchFixtures.setAdapter(mMatchFixutreRecyclerviewAdapter);
//
//    }

    /**
     * fetch manutd recent match results list
     */
    public void requestEplMatchResults(){
        mApiInterface = ServiceGenerator.createService(MuscnApiInterface.class);
        Observable<EplMatchweekFixturesResponse> call = mApiInterface.getEplMatchweekFixtures();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EplMatchweekFixturesResponse>() {
                    @Override
                    public void onCompleted() {
                        mActivity.showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.showErrorView(e.getMessage() + ". Try again");
                    }

                    @Override
                    public void onNext(EplMatchweekFixturesResponse eplMatchweekFixturesResponse) {
//                        setupMatchFixtureRecylerView(eplMatchweekFixturesResponse);
                    }
                });
    }

}
