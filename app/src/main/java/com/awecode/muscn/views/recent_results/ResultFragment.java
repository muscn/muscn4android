package com.awecode.muscn.views.recent_results;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.ResultAdapter;
import com.awecode.muscn.model.http.recent_results.RecentResultData;
import com.awecode.muscn.model.http.recent_results.RecentResultsResponse;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.model.listener.ResultItemClickListener;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.MasterFragment;
import com.awecode.muscn.views.resultdetails.ResultDetailsActivity;

import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.Sort;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by suresh on 9/26/16.
 */

public class ResultFragment extends MasterFragment implements ResultItemClickListener {
    @BindView(R.id.matchResultRecyclerview)
    RecyclerView mMatchResultRecyclerview;
    private ResultAdapter mResultAdapter;
    public RecyclerViewScrollListener recyclerViewScrollListener;
    private RealmAsyncTask mTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerViewScrollListener = (RecyclerViewScrollListener) this.getContext();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_match_result;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setCustomTitle(R.string.recent_results);
        initializeRecyclerView();
        checkInternetConnection();
    }

    private void initializeRecyclerView() {
        mMatchResultRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMatchResultRecyclerview.setHasFixedSize(true);
    }

    /**
     * check internet, initialize request
     * if no internet, show message incase of empty db,
     * show saved data incase of data in db
     */
    private void checkInternetConnection() {
        if (Util.checkInternetConnection(mContext))
            requestMatchResults();
        else {
            if (getTableDataCount(RecentResultData.class) < 1)
                noInternetConnectionDialog();
            else
                setUpAdapter();

        }
    }


    /**
     * populate match result, opponent name and match score
     */
    public void setUpAdapter() {
        if (mResultAdapter != null) {
            mResultAdapter.notifyDataSetChanged();
            return;
        }

        mResultAdapter = new ResultAdapter(mRealm.where(RecentResultData.class).findAllSorted("datetime", Sort.DESCENDING));
        mMatchResultRecyclerview.setAdapter(mResultAdapter);
        recyclerViewScrollListener.onRecyclerViewScrolled(mMatchResultRecyclerview);
        mResultAdapter.mResultItemClickListener = this;

    }


    /**
     * fetch manutd recent match results list
     */
    public void requestMatchResults() {
        if (getTableDataCount(RecentResultData.class) < 1)
            showProgressView(getString(R.string.loading_recent_results));
        else
            setUpAdapter();

        Observable<RecentResultsResponse> call = mApiInterface.getResults();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecentResultsResponse>() {
                    @Override
                    public void onCompleted() {
                        mActivity.showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.noInternetConnectionDialog(mContext);
                    }

                    @Override
                    public void onNext(RecentResultsResponse fixturesResponse) {
                        if (fixturesResponse != null
                                && fixturesResponse.getRecentResultDatas() != null)
                            deleteDataFromDBAndSave(fixturesResponse.getRecentResultDatas());
                    }
                });
    }

    /**
     * first delete the existing  data from db
     */
    private void deleteDataFromDBAndSave(final List<RecentResultData> responseList) {
        try {
            //delete fixture results first
            mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(RecentResultData.class);
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
    private void saveResponseData(final List<RecentResultData> responseList) {

        if (responseList != null && responseList.size() > 0) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(responseList);
            mRealm.commitTransaction();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onRecentResultClicked(int resultId) {
        Intent intent = new Intent(mContext, ResultDetailsActivity.class);
        intent.putExtra(Constants.ID, resultId);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTransaction != null && !mTransaction.isCancelled())
            mTransaction.cancel();

    }

}
