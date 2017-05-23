package com.awecode.muscn.views.signup;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.awecode.muscn.R;
import com.awecode.muscn.views.AppCompatBaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surensth on 5/23/17.
 */

public class SignUpFragment extends AppCompatBaseFragment {
    @BindView(R.id.fullnameEditText)
    EditText fullnameEditText;
    @BindView(R.id.usernameEditText)
    EditText usernameEditText;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.confirmPasswordEditText)
    EditText confirmPasswordEditText;

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    public SignUpFragment() {
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_signup;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SignUpActivity) mContext).setToolbarTitle(getString(R.string.signup));
    }

    @OnClick(R.id.signUpButton)
    public void onClick() {
        String fullname = fullnameEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        signUpRequest();

    }

    private void signUpRequest() {

    }
}
