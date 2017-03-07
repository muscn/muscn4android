package com.awecode.muscn.views;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.enumType.MenuType;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.listener.FixturesApiListener;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.aboutus.AboutUsActivity;
import com.awecode.muscn.views.home.HomeFragment;
import com.awecode.muscn.views.injuries.InjuriesFragment;
import com.awecode.muscn.views.league.LeagueTableFragment;
import com.awecode.muscn.views.matchweekfixtures.EplMatchWeekFixtureFragment;
import com.awecode.muscn.views.nav.NavigationDrawerCallbacks;
import com.awecode.muscn.views.nav.NavigationDrawerFragment;
import com.awecode.muscn.views.nav.NavigationItem;
import com.awecode.muscn.views.recentresults.MatchResultFragment;
import com.awecode.muscn.views.topscorer.TopScorersFragment;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity implements FixturesApiListener, RecyclerViewScrollListener, NavigationDrawerCallbacks {

    private static final String TAG = HomeActivity.class.getSimpleName();
    HomeFragment homeFragment;
    @BindView(R.id.background_one)
    ImageView mBackgroundOne;
    @BindView(R.id.background_two)
    ImageView mBackgroundTwo;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
//    @BindView(R.id.titleBarTextView)
//    TextView titleBarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        homeFragment = new HomeFragment();
        homeFragment.fixturesApiListener = this;
        openFragmentNoHistory(HomeFragment.newInstance(), "HOME");
//        openFragmentNoHistory(HomeFragment.newInstance());

//        setupFloatingActionButton();
//        transparentView.setVisibility(View.GONE);
//        mActionMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
//            @Override
//            public void onMenuToggle(boolean opened) {
//                if (opened)
//                    transparentView.setVisibility(View.VISIBLE);
//                else
//                    transparentView.setVisibility(View.GONE);
//            }
//        });

        //TODO add new image animation
//        configureParallaxBackgroundEffect();
        setup_onErrorClickListener();
        setupNavigationDrawerView();

    }

    @Override
    public void onErrorViewClicked() {
        if (Util.checkInternetConnection(mContext)) {
            if (mHomeFragment != null && mHomeFragment.isVisible() && mHomeFragment.isMenuVisible()) {
                mHomeFragment.requestFixturesList();
            } else if (mLeagueTableFragment != null && mLeagueTableFragment.isVisible() && mLeagueTableFragment.isMenuVisible()) {
                mLeagueTableFragment.requestLeagueTable();
            } else if (mInjuriesFragment != null && mInjuriesFragment.isVisible() && mInjuriesFragment.isMenuVisible()) {
                mInjuriesFragment.requestInjuries();
            } else if (mTopScorersFragment != null && mTopScorersFragment.isVisible() && mTopScorersFragment.isMenuVisible()) {
                mTopScorersFragment.requestTopScorers();

            } else if (mMatchResultFragment != null && mMatchResultFragment.isVisible() && mMatchResultFragment.isMenuVisible()) {
                mMatchResultFragment.requestMatchResults();

            } else if (mEplMatchWeekFixtureFragment != null && mEplMatchWeekFixtureFragment.isVisible() && mEplMatchWeekFixtureFragment.isMenuVisible()) {
                mEplMatchWeekFixtureFragment.requestEplMatchResults();

            }
        } else {
            noInternetConnectionDialog(mContext);
        }
    }


    public void setParallaxImageBackground(int drawableId) {
        mBackgroundOne.setImageDrawable(null);
        mBackgroundTwo.setImageDrawable(null);
        mBackgroundOne.setImageDrawable(ContextCompat.getDrawable(mContext, drawableId));
        mBackgroundTwo.setImageDrawable(ContextCompat.getDrawable(mContext, drawableId));
    }

    private void configureParallaxBackgroundEffect() {
        try {
            final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(80000L);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    final float progress = (float) animation.getAnimatedValue();
                    final float width = mBackgroundOne.getWidth();
                    final float translationX = width * progress;
                    mBackgroundOne.setTranslationX(translationX);
                    mBackgroundTwo.setTranslationX(translationX - width);
                }
            });
            animator.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//        setScrollAnimation(recyclerView);
    }

    /**
     * intialize navigation drawer layout and fragment
     */
    private void setupNavigationDrawerView() {
//        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, mDrawerLayout/*, mToolbar*/);
        mDrawerLayout.closeDrawer(Gravity.LEFT);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position, NavigationItem navigationItem) {
        MenuType menuType = navigationItem.getMenuType();
        if (menuType != null) {
            if (menuType == MenuType.HOME) {
                mHomeFragment = HomeFragment.newInstance();
                openFragmentNoHistory(mHomeFragment, "HOME");
            }
//                openNewSaleFragment();//open new sale view
            else if (menuType == MenuType.FIXTURES) {
                mFixturesFragment = FixturesFragment.newInstance(fixturesResponse);
                openFragment(mFixturesFragment);
            } else if (menuType == MenuType.LEAGUE_TABLE) {
                mLeagueTableFragment = LeagueTableFragment.newInstance();
                openFragment(mLeagueTableFragment);
            } else if (menuType == MenuType.INJURIES) {
                mInjuriesFragment = InjuriesFragment.newInstance();
                openFragment(mInjuriesFragment);
            } else if (menuType == MenuType.TOPSCORERS) {
                mTopScorersFragment = TopScorersFragment.newInstance();
                openFragment(mTopScorersFragment);
            } else if (menuType == MenuType.EPL_MATCH_WEEK) {
                mEplMatchWeekFixtureFragment = new EplMatchWeekFixtureFragment();
                openFragment(mEplMatchWeekFixtureFragment);
            } else if (menuType == MenuType.RECENT_RESULTS) {
                mMatchResultFragment = new MatchResultFragment();
                openFragment(mMatchResultFragment);
            } else if (menuType == MenuType.ABOUT_US) {
                startActivity(new Intent(this, AboutUsActivity.class));
            } else {
                mHomeFragment = HomeFragment.newInstance();
                openFragmentNoHistory(mHomeFragment, "HOME");
            }
        }
    }
    @OnClick(R.id.muscnLogo)
    public void onClickLogo(){
        mNavigationDrawerFragment.openDrawer();
    }
}
