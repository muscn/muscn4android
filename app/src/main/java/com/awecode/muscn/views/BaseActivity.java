package com.awecode.muscn.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.awecode.muscn.R;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.awecode.muscn.util.stateLayout.StateLayout;
import com.awecode.muscn.views.home.HomeFragment;
import com.awecode.muscn.views.injuries.InjuriesFragment;
import com.awecode.muscn.views.league.LeagueTableFragment;
import com.awecode.muscn.views.matchweekfixtures.MatchWeekFixtureFragment;
import com.awecode.muscn.views.recentresults.MatchResultFragment;
import com.awecode.muscn.views.topscorer.TopScorersFragment;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by munnadroid on 9/21/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected MuscnApiInterface mApiInterface = ServiceGenerator.createService(MuscnApiInterface.class);
    @BindView(R.id.stateLayout)
    StateLayout mStateLayout;
    @BindView(R.id.title)
    TextView titleTextView;

    protected Context mContext;
    protected Activity mActivity;
    public FloatingActionMenu mActionMenu;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
    }

    protected int getDimen(int id) {
        return (int) mContext.getResources().getDimension(id);
    }

    public void toast(String message) {
        Util.toast(mContext, message);
    }

    protected abstract int getLayoutResourceId();

    protected void showProgressView(String message) {
        mStateLayout.showProgressView(message);
    }

    protected void showProgressView() {
        mStateLayout.showProgressView();
    }

    public void showErrorView(String message) {
        mStateLayout.showEmptyView(message);
    }

    protected void showErrorView() {
        mStateLayout.showEmptyView();
    }

    public void showContentView() {
        mStateLayout.showContentView();
    }

    public void setCustomTitle(int id) {
        titleTextView.setText(mContext.getResources().getString(id));
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.container,
                fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    public void openFragmentNoHistory(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.container,
                fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commitAllowingStateLoss();
    }

    public void setupFloatingActionButton() {
        mActionMenu = (FloatingActionMenu) findViewById(R.id.menu2);
    }

    @OnClick({R.id.fabFixtures, R.id.fabLeagueTable, R.id.fabInjuries, R.id.fabTopScores, R.id.fabEplMatchWeek, R.id.fabRecentResults})
    public void onClick(View view) {
        mActionMenu.close(true);
        switch (view.getId()) {
            case R.id.fabFixtures:
                openFragment(HomeFragment.newInstance());
                break;
            case R.id.fabLeagueTable:
                openFragment(LeagueTableFragment.newInstance());
                break;
            case R.id.fabInjuries:
                openFragment(InjuriesFragment.newInstance());
                break;
            case R.id.fabTopScores:
                openFragment(TopScorersFragment.newInstance());
                break;
            case R.id.fabEplMatchWeek:
                openFragment(new MatchWeekFixtureFragment());
                break;
            case R.id.fabRecentResults:
                openFragment(new MatchResultFragment());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mActionMenu.isOpened())
            mActionMenu.close(true);

        else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed();
                mActivity.finish();
            } else {
                Util.toast(mContext,getString(R.string.tapExitMessage));
            }

            mBackPressed = System.currentTimeMillis();
        }
    }
}
