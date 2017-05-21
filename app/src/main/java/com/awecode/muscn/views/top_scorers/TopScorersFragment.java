package com.awecode.muscn.views.top_scorers;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.TopScorerAdapter;
import com.awecode.muscn.model.http.top_scorers.TopScorersResponse;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.MasterFragment;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by surensth on 9/28/16.
 */

public class TopScorersFragment extends MasterFragment {
    @BindView(R.id.topScorersRecyclerView)
    RecyclerView mRecyclerView;

    TopScorerAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerViewScrollListener mRecyclerViewScrollListener;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RealmAsyncTask mTransaction;

    public static TopScorersFragment newInstance() {
        TopScorersFragment fragment = new TopScorersFragment();
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_top_scorers;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerViewScrollListener = (RecyclerViewScrollListener) this.getContext();

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity.setCustomTitle(R.string.top_scorers);
        initializeRecyclerView();

        checkInternetConnection();

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        checkInternetConnection();
                    }
                }
        );

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
            requestTopScorers();
        else {
            if (mSwipeRefreshLayout.isRefreshing())
                mSwipeRefreshLayout.setRefreshing(false);

            if (getTableDataCount(TopScorersResponse.class) < 1)
                noInternetConnectionDialog();
            else
                setUpAdapter();

        }
    }


    /**
     * fetch top scorer data from api
     */
    public void requestTopScorers() {
        if (getTableDataCount(TopScorersResponse.class) > 0)
            setUpAdapter();
        else
            showProgressView(getString(R.string.loading_top_scorers));


        Observable<List<TopScorersResponse>> call = mApiInterface.getTopScorers();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TopScorersResponse>>() {
                    @Override
                    public void onCompleted() {
                        if (mSwipeRefreshLayout.isRefreshing())
                            mSwipeRefreshLayout.setRefreshing(false);
                        mActivity.showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.noInternetConnectionDialog(mContext);
                    }

                    @Override
                    public void onNext(List<TopScorersResponse> topScorersResponses) {
                        deleteDataFromDBAndSave(topScorersResponses);
                    }
                });
    }

    /**
     * first delete the existing  data from db
     */
    private void deleteDataFromDBAndSave(final List<TopScorersResponse> responseList) {
        try {
            //delete fixture results first
            mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(TopScorersResponse.class);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    //now save fixture data
                    saveResponseData(responseList);
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
     * @param responseList
     * @return
     */
    private void saveResponseData(final List<TopScorersResponse> responseList) {

        Collection<TopScorersResponse> realmTableList = null;
        if (responseList != null && responseList.size() > 0) {
            mRealm.beginTransaction();
            realmTableList = mRealm.copyToRealm(responseList);
            mRealm.commitTransaction();
        }
    }


    /**
     * populate saved db data in recyclerview
     */
    private void setUpAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            return;
        }
        mAdapter = new TopScorerAdapter(mRealm.where(TopScorersResponse.class).findAll(), true);
        mRecyclerView.setAdapter(Util.getAnimationAdapter(mAdapter));
        mRecyclerViewScrollListener.onRecyclerViewScrolled(mRecyclerView);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTransaction != null && !mTransaction.isCancelled())
            mTransaction.cancel();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
