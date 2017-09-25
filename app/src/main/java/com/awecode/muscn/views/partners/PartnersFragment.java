package com.awecode.muscn.views.partners;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.PartnersAdapter;
import com.awecode.muscn.model.http.partners.PartnersResponse;
import com.awecode.muscn.model.http.partners.PartnersResult;
import com.awecode.muscn.model.listener.PartnerClickListener;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.MasterFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
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
 * Created by surensth on 9/23/17.
 */

public class PartnersFragment extends MasterFragment implements PartnerClickListener {
    private static final String TAG = "PartnersFragment";
    private int dbDataCount;

    @BindView(R.id.partnersRecyclerView)
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    PartnersAdapter mAdapter;
    private RealmAsyncTask mTransaction;

    public static PartnersFragment newInstance() {
        PartnersFragment fragment = new PartnersFragment();
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_partners;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setCustomTitle(R.string.partners);
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
        dbDataCount = getTableDataCount(PartnersResult.class);
        if (dbDataCount > 0)
            setUpAdapter();

        if (Util.checkInternetConnection(mContext))
            requestPartnersList();
        else if (dbDataCount < 1)
            noInternetConnectionDialog();
    }

    /**
     * partners API request
     */

    private void requestPartnersList() {
        showProgressView(getString(R.string.loading_partners));

        Observable<PartnersResponse> call = mApiInterface.getPartnersList();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PartnersResponse>() {
                    @Override
                    public void onCompleted() {
                        mActivity.showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.v(TAG, "Partners errors " + e.getLocalizedMessage());

                    }

                    @Override
                    public void onNext(PartnersResponse partnersResponse) {
                        Log.v(TAG, "Partners res " + new Gson().toJson(partnersResponse));
                        deleteDataFromDBAndSave(partnersResponse);
                    }
                });
    }


    /**
     * first delete the existing  data from db
     */
    private void deleteDataFromDBAndSave(final PartnersResponse partnersResponse) {
        try {
            //delete fixture results first
            mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(PartnersResult.class);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    //now save fixture data
                    savePartnersData(partnersResponse);
                    setUpAdapter();

                }
            });
        } catch (Exception e) {
            Log.v("test", "error get" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    /**
     * save all partnerResponses
     *
     * @param partnersResponse
     */
    private List<PartnersResult> savePartnersData(final PartnersResponse partnersResponse) {
        Collection<PartnersResult> realmFixtures = null;
        List<PartnersResult> results = partnersResponse.getResults();

        if (results != null && results.size() > 0) {
            Log.v("test", "result present");
            mRealm.beginTransaction();
            realmFixtures = mRealm.copyToRealm(results);
            mRealm.commitTransaction();
        }
        if (realmFixtures != null && realmFixtures.size() > 0)
            return new ArrayList<PartnersResult>(realmFixtures);
        else
            return new ArrayList<>();
    }

    /**
     * populate saved db data in recyclerview
     */
    private void setUpAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            return;
        }
        Log.v("test", "data is " + mRealm.where(PartnersResult.class).findAll().get(0).getName());
        mAdapter = new PartnersAdapter(mRealm.where(PartnersResult.class).findAll());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.partnerClickListener = this;
    }

    @Override
    public void onClickPartner(String urlString) {
        if (urlString != null)
            Util.openUrl(mContext, urlString);
    }
}
