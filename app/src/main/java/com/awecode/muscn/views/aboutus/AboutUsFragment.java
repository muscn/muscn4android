package com.awecode.muscn.views.aboutus;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.MasterFragment;

import butterknife.BindView;

import static com.awecode.muscn.util.Util.getAppVersion;

/**
 * Created by surensth on 9/28/16.
 */

public class AboutUsFragment extends MasterFragment {
    @BindView(R.id.versionTextView)
    TextView versionTextView;
    @BindView(R.id.textViewAwecode)
    TextView textViewAwecode;
    @BindView(R.id.textViewOfficial_website)
    TextView textViewOfficial_website;
    @BindView(R.id.textViewEmail)
    TextView textViewEmail;
    @BindView(R.id.textViewFacebook)
    TextView textViewFacebook;
    @BindView(R.id.textViewTwitter)
    TextView textViewTwitter;
    @BindView(R.id.textViewInstagram)
    TextView textViewInstagram;

    String manutd, email, facebook, twitter, instagram, awecode;


    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

    public AboutUsFragment() {

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_about_us;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity.setCustomTitle(R.string.about_us);
        manutd = getResources().getString(R.string.official_website_link_text);
        facebook = getResources().getString(R.string.facebook_link_text);
        twitter = getResources().getString(R.string.twitter_link_text);
        email = getResources().getString(R.string.email_link_text);
        instagram = getResources().getString(R.string.instagram_link_text);
        awecode = getResources().getString(R.string.awecode_link_text);

        makeClickableLink(textViewEmail, email);
        makeClickableLink(textViewOfficial_website, manutd);
        makeClickableLink(textViewFacebook, facebook);
        makeClickableLink(textViewTwitter, twitter);
        makeClickableLink(textViewInstagram, instagram);
        makeClickableLink(textViewAwecode, awecode);
        versionTextView.setText("Version " + getAppVersion());

    }

    /**
     * @param textView textview of the link
     * @param string   string link
     */
    private void makeClickableLink(TextView textView, String string) {
        textView.setText(Util.removeUnderline(string));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setClickable(true);
    }

}
