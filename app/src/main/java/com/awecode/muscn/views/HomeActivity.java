package com.awecode.muscn.views;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.enumType.MenuType;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.listener.FixturesApiListener;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.prefs.Prefs;
import com.awecode.muscn.views.aboutus.AboutUsActivity;
import com.awecode.muscn.views.home.HomeFragment;
import com.awecode.muscn.views.injuries.InjuriesFragment;
import com.awecode.muscn.views.league.LeagueTableFragment;
import com.awecode.muscn.views.matchweekfixtures.EplMatchWeekFixtureFragment;
import com.awecode.muscn.views.nav.NavigationDrawerCallbacks;
import com.awecode.muscn.views.nav.NavigationDrawerFragment;
import com.awecode.muscn.views.nav.NavigationItem;
import com.awecode.muscn.views.recentresults.ResultFragment;
import com.awecode.muscn.views.topscorer.TopScorersFragment;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.robohorse.gpversionchecker.GPVersionChecker;
import com.robohorse.gpversionchecker.base.VersionInfoListener;
import com.robohorse.gpversionchecker.domain.Version;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity implements FixturesApiListener, RecyclerViewScrollListener, NavigationDrawerCallbacks {

    private static final String TAG = HomeActivity.class.getSimpleName();
    HomeFragment homeFragment;
    @BindView(R.id.background_one)
    ImageView mBackgroundOne;
    @BindView(R.id.background_two)
    ImageView mBackgroundTwo;
    @BindView(R.id.fragment_drawer)
    View mNavLayout;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    //    @BindView(R.id.titleBarTextView)
//    TextView titleBarTextView;

    protected AlertDialog updateAlertDialog;
    protected Version mVersion = null;

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

        //create tap guidence of navigation drawer for first time of installation
        if (Prefs.getBoolean(Constants.PREFS_TAP_STATUS, false) != true)
            createTapTarget();

    }

    private void createTapTarget() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.muscnLogo), getString(R.string.tap_title), getString(R.string.tap_description))
                        // All options below are optional
                        .outerCircleColor(R.color.colorPrimary)      // Specify a color for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(25)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(20)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.white)  // Specify the color of the description text
//                        .textColor(R.color.gpvch_black)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.white)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .icon(ContextCompat.getDrawable(mContext, R.drawable.logo_white_muscn_new), false)                     // Specify a custom drawable to draw as the target
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        mNavigationDrawerFragment.openDrawer();
                        saveClickStatus();
                    }
                });
    }

    private void saveClickStatus() {
        Prefs.putBoolean(Constants.PREFS_TAP_STATUS, true);
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

            } else if (mResultFragment != null && mResultFragment.isVisible() && mResultFragment.isMenuVisible()) {
                mResultFragment.requestMatchResults();

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
                mResultFragment = new ResultFragment();
                openFragment(mResultFragment);
            } else if (menuType == MenuType.ABOUT_US) {
                startActivity(new Intent(this, AboutUsActivity.class));
            } else {
                mHomeFragment = HomeFragment.newInstance();
                openFragmentNoHistory(mHomeFragment, "HOME");
            }
        }
    }

    @OnClick(R.id.muscnLogo)
    public void onClickLogo() {
        mNavigationDrawerFragment.openDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForNewAppVersion();
    }

    /**
     * get app versioncode, update status
     */
    protected void checkForNewAppVersion() {
        new GPVersionChecker.Builder(HomeActivity.this).setVersionInfoListener(new VersionInfoListener() {
            @Override
            public void onResulted(Version version) {
                mVersion = version;
                prepareUpdateDialog();
            }
        }).create();

    }

    private void prepareUpdateDialog() {
        if (mVersion != null && updateAlertDialog == null && mVersion.isNeedToUpdate())
            showAppUpdateDialog();
    }

    /**
     * display App update alert dialog
     */
    protected void showAppUpdateDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(HomeActivity.this);
        final View promtView = layoutInflater.inflate(R.layout.update_dialog_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(promtView);

        TextView versionName = (TextView) promtView.findViewById(R.id.versionNameTextView);
        TextView versionChanges = (TextView) promtView.findViewById(R.id.versionChangesTextView);

        versionName.setText(": " + mVersion.getNewVersionCode());
        versionChanges.setText(mVersion.getChanges());

        builder.setTitle(com.robohorse.gpversionchecker.R.string.gpvch_header)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.awecode.muscn"));
                        startActivity(intent);
                        updateAlertDialog = null;
                    }
                });
        updateAlertDialog = builder.create();
        updateAlertDialog.show();
        //change the text color of button
        Button nButton = updateAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nButton.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        Button pButton = updateAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pButton.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }
}
