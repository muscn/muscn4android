package com.awecode.muscn.views.nav;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.awecode.muscn.R;
import com.awecode.muscn.model.enumType.MenuType;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.prefs.Prefs;
import com.awecode.muscn.util.prefs.PrefsHelper;
import com.awecode.muscn.views.MasterFragment;
import com.awecode.muscn.views.signup.SignUpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surensth on 10/6/16.
 */
public class NavigationDrawerFragment extends MasterFragment implements NavigationDrawerCallbacks {
    private static final String TAG = NavigationDrawerFragment.class.getSimpleName();
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREFERENCES_FILE = "my_app_settings"; //TODO: change this to your file

    @BindView(R.id.signInButton)
    Button signInButton;
    @BindView(R.id.signUpButton)
    Button signUpButton;
    private NavigationDrawerCallbacks mCallbacks;
    private RecyclerView mDrawerList;
    private View mFragmentContainerView;
    private DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mActionBarDrawerToggle;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private int mCurrentSelectedPosition;
    private List<NavigationItem> navigationItems;

    @Override
    public int getLayout() {
        return R.layout.fragment_navigation_drawer;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDrawerList = (RecyclerView) view.findViewById(R.id.drawerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);
        populateNavMenu();
        mCurrentSelectedPosition = 0;
        selectItem(mCurrentSelectedPosition, navigationItems.get(mCurrentSelectedPosition));
    }


    private void populateNavMenu() {
        navigationItems = getMenu();
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(navigationItems, getActivity());
        adapter.setNavigationDrawerCallbacks(this);
        mDrawerList.setAdapter(adapter);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(getActivity(), PREF_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallbacks = (NavigationDrawerCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }


    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return mActionBarDrawerToggle;
    }

    public void setActionBarDrawerToggle(ActionBarDrawerToggle actionBarDrawerToggle) {
        mActionBarDrawerToggle = actionBarDrawerToggle;
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout/*, Toolbar toolbar*/) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout/*, toolbar*/, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) return;
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveSharedSetting(getActivity(), PREF_USER_LEARNED_DRAWER, "true");
                }

                getActivity().invalidateOptionsMenu();
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState)
            mDrawerLayout.openDrawer(mFragmentContainerView);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public List<NavigationItem> getMenu() {
        List<NavigationItem> items = new ArrayList<NavigationItem>();
        items.add(new NavigationItem("Home", "1", R.drawable.ic_home, true, MenuType.HOME)); //0 pos
        items.add(new NavigationItem("Fixtures", "2", R.drawable.ic_fab_fixtures, true, MenuType.FIXTURES));//1 pos
        items.add(new NavigationItem("League Table", "3", R.drawable.ic_fab_league_table, true, MenuType.LEAGUE_TABLE)); //2 pos
        items.add(new NavigationItem("Injuries", "4", R.drawable.ic_fab_injuries, true, MenuType.INJURIES));//3 pos
        items.add(new NavigationItem("Top Scorers", "5", R.drawable.ic_fab_tops_scorer, true, MenuType.TOPSCORERS));//4 pos
        items.add(new NavigationItem("EPL Matchweek", "6", R.drawable.ic_fab_epl_matchweek, true, MenuType.EPL_MATCH_WEEK));//5 pos
        items.add(new NavigationItem("Recent Results", "7", R.drawable.ic_fab_recent_result, true, MenuType.RECENT_RESULTS));//6 pos
        items.add(new NavigationItem("News", "8", R.drawable.ic_rss_feed_24dp, true, MenuType.NEWS));//7 pos
        items.add(new NavigationItem("About Us", "9", R.drawable.ic_fab_about_us, true, MenuType.ABOUT_US));//8 pos
        items.add(new NavigationItem("Partners", "10", R.drawable.ic_partner_24dp, true, MenuType.PARTNERS));//9 pos

        return items;
    }

    void selectItem(int position, NavigationItem navigationItem) {
        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position, navigationItem);
        }
        ((NavigationDrawerAdapter) mDrawerList.getAdapter()).selectPosition(position);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, NavigationItem navigationItem) {
        selectItem(position, navigationItem);
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    @OnClick({R.id.signUpButton, R.id.signInButton})
    public void onClick(View view) {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        switch (view.getId()) {
            case R.id.signUpButton:
                String text = signUpButton.getText().toString();

                Intent intent = new Intent(mContext, SignUpActivity.class);

                if (text.equalsIgnoreCase(getString(R.string.membership_registration))) {
                    intent.putExtra(SignUpActivity.TYPE_INTENT, MenuType.MEMBERSHIP_REGISTRATION);
                } else {
                    intent.putExtra(SignUpActivity.TYPE_INTENT, MenuType.SIGN_UP);
                }
                startActivity(intent);
                break;
            case R.id.signInButton:
                if (signInButton.getText().toString().equalsIgnoreCase(getString(R.string.sign_in))) {
                    Intent signInIntent = new Intent(mContext, SignUpActivity.class);
                    signInIntent.putExtra(SignUpActivity.TYPE_INTENT, MenuType.SIGN_IN_OUT);
                    startActivity(signInIntent);
                } else {//logout case
                    PrefsHelper.saveLoginStatus(false);
                    Prefs.remove(Constants.PREFS_LOGIN_TOKEN);
                    mActivity.showSuccessDialog(mContext, getString(R.string.success), getString(R.string.success_sign_out_text));
                    signInButton.setText(getString(R.string.sign_in));
                    signUpButton.setText(getString(R.string.sign_up));

                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (PrefsHelper.getLoginStatus()) {
            signInButton.setText(getString(R.string.sign_out));
            if (!PrefsHelper.getLoginResponse().getStatus())
                signUpButton.setText(getString(R.string.membership_registration));
            else
                signUpButton.setText("");


        } else {
            signInButton.setText(getString(R.string.sign_in));
            signUpButton.setText(getString(R.string.signup));
        }
    }
}
