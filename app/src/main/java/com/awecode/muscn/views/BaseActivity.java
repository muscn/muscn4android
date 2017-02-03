package com.awecode.muscn.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.awecode.muscn.util.stateLayout.StateLayout;
import com.awecode.muscn.views.aboutus.AboutUsActivity;
import com.awecode.muscn.views.home.HomeFragment;
import com.awecode.muscn.views.injuries.InjuriesFragment;
import com.awecode.muscn.views.league.LeagueTableFragment;
import com.awecode.muscn.views.matchweekfixtures.EplMatchWeekFixtureFragment;
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
    @BindView(R.id.transparentView)
    View transparentView;

    private FixturesResponse fixturesResponse;
    protected Context mContext;
    protected Activity mActivity;
    public FloatingActionMenu mActionMenu;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    public HomeFragment mHomeFragment;
    public LeagueTableFragment mLeagueTableFragment;
    public FixturesFragment mFixturesFragment;
    public MatchResultFragment mMatchResultFragment;
    public EplMatchWeekFixtureFragment mEplMatchWeekFixtureFragment;
    public InjuriesFragment mInjuriesFragment;
    public TopScorersFragment mTopScorersFragment;


    public abstract void onErrorViewClicked();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
    }

    public void setup_onErrorClickListener() {
        mStateLayout.setErrorAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onErrorViewClicked();
            }
        });
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
        mStateLayout.showErrorView(message);
    }

    protected void showErrorView() {
        mStateLayout.showErrorView();
    }

    public void showContentView() {
        try {
            mStateLayout.showContentView();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void openFragmentNoHistory(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.container,
                fragment, tag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
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
        createCustomAnimation();

    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(mActionMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(mActionMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(mActionMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(mActionMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mActionMenu.getMenuIconView().setImageResource(mActionMenu.isOpened()
                        ? R.drawable.ic_fab_close_menu : R.drawable.ic_fab_menu);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        mActionMenu.setIconToggleAnimatorSet(set);
    }

    @OnClick({R.id.fabAboutUs, R.id.fabHome, R.id.fabLeagueTable, R.id.fabInjuries, R.id.fabTopScores, R.id.fabEplMatchWeek, R.id.fabRecentResults, R.id.fabFixtures})
    public void onClick(View view) {
        mActionMenu.close(true);
        switch (view.getId()) {
            case R.id.fabHome:
                mHomeFragment = HomeFragment.newInstance();
                openFragmentNoHistory(mHomeFragment, "HOME");
                break;
            case R.id.fabLeagueTable:
                mLeagueTableFragment = LeagueTableFragment.newInstance();
                openFragment(mLeagueTableFragment);
                break;
            case R.id.fabInjuries:
                mInjuriesFragment = InjuriesFragment.newInstance();
                openFragment(mInjuriesFragment);
                break;
            case R.id.fabTopScores:
                mTopScorersFragment = TopScorersFragment.newInstance();
                openFragment(mTopScorersFragment);
                break;
            case R.id.fabEplMatchWeek:
                mEplMatchWeekFixtureFragment = new EplMatchWeekFixtureFragment();
                openFragment(mEplMatchWeekFixtureFragment);
                break;
            case R.id.fabRecentResults:
                mMatchResultFragment = new MatchResultFragment();
                openFragment(mMatchResultFragment);
                break;
            case R.id.fabFixtures:
                mFixturesFragment = FixturesFragment.newInstance(fixturesResponse);
                openFragment(mFixturesFragment);
                break;
            case R.id.fabAboutUs:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag("HOME");

        if (mActionMenu.isOpened())
            mActionMenu.close(true);    //if fab menu is open, then close it
        else {
            if (homeFragment.isVisible()) {
                //if home fragment is visible do nothing
                finish();
            } else
                openFragmentNoHistory(HomeFragment.newInstance(), "HOME");
        }

//        openFragment(HomeFragment.newInstance());
//        super.onBackPressed();
//        if (mActionMenu.isOpened())
//            mActionMenu.close(true);
//
//        else {
//            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
//                super.onBackPressed();
//                mActivity.finish();
//            } else {
//                Util.toast(mContext, getString(R.string.tapExitMessage));
//            }
//
//            mBackPressed = System.currentTimeMillis();
//        }
    }

    public void setFixtureResponse(FixturesResponse fixtureResponse) {
        this.fixturesResponse = fixtureResponse;
    }

    /**
     * when recycler view is scrolled, floating action button hide/show
     *
     * @param recyclerView recyclerview with scroll enabled
     */
    public void setScrollAnimation(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    mActionMenu.hideMenu(true);
                else if (dy < 0)
                    mActionMenu.showMenu(true);
            }
        });
    }

    /**
     * dialog to show message
     *
     * @param context context of current view
     * @param title   title of the dialog to show
     * @param message message to show in dialog
     */
    public void showDialog(final Context context, String title, String message) {
        new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                        dialog.dismiss();
                        showErrorView();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void noInternetConnectionDialog(Context mContext) {
        showDialog(mContext, "Oops!", getString(R.string.no_internet_message));
//        showErrorView();
//        mActionMenu.hideMenu(true);
    }
}
