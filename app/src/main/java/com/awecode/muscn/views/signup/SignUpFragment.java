package com.awecode.muscn.views.signup;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.signup.SignUpPostData;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.AppCompatBaseFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by surensth on 5/23/17.
 */

public class SignUpFragment extends AppCompatBaseFragment {
    private static final String TAG = "SignUpFragment";
    @NotEmpty(messageResId = R.string.not_empty_error_text)
    @BindView(R.id.fullnameEditText)
    EditText fullnameEditText;

    @NotEmpty(messageResId = R.string.not_empty_error_text)
    @BindView(R.id.usernameEditText)
    EditText usernameEditText;

    @NotEmpty(messageResId = R.string.not_empty_error_text)
    @Email(messageResId = R.string.invalid_email)
    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @NotEmpty(messageResId = R.string.not_empty_error_text)
    @Length(messageResId = R.string.invalid_length, min=8)
    @Password(messageResId = R.string.password_error_text, scheme = Password.Scheme.ALPHA_NUMERIC)
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @NotEmpty(messageResId = R.string.not_empty_error_text)
    @ConfirmPassword
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
        if (Util.checkInternetConnection(mContext))
            validateForm();
        else
            noInternetConnectionDialog();
    }

    //sign up request
    private void signUpRequest() {
        mActivity.showProgressDialog();
        SignUpPostData signUpPostData = new SignUpPostData();
        signUpPostData.setFullName(fullnameEditText.getText().toString());
        signUpPostData.setUsername(usernameEditText.getText().toString());
        signUpPostData.setEmail(emailEditText.getText().toString());
        if (passwordEditText.getText().toString().equalsIgnoreCase(confirmPasswordEditText.getText().toString()))
            signUpPostData.setPassword(passwordEditText.getText().toString());
        else {
            mActivity.showDialog(mContext, "Error!", getString(R.string.password_error));
            mActivity.closeProgressDialog();
            return;
        }
        Observable<SignUpPostData> call = mApiInterface.postSignUpData(signUpPostData);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SignUpPostData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO parse error and show message
                        e.printStackTrace();
                        mActivity.closeProgressDialog();
                        mActivity.noInternetConnectionDialog(mContext);
                    }

                    @Override
                    public void onNext(SignUpPostData signUpPostData) {
                        mActivity.closeProgressDialog();
                        Log.v(TAG, "success");
                        if (signUpPostData != null)
                            mActivity.showSuccessDialog(mContext, "Success!", "Successfully created account.");
                    }
                });
    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        signUpRequest();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        super.onValidationFailed(errors);
    }
}
