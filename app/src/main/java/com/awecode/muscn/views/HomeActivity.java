package com.awecode.muscn.views;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.fixtures.FixturesResponse;
import com.awecode.muscn.model.listener.FixturesApiListener;
import com.awecode.muscn.views.home.HomeFragment;

import butterknife.BindView;


public class HomeActivity extends BaseActivity implements FixturesApiListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    HomeFragment homeFragment;
    @BindView(R.id.background_one)
    ImageView mBackgroundOne;
    @BindView(R.id.background_two)
    ImageView mBackgroundTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragment = new HomeFragment();
        homeFragment.fixturesApiListener = this;
        openFragmentNoHistory(HomeFragment.newInstance());
        setupFloatingActionButton();
        configureParallaxBackgroundEffect();
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
}
