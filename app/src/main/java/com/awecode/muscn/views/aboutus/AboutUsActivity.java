package com.awecode.muscn.views.aboutus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.awecode.muscn.util.Util.getAppVersion;

/**
 * Created by surensth on 11/16/16.
 */

public class AboutUsActivity extends AppCompatActivity {

    @BindView(R.id.textViewDescription)
    TextView textViewDescription;
    @BindView(R.id.textViewMembership)
    TextView textViewMembership;
    @BindView(R.id.websiteLayout)
    LinearLayout websiteLayout;
    @BindView(R.id.emailLayout)
    LinearLayout emailLayout;
    @BindView(R.id.facebookLayout)
    LinearLayout facebookLayout;
    @BindView(R.id.twitterLayout)
    LinearLayout twitterLayout;
    @BindView(R.id.instagramLayout)
    LinearLayout instagramLayout;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

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
