package com.awecode.muscn.views.matchweekfixtures;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.MatchFixutreRecyclerviewAdapter;
import com.awecode.muscn.model.http.eplmatchweek._20161001;
import com.awecode.muscn.model.listener.MatchweekItemClickListener;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.awecode.muscn.views.HomeActivity;
import com.awecode.muscn.views.MasterFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by suresh on 9/28/16.
 */

public class EplMatchWeekFixtureFragment extends MasterFragment implements MatchweekItemClickListener {

    @BindView(R.id.matchFixtures)
    RecyclerView matchFixtures;

    private MatchFixutreRecyclerviewAdapter mMatchFixutreRecyclerviewAdapter;
    private RecyclerViewScrollListener mRecyclerViewScrollListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerViewScrollListener = (RecyclerViewScrollListener) this.getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_epl_matchweek_fixture, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setCustomTitle(R.string.epl_matchweek);
        if (Util.checkInternetConnection(mContext))
            requestEplMatchResults();
        else {
            ((HomeActivity) mContext).noInternetConnectionDialog(mContext);
        }
    }

    /**
     * Setup match fixture recyclerview to show the matchweek mixtures
     */
    public void setupMatchFixtureRecylerView(ArrayList<_20161001> mCategoryList) {
        matchFixtures.setHasFixedSize(true);
        matchFixtures.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMatchFixutreRecyclerviewAdapter = new MatchFixutreRecyclerviewAdapter(getActivity(), mCategoryList);
        matchFixtures.setAdapter(Util.getAnimationAdapter(mMatchFixutreRecyclerviewAdapter));
        mRecyclerViewScrollListener.onRecyclerViewScrolled(matchFixtures);
        mMatchFixutreRecyclerviewAdapter.mMatchweekItemClickListener = this;
    }

    /**
     * fetch manutd recent match results list
     */
    public void requestEplMatchResults() {
        showProgressView(getString(R.string.loading_fixtures));
        mApiInterface = ServiceGenerator.createService(MuscnApiInterface.class);
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
                            HashMap<String, ArrayList<_20161001>> mMap = new HashMap<String, ArrayList<_20161001>>();
                            ArrayList<_20161001> mCategoryList = new ArrayList<_20161001>();

                            while (keys.hasNext()) {
                                // here you will get dynamic keys
                                String dynamicKey = (String) keys.next();

                                // get the value of the dynamic key
                                JSONArray dynamicValue = object.getJSONArray(dynamicKey);

                                //Let's store into POJO Class and Prepare HashMap.
                                for (int i = 0; i < dynamicValue.length(); i++) {
                                    _20161001 mCategory = new _20161001();
                                    mCategory.setAwayTeam(dynamicValue.getJSONObject(i).get("away_team").toString());
                                    mCategory.setHomeTeam(dynamicValue.getJSONObject(i).get("home_team").toString());
                                    mCategory.setEid(dynamicValue.getJSONObject(i).get("eid").toString());
                                    mCategory.setKickoff(dynamicValue.getJSONObject(i).get("kickoff").toString());
                                    mCategory.setLive(dynamicValue.getJSONObject(i).get("live").toString());
                                    mCategory.setMinute(dynamicValue.getJSONObject(i).get("minute").toString());
                                    mCategory.setScore(dynamicValue.getJSONObject(i).get("score").toString());
                                    mCategory.setFixtureId(dynamicValue.getJSONObject(i).get("fixture_id").toString());
                                    mCategoryList.add(mCategory);
                                }
                                //Add Into Hashmap
                                mMap.put(dynamicKey, mCategoryList);
                                Collections.sort(mCategoryList);
                                setupMatchFixtureRecylerView(mCategoryList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onEplMatchWeekClicked(String fixtureId) {
//        openFragment(EplMatchWeekFixtureDetailsFragment.newInstance(fixtureId));
    }
}
