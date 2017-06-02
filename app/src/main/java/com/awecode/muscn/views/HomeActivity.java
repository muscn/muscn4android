package com.awecode.muscn.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.awecode.muscn.BuildConfig;
import com.awecode.muscn.R;
import com.awecode.muscn.model.enumType.MenuType;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.model.registration.RegistrationPostData;
import com.awecode.muscn.model.registration.RegistrationResponse;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.prefs.Prefs;
import com.awecode.muscn.views.aboutus.AboutUsActivity;
import com.awecode.muscn.views.base.BaseActivity;
import com.awecode.muscn.views.fixture.FixturesFragment;
import com.awecode.muscn.views.home.HomeFragment;
import com.awecode.muscn.views.injuries.InjuriesFragment;
import com.awecode.muscn.views.league.LeagueTableFragment;
import com.awecode.muscn.views.match_week.MatchWeekFragment;
import com.awecode.muscn.views.nav.NavigationDrawerCallbacks;
import com.awecode.muscn.views.nav.NavigationDrawerFragment;
import com.awecode.muscn.views.nav.NavigationItem;
import com.awecode.muscn.views.news.NewsFragment;
import com.awecode.muscn.views.recent_results.ResultFragment;
import com.awecode.muscn.views.top_scorers.TopScorersFragment;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.robohorse.gpversionchecker.GPVersionChecker;
import com.robohorse.gpversionchecker.base.VersionInfoListener;
import com.robohorse.gpversionchecker.domain.Version;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.awecode.muscn.util.Util.getAppVersion;


public class HomeActivity extends BaseActivity implements RecyclerViewScrollListener, NavigationDrawerCallbacks {

    private static final String TAG = HomeActivity.class.getSimpleName();
    @BindView(R.id.background_one)
    ImageView mBackgroundOne;
    @BindView(R.id.background_two)
    ImageView mBackgroundTwo;
    @BindView(R.id.fragment_drawer)
    View mNavLayout;
    @BindView(R.id.versionNavTextView)
    TextView mVersionTextView;

    private DrawerLayout mDrawerLayout;
    protected AlertDialog updateAlertDialog;
    protected Version mVersion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHomeFragment = HomeFragment.newInstance();
        //if device is not registered then registration request is send
        if (!Prefs.getBoolean(Constants.PREFS_DEVICE_REGISTERED, false))
            sendRegistrationToServer();
        setAppVersion();
        setup_onErrorClickListener();
        setupNavigationDrawerView();

        //create tap guidence of navigation drawer for first time of installation
        if (Prefs.getBoolean(Constants.PREFS_TAP_STATUS, false) != true)
            createTapTarget();

    }


    /**
     * create tap target view for first time
     */
    @SuppressLint("WrongViewCast")
    private void createTapTarget() {

        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.hamBurgerImageView),
                        getString(R.string.tap_title),
                        getString(R.string.tap_description))
                        // All options below are optional
                        .outerCircleColor(R.color.colorPrimary)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(25)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(20)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.white)  // Specify the color of the description text
                        .textColor(R.color.white)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                        .icon(ContextCompat.getDrawable(mContext, R.drawable.ic_ham_burger_taptarget))                     // Specify a custom drawable to draw as the target
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

    @SuppressLint("RestrictedApi")
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

            } else if (mMatchWeekFragment != null && mMatchWeekFragment.isVisible() && mMatchWeekFragment.isMenuVisible()) {
                mMatchWeekFragment.requestEplMatchResults();

            }
        } else {
            noInternetConnectionDialog(mContext);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }


    @Override
    public void onRecyclerViewScrolled(RecyclerView recyclerView) {
    }

    /**
     * intialize navigation drawer layout and fragment
     */
    private void setupNavigationDrawerView() {
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
            } else if (menuType == MenuType.FIXTURES) {
                mFixturesFragment = FixturesFragment.newInstance();
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
                mMatchWeekFragment = new MatchWeekFragment();
                openFragment(mMatchWeekFragment);
            } else if (menuType == MenuType.RECENT_RESULTS) {
                mResultFragment = new ResultFragment();
                openFragment(mResultFragment);
            } else if (menuType == MenuType.ABOUT_US)
                startActivity(new Intent(this, AboutUsActivity.class));
            else if(menuType==MenuType.NEWS)
                openFragment(NewsFragment.newInstance());
            else {
                mHomeFragment = HomeFragment.newInstance();
                openFragmentNoHistory(mHomeFragment, "HOME");
            }
        }
    }

    @OnClick(R.id.hamBurgerImageView)
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
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID));
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

    public void setAppVersion() {
        mVersionTextView.setText("v" + getAppVersion());
    }

    public void sendRegistrationToServer() {
        final String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String refreshedToken = Prefs.getString(Constants.PREFS_REFRESH_TOKEN, "");
        RegistrationPostData mRegistrationPostData = new RegistrationPostData(deviceId, refreshedToken, Build.MODEL, Constants.DEVICE_TYPE);

        Observable<RegistrationResponse> call = mApiInterface.postRegistrationData(mRegistrationPostData);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegistrationResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RegistrationResponse registrationResponse) {
                        Prefs.putBoolean(Constants.PREFS_DEVICE_REGISTERED, true);
                    }
                });

    }

}
