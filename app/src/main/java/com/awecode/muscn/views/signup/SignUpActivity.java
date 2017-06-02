package com.awecode.muscn.views.signup;

import android.os.Bundle;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.enumType.MenuType;
import com.awecode.muscn.views.base.AppCompatBaseActivity;
import com.awecode.muscn.views.signin.SignInFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surensth on 5/23/17.
 */

public class SignUpActivity extends AppCompatBaseActivity {

    public static final String TYPE_INTENT = "type_intent";
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
        if (menuType != null && menuType == MenuType.SIGN_UP)
            configureSignUpView();
        else if (menuType != null && menuType == MenuType.SIGN_IN_OUT)
            configureSignInView();
    }

    private void configureSignInView() {
        openFragmentNoHistory(SignInFragment.newInstance());
    }

    private void configureSignUpView() {
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