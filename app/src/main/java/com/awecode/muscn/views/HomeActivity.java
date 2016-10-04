package com.awecode.muscn.views;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.listener.FixturesApiListener;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.views.home.HomeFragment;
import com.github.clans.fab.FloatingActionMenu;


public class HomeActivity extends BaseActivity implements FixturesApiListener ,RecyclerViewScrollListener{

    private static final String TAG = HomeActivity.class.getSimpleName();
    HomeFragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragment = new HomeFragment();
        homeFragment.fixturesApiListener = this;
        openFragmentNoHistory(HomeFragment.newInstance());
        setupFloatingActionButton();
        transparentView.setVisibility(View.GONE);
        mActionMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened)
                    transparentView.setVisibility(View.VISIBLE);
                else
                    transparentView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }


    @Override
    public void onCallFixtures(FixturesResponse fixturesResponse) {
        setFixtureResponse(fixturesResponse);
    }

    @Override
    public void onRecyclerViewScrolled(RecyclerView recyclerView) {
        setScrollAnimation(recyclerView);
    }
}
