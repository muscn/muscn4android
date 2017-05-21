package com.awecode.muscn.views.league;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.RealmLeagueTableAdapter;
import com.awecode.muscn.model.http.leaguetable.LeagueTableResponse;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.MasterFragment;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
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

    RealmLeagueTableAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerViewScrollListener recyclerViewScrollListener;
    private RealmAsyncTask mTransaction;

    public static LeagueTableFragment newInstance() {
        LeagueTableFragment fragment = new LeagueTableFragment();
        return fragment;
    }

    public LeagueTableFragment() {
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_league_table;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerViewScrollListener = (RecyclerViewScrollListener) this.getContext();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity.setCustomTitle(R.string.league_table);
        initializeRecyclerView();

        checkInternetConnection();
    }

    private void initializeRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    /**
     * check internet, initialize request
     * if no internet, show message incase of empty db,
     * show saved data incase of data in db
     */
    private void checkInternetConnection() {
        if (Util.checkInternetConnection(mContext))
            requestLeagueTable();
        else {
            if (getTableDataCount(LeagueTableResponse.class) < 1)
                noInternetConnectionDialog();
            else
                setUpAdapter();

        }
    }


    /**
     * request for league table data
     */
    public void requestLeagueTable() {
        if (mRealm.where(LeagueTableResponse.class).count() < 1)
            showProgressView(getString(R.string.loading_league_table));
        else
            setUpAdapter();
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
                        deleteLeagueTableAndSave(leagueTableResponses);
                    }
                });
    }

    /**
     * first delete the existing  league data from db
     *
     * @param leagueTableResponses
     */
    private void deleteLeagueTableAndSave(final List<LeagueTableResponse> leagueTableResponses) {
        try {
            //delete fixture results first
            mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(LeagueTableResponse.class);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    //now save fixture data
                    saveLeagueTableData(leagueTableResponses);
                    setUpAdapter();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * save data in db and return the arraylist for recyclerview adapter
     *
     * @param leagueTableResponses
     * @return
     */
    private void saveLeagueTableData(final List<LeagueTableResponse> leagueTableResponses) {

        Collection<LeagueTableResponse> realmTableList = null;
        if (leagueTableResponses != null && leagueTableResponses.size() > 0) {
            mRealm.beginTransaction();
            realmTableList = mRealm.copyToRealm(leagueTableResponses);
            mRealm.commitTransaction();
        }
    }


    /**
     * populate leaguetable list in db
     */
    private void setUpAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            return;
        }
        mAdapter = new RealmLeagueTableAdapter(mRealm.where(LeagueTableResponse.class).findAll());
        mRecyclerView.setAdapter(Util.getAnimationAdapter(mAdapter));
        recyclerViewScrollListener.onRecyclerViewScrolled(mRecyclerView);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTransaction != null && !mTransaction.isCancelled())
            mTransaction.cancel();

    }

}
