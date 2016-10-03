package com.awecode.muscn.views;

import android.os.Bundle;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.listener.FixturesApiListener;
import com.awecode.muscn.views.home.HomeFragment;


public class HomeActivity extends BaseActivity implements FixturesApiListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    HomeFragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragment = new HomeFragment();
        homeFragment.fixturesApiListener=this;
        openFragmentNoHistory(HomeFragment.newInstance());
        setupFloatingActionButton();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }


    @Override
    public void onCallFixtures(FixturesResponse fixturesResponse) {
        setFixtureResponse(fixturesResponse);
    }
}
