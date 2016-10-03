package com.awecode.muscn.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;

import butterknife.ButterKnife;

/**
 * Created by surensth on 9/23/16.
 */

public abstract class MasterFragment extends Fragment {
    public MuscnApiInterface mApiInterface;
    public Context mContext;
    public HomeActivity mActivity;
    public FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mActivity = (HomeActivity) getActivity();
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

    }


    public void toast(String message) {
        Util.toast(mContext, message);
    }


    protected int getDimen(int id) {
        return (int) mContext.getResources().getDimension(id);
    }

    public MuscnApiInterface getApiInterface() {
        mApiInterface = ((BaseActivity) mContext).mApiInterface;
        return mApiInterface;
    }

    public void showProgressView(String message) {
        ((BaseActivity) mContext).showProgressView(message);
    }

    public void changeParallaxImage(int drawableId) {
        ((HomeActivity) getActivity()).setParallaxImageBackground(drawableId);
    }
}
