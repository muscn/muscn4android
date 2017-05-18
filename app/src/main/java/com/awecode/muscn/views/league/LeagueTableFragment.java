package com.awecode.muscn.views.league;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.LeagueTableAdapter;
import com.awecode.muscn.model.http.leaguetable.LeagueTableResponse;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.views.HomeActivity;
import com.awecode.muscn.views.MasterFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by surensth on 9/25/16.
 */

public class LeagueTableFragment extends MasterFragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    LeagueTableAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerViewScrollListener recyclerViewScrollListener;

    public static LeagueTableFragment newInstance() {
        LeagueTableFragment fragment = new LeagueTableFragment();
        return fragment;
    }

    public LeagueTableFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerViewScrollListener = (RecyclerViewScrollListener) this.getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_league_table, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mActivity.setCustomTitle(R.string.league_table);
        if (Util.checkInternetConnection(mContext))
            requestLeagueTable();
        else {
            ((HomeActivity) mContext).noInternetConnectionDialog(mContext);
        }
    }

    /**
     * fetch
     */
    public void requestLeagueTable() {
        if (mRealm.where(LeagueTableResponse.class).count() < 1)
            showProgressView(getString(R.string.loading_league_table));
        MuscnApiInterface mApiInterface = getApiInterface();
        Observable<List<LeagueTableResponse>> call = mApiInterface.getLeague();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<LeagueTableResponse>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.noInternetConnectionDialog(mContext);
                    }

                    @Override
                    public void onNext(List<LeagueTableResponse> leagueTableResponses) {
                        setUpAdapter(handleLeagueTableResponse(leagueTableResponses));
                    }
                });
    }

    /**
     * save data in db and return the arraylist for recyclerview adapter
     * @param leagueTableResponses
     * @return
     */
    private List<LeagueTableResponse> handleLeagueTableResponse(final List<LeagueTableResponse> leagueTableResponses) {

        Collection<LeagueTableResponse> realmTableList = null;
        if (leagueTableResponses != null && leagueTableResponses.size() > 0) {
            mRealm.beginTransaction();
            realmTableList = mRealm.copyToRealm(leagueTableResponses);
            mRealm.commitTransaction();
        }
        return new ArrayList<LeagueTableResponse>(realmTableList);

    }

    private void setUpAdapter(List<LeagueTableResponse> leagueTableResponses) {
        mAdapter = new LeagueTableAdapter(mContext, leagueTableResponses);
        mRecyclerView.setAdapter(Util.getAnimationAdapter(mAdapter));
        recyclerViewScrollListener.onRecyclerViewScrolled(mRecyclerView);
    }
}
