package com.awecode.muscn.views.signup;

import android.os.Bundle;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.enumType.MenuType;
import com.awecode.muscn.model.http.signup.SignUpPostData;
import com.awecode.muscn.views.base.AppCompatBaseActivity;
import com.awecode.muscn.views.membership_registration.RegistrationFragment;
import com.awecode.muscn.views.signin.SignInFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surensth on 5/23/17.
 */

public class SignUpActivity extends AppCompatBaseActivity {

    public static final String TYPE_INTENT = "type_intent";
    public static final String INTENT_SOCIAL_LOGIN_DATA = "intent_social_login_data";
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    MenuType menuType;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuType = (MenuType) getIntent().getSerializableExtra(TYPE_INTENT);
        if (menuType != null)
            if (menuType == MenuType.SIGN_UP)
                configureSignUpView();
            else if (menuType == MenuType.SIGN_IN_OUT)
                configureSignInView();
            else if (menuType == MenuType.MEMBERSHIP_REGISTRATION)
                configureMembershipRegistrationView();
    }

    private void configureMembershipRegistrationView() {
        setToolbarTitle(getString(R.string.membership_registration));
        openFragmentNoHistory(RegistrationFragment.newInstance());
    }

    private void configureSignInView() {
        setToolbarTitle(getString(R.string.sign_in));
        openFragmentNoHistory(SignInFragment.newInstance());
    }

    private void configureSignUpView() {
        setToolbarTitle(getString(R.string.signup));
        SignUpPostData signUpPostData = null;
        if (getIntent().hasExtra(INTENT_SOCIAL_LOGIN_DATA)) {
            signUpPostData = (SignUpPostData) getIntent().getParcelableExtra(INTENT_SOCIAL_LOGIN_DATA);
            openFragmentNoHistory(SignUpFragment.newInstance(signUpPostData));
        } else
            openFragmentNoHistory(SignUpFragment.newInstance());
    }

    /**
     * sets toolbar title
     *
     * @param message title
     */
    public void setToolbarTitle(String message) {
        toolbarTitle.setText(message);
    }


    @OnClick(R.id.angleBackImageView)
    public void onClick() {
        finish();
    }
}
