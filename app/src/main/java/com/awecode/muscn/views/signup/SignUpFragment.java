package com.awecode.muscn.views.signup;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.api_error.APIError;
import com.awecode.muscn.model.http.signup.SignUpPostData;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.base.AppCompatBaseFragment;
import com.awecode.muscn.views.signin.SignInFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
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
    @Email(messageResId = R.string.invalid_email)
    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @NotEmpty(messageResId = R.string.not_empty_error_text)
    @Password()
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @NotEmpty(messageResId = R.string.not_empty_error_text)
    @ConfirmPassword
    @BindView(R.id.confirmPasswordEditText)
    EditText confirmPasswordEditText;
    private SignUpPostData mSignupPostData;

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    public static SignUpFragment newInstance(SignUpPostData signUpPostData) {
        SignUpFragment fragment = new SignUpFragment();
        fragment.setData(signUpPostData);
        return fragment;
    }

    public SignUpFragment() {
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_signup;
    }


    @OnClick(R.id.signUpButton)
    public void onClick() {
        if (Util.checkInternetConnection(mContext))
            validateForm();
        else
            noInternetConnectionDialog();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        confirmPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    validateForm();
                    handled = true;
                }
                return handled;
            }
        });


        if (mSignupPostData != null) {
            fullnameEditText.setText(mSignupPostData.getFullName());
            emailEditText.setText(mSignupPostData.getEmail());
        }
    }

    //sign up request
    private void signUpRequest() {
        mActivity.showProgressDialog("Creating account...");
        SignUpPostData signUpPostData = new SignUpPostData();
        signUpPostData.setFullName(fullnameEditText.getText().toString());
        signUpPostData.setEmail(emailEditText.getText().toString());
        signUpPostData.setPassword(passwordEditText.getText().toString());

        //if from social login
        if (mSignupPostData != null) {
            SignUpPostData socialData = new SignUpPostData();
            socialData.setProvider(mSignupPostData.getProvider());
            socialData.setFbToken(mSignupPostData.getFbToken());

            signUpPostData.setSocialData(socialData);
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
                        mActivity.closeProgressDialog();
                        showSignupError(e);

                    }

                    @Override
                    public void onNext(SignUpPostData signUpPostData) {
                        mActivity.closeProgressDialog();
                        if (signUpPostData != null)
                            mActivity.successDialogAndOpen(mContext, getString(R.string.successful_account_creation), SignInFragment.newInstance(signUpPostData));
                    }
                });
    }

    private String showSignupError(Throwable e) {
        APIError apiError = null;
        String errorMessage = null;
        if (e instanceof HttpException) {
            apiError = Util.parseError(e);
            if (apiError != null)
                errorMessage = apiError.getError();
            if (!TextUtils.isEmpty(errorMessage)) {
                if (errorMessage.contains("users_user_username_key"))
                    return getString(R.string.duplicate_username);
                else if (errorMessage.contains("users_user_email_key"))
                    return getString(R.string.duplicate_email);
            }
        }

        return "Network error. Please try again.";
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

    public void setData(SignUpPostData data) {
        this.mSignupPostData = data;
    }
}
