package com.awecode.muscn.views.injuries;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.InjuriesAdapter;
import com.awecode.muscn.model.http.injuries.InjuriesResponse;
import com.awecode.muscn.model.http.injuries.InjuryResult;
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
 * Created by surensth on 9/28/16.
 */

public class InjuriesFragment extends MasterFragment {
    @BindView(R.id.injuriesRecyclerView)
    RecyclerView mRecyclerView;

    InjuriesAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerViewScrollListener mRecyclerViewScrollListener;
    private RealmAsyncTask mTransaction;

    public static InjuriesFragment newInstance() {
        InjuriesFragment fragment = new InjuriesFragment();
        return fragment;
    }

    public InjuriesFragment() {

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_injuries;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerViewScrollListener = (RecyclerViewScrollListener) this.getContext();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity.setCustomTitle(R.string.injuries);
        initializeRecyclerView();
        checkInternetConnection();

    }

    /**
     * check internet, initialize request
     * if no internet, show message incase of empty db,
     * show saved data incase of data in db
     */
    private void checkInternetConnection() {
        if (Util.checkInternetConnection(mContext))
            requestInjuries();
        else {
            if (getTableDataCount(InjuryResult.class) < 1)
                noInternetConnectionDialog();
            else
                setUpAdapter();

        }
    }

    private void initializeRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    /**
     * fetch injury data from api
     */
    public void requestInjuries() {

        if (getTableDataCount(InjuryResult.class) < 1)
            showProgressView(getString(R.string.loading_injuries));
        else
            setUpAdapter();

        Observable<InjuriesResponse> call = mApiInterface.getInjuredPlayers();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InjuriesResponse>() {
                    @Override
                    public void onCompleted() {
                        mActivity.showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.noInternetConnectionDialog(mContext);
                    }

                    @Override
                    public void onNext(InjuriesResponse injuriesResponse) {
                        if (injuriesResponse != null
                                && injuriesResponse.getInjuryResults() != null)
                            deleteDataFromDBAndSave(injuriesResponse.getInjuryResults());
                    }
                });
    }

    /**
     * first delete the existing  db data
     */
    private void deleteDataFromDBAndSave(final List<InjuryResult> injuryResults) {
        try {
            //delete fixture results first
            mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(InjuryResult.class);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    //now save fixture data
                    saveDataInDB(injuryResults);
                    setUpAdapter();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * save data in db
     *
     * @param injuryResults
     */
    private void saveDataInDB(List<InjuryResult> injuryResults) {
        Collection<InjuryResult> realmTableList = null;
        if (injuryResults != null && injuryResults.size() > 0) {
            mRealm.beginTransaction();
            realmTableList = mRealm.copyToRealm(injuryResults);
            mRealm.commitTransaction();
        }
    }

    /**
     * populate data in recyclerview
     */
    private void setUpAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            return;
        }
        mAdapter = new InjuriesAdapter(mRealm.where(InjuryResult.class).findAll());
        mRecyclerView.setAdapter(Util.getAnimationAdapter(mAdapter));
        mRecyclerViewScrollListener.onRecyclerViewScrolled(mRecyclerView);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTransaction != null && !mTransaction.isCancelled())
            mTransaction.cancel();

    }


}
