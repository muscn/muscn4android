package com.awecode.muscn.views.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.awecode.muscn.MyApplication;
import com.awecode.muscn.R;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.stateLayout.StateLayout;
import com.awecode.muscn.views.fixture.FixturesFragment;
import com.awecode.muscn.views.home.HomeFragment;
import com.awecode.muscn.views.injuries.InjuriesFragment;
import com.awecode.muscn.views.league.LeagueTableFragment;
import com.awecode.muscn.views.match_week.MatchWeekFragment;
import com.awecode.muscn.views.nav.NavigationDrawerFragment;
import com.awecode.muscn.views.recent_results.ResultFragment;
import com.awecode.muscn.views.top_scorers.TopScorersFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by munnadroid on 9/21/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected MuscnApiInterface mApiInterface = null;
    @BindView(R.id.stateLayout)
    StateLayout mStateLayout;
    @BindView(R.id.title)
    TextView titleTextView;

    protected NavigationDrawerFragment mNavigationDrawerFragment;
    protected Realm mRealm;


    protected Context mContext;
    protected Activity mActivity;
    public HomeFragment mHomeFragment;
    public LeagueTableFragment mLeagueTableFragment;
    public FixturesFragment mFixturesFragment;
    public ResultFragment mResultFragment;
    public MatchWeekFragment mMatchWeekFragment;
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
        mApiInterface = ((MyApplication) getApplication()).getApiInterface();
        initializedRealm();

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

    public void showProgressView(String message) {
        mStateLayout.showProgressView(message);
    }

    protected void showProgressView() {
        mStateLayout.showProgressView();
    }

    public void showErrorView(String message) {
        mStateLayout.showErrorView(message);
    }

    public void showErrorView() {
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
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    public void openFragmentNoHistory(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.container,
                fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public void openFragmentNoHistory(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.container,
                fragment);
        ft.commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag("HOME");
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer(); //close navigation bar on back pressed if opened
        else {
            if (homeFragment.isVisible()) {
                //if home fragment is visible do nothing
                finish();
            } else
                openFragmentNoHistory(HomeFragment.newInstance(), "HOME");
        }
    }

    /**
     * dialog to show message
     *
     * @param context context of current view
     * @param message message to show in dialog
     */
    public void showDialog(final Context context, String message) {
        new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showErrorView();
                    }
                })
                .show();
    }

    public void noInternetConnectionDialog(Context mContext) {
        showDialog(mContext, getString(R.string.no_internet_message));
    }

    protected int getTableDataCount(Class type) {
        return (int) mRealm.where(type).count();
    }

    private void initializedRealm() {
        mRealm = ((MyApplication) getApplication()).getRealmInstance();
    }

    public void showSuccessDialog(final Context context, String title, String message) {
        new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
