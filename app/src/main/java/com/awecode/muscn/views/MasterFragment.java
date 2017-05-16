package com.awecode.muscn.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.awecode.muscn.R;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by surensth on 9/23/16.
 */

public abstract class MasterFragment extends Fragment {
    public MuscnApiInterface mApiInterface;
    public Context mContext;
    public HomeActivity mActivity;
    public FragmentManager mFragmentManager;
    protected Realm mRealm;

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
        changeRandomParallaxImage();
        initializedRealm();
    }

    private void initializedRealm() {
        mRealm = Realm.getDefaultInstance();
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
//        ((HomeActivity) getActivity()).setParallaxImageBackground(drawableId);
    }


    public void changeRandomParallaxImage() {
        try {
            int drawables[] = {R.drawable.background_1,
                    R.drawable.background_2,
                    R.drawable.background_3,
                    R.drawable.background_4,
                    R.drawable.background_5,
                    R.drawable.background_6,
                    R.drawable.background_7};
            changeParallaxImage(drawables[(int) Math.floor(Math.random() * (6 - 0 + 1)) + 0]);
        } catch (Exception e) {
            e.printStackTrace();
            changeParallaxImage(R.drawable.background_2);
        }
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
}
