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
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;

import butterknife.ButterKnife;

/**
 * Created by surensth on 5/23/17.
 */

public abstract class AppCompatBaseFragment extends Fragment {
    public MuscnApiInterface mApiInterface;
    public Context mContext;
    public AppCompatBaseActivity mActivity;
    public FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mActivity = (AppCompatBaseActivity) getActivity();
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApiInterface = getApiInterface();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public abstract int getLayout();

    public void toast(String message) {
        Util.toast(mContext, message);
    }

    protected int getDimen(int id) {
        return (int) mContext.getResources().getDimension(id);
    }

    public MuscnApiInterface getApiInterface() {
        return mApiInterface = ((MyApplication) getActivity().getApplication()).getApiInterface();
    }

    protected void openFragment(Fragment fragment) {
        ((BaseActivity) mContext).openFragment(fragment);
    }

    public void noInternetConnectionDialog() {
        ((AppCompatBaseActivity) mContext).noInternetConnectionDialog(mContext);
    }
}
