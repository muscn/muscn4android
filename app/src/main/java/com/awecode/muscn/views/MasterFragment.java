package com.awecode.muscn.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awecode.muscn.MyApplication;
import com.awecode.muscn.R;
import com.awecode.muscn.model.http.fixtures.Result;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.feed.FeedApiInterface;
import com.awecode.muscn.views.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by surensth on 9/23/16.
 */

public abstract class MasterFragment extends Fragment {
    public MuscnApiInterface mApiInterface;
    protected FeedApiInterface mFeedApiInterface;
    public Context mContext;
    public BaseActivity mActivity;
    public FragmentManager mFragmentManager;
    protected Realm mRealm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mActivity = (BaseActivity) getActivity();
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mApiInterface = getApiInterface();
        mFeedApiInterface = getFeedApiInterface();

        initializedRealm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public abstract int getLayout();

    private void initializedRealm() {
        try {
            mRealm = ((MyApplication) getActivity().getApplication()).getRealmInstance();
        } catch (RealmMigrationNeededException e) {
            e.printStackTrace();
        }
    }


    public void toast(String message) {
        Util.toast(mContext, message);
    }


    protected int getDimen(int id) {
        return (int) mContext.getResources().getDimension(id);
    }

    public MuscnApiInterface getApiInterface() {
        return ((MyApplication) getActivity().getApplication()).getApiInterface();
    }

    public FeedApiInterface getFeedApiInterface() {
        return ((MyApplication) getActivity().getApplication()).getFeedApiInterface();
    }

    public void showProgressView(String message) {
        ((BaseActivity) mContext).showProgressView(message);
    }


    protected void showErrorView() {
        ((BaseActivity) mContext).showErrorView();
    }

    protected void showContentView() {
        ((BaseActivity) mContext).showContentView();
    }

    protected void openFragment(Fragment fragment) {
        ((BaseActivity) mContext).openFragment(fragment);
    }

    protected List<Result> deletePastFixtureTable() {
        RealmResults<Result> results = mRealm.where(Result.class).findAll();
        for (Result data : results) {
            try {
                if (Util.matchDateIsBeforeToday(data.getDatetime())) {
                    //delete opponent
                    data.getOpponent().deleteFromRealm();
                    //delete competition
                    data.getCompetitionYear().getCompetition().deleteFromRealm();
                    //delete competition year
                    data.getCompetitionYear().deleteFromRealm();
                    //delete result
                    data.deleteFromRealm();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ArrayList<Result>(mRealm.where(Result.class).findAll());

    }

    public void noInternetConnectionDialog() {
        ((HomeActivity) mContext).noInternetConnectionDialog(mContext);
    }


    protected int getTableDataCount(Class type) {
        return (int) mRealm.where(type).count();
    }

}
