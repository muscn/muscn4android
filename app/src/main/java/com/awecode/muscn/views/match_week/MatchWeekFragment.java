package com.awecode.muscn.views.match_week;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.MatchWeekAdapter;
import com.awecode.muscn.model.http.eplmatchweek.EplMatchweekResponse;
import com.awecode.muscn.model.http.top_scorers.TopScorersResponse;
import com.awecode.muscn.model.listener.MatchweekItemClickListener;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.MasterFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.Sort;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by suresh on 9/28/16.
 */

public class MatchWeekFragment extends MasterFragment implements MatchweekItemClickListener {

    @BindView(R.id.matchFixtures)
    RecyclerView matchFixtures;

    private MatchWeekAdapter mMatchWeekAdapter;
    private RecyclerViewScrollListener mRecyclerViewScrollListener;
    private RealmAsyncTask mTransaction;
    private int dbDataCount;

    @Override
    public int getLayout() {
        return R.layout.fragment_epl_matchweek_fixture;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerViewScrollListener = (RecyclerViewScrollListener) this.getContext();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setCustomTitle(R.string.epl_matchweek);
        initializeRecyclerView();
        checkInternetConnection();
    }

    private void initializeRecyclerView() {
        matchFixtures.setHasFixedSize(true);
        matchFixtures.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * check internet, initialize request
     * if no internet, show message incase of empty db,
     * show saved data incase of data in db
     */
    private void checkInternetConnection() {
        dbDataCount = getTableDataCount(TopScorersResponse.class);
        if (dbDataCount > 1)
            setUpAdapter();

        if (Util.checkInternetConnection(mContext))
            requestEplMatchResults();
        else if (dbDataCount < 1)
            noInternetConnectionDialog();


    }


    /**
     * Setup match fixture recyclerview to show the matchweek mixtures
     */
    public void setUpAdapter() {

        if (mMatchWeekAdapter != null) {
            mMatchWeekAdapter.notifyDataSetChanged();
            return;
        }
        mMatchWeekAdapter = new MatchWeekAdapter(mRealm.where(EplMatchweekResponse.class).findAllSorted("kickoff", Sort.ASCENDING));
        matchFixtures.setAdapter(Util.getAnimationAdapter(mMatchWeekAdapter));
        mRecyclerViewScrollListener.onRecyclerViewScrolled(matchFixtures);
        mMatchWeekAdapter.mMatchweekItemClickListener = this;
    }

    /**
     * fetch manutd recent match results list
     */
    public void requestEplMatchResults() {
        if (dbDataCount < 1)
            showProgressView(getString(R.string.loading_data));

        Observable<ResponseBody> call = mApiInterface.getEplMatchweekFixtures();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        mActivity.showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.noInternetConnectionDialog(mContext);

                    }

                    @Override
                    public void onNext(ResponseBody eplMatchweekFixturesResponse) {

                        /**
                         * fetch dynamic key and its values from API and store it in ArrayList
                         */
                        try {
                            JSONObject object = new JSONObject(eplMatchweekFixturesResponse.string());
                            Iterator keys = object.keys();

                            //Let's consider your POJO class is CategoryClass
                            // Let's take HashMap to store your POJO class for specific KEY
                            List<EplMatchweekResponse> responseList = new ArrayList<EplMatchweekResponse>();

                            while (keys.hasNext()) {
                                // here you will get dynamic keys
                                String dynamicKey = (String) keys.next();

                                // get the value of the dynamic key
                                JSONArray dynamicValue = object.getJSONArray(dynamicKey);

                                Type listType = new TypeToken<List<EplMatchweekResponse>>() {
                                }.getType();
                                // In this test code i just shove the JSON here as string.
                                List<EplMatchweekResponse> dataList = new Gson().fromJson(dynamicValue.toString(), listType);
                                responseList.addAll(dataList);
                            }
                            if (responseList != null
                                    && responseList.size() > 0)
                                deleteDataFromDBAndSave(responseList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * first delete the existing  data from db
     */
    private void deleteDataFromDBAndSave(final List<EplMatchweekResponse> responseList) {
        try {
            //delete fixture results first
            mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(EplMatchweekResponse.class);
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
    private void saveResponseData(final List<EplMatchweekResponse> responseList) {

        if (responseList != null && responseList.size() > 0) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(responseList);
            mRealm.commitTransaction();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTransaction != null && !mTransaction.isCancelled())
            mTransaction.cancel();

    }

    @Override
    public void onEplMatchWeekClicked(String fixtureId) {
//        openFragment(EplMatchWeekFixtureDetailsFragment.newInstance(fixtureId));
    }
}
