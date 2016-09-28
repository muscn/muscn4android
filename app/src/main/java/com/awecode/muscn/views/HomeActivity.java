package com.awecode.muscn.views;

import android.os.Bundle;

import com.awecode.muscn.R;
import com.awecode.muscn.views.home.HomeFragment;


public class HomeActivity extends BaseActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openFragmentNoHistory(HomeFragment.newInstance());
        setupFloatingActionButton();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }


}
