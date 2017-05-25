package com.awecode.muscn.views.signin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.signin.SignInData;
import com.awecode.muscn.model.http.signin.SignInSuccessData;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.prefs.Prefs;
import com.awecode.muscn.views.base.AppCompatBaseFragment;
import com.awecode.muscn.views.signup.SignUpActivity;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
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
 * Created by surensth on 5/24/17.
 */

public class SignInFragment extends AppCompatBaseFragment {
    private static final String TAG = "SignInFragment";

    @NotEmpty(messageResId = R.string.not_empty_error_text)
    @BindView(R.id.usernameEditText)
    EditText usernameEditText;

    @Length(messageResId = R.string.invalid_length, min = 8)
    @Password(messageResId = R.string.password_error_text, scheme = Password.Scheme.ALPHA_NUMERIC)
    @NotEmpty(messageResId = R.string.not_empty_error_text)
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    public SignInFragment() {
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_sign_in;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SignUpActivity) mContext).setToolbarTitle(getString(R.string.sign_in));

    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        signInRequest();

    }

    //sign in request
    private void signInRequest() {
        mActivity.showProgressDialog();
        final SignInData signInData = new SignInData();
        signInData.setUsername(usernameEditText.getText().toString());
        signInData.setPassword(passwordEditText.getText().toString());

        Observable<SignInSuccessData> call = mApiInterface.doSignIn(signInData);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SignInSuccessData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO parse error and show message
                        Log.i(TAG, "onError: "+new Gson().toJson(e).toString());
                        e.printStackTrace();
                        mActivity.closeProgressDialog();
                        mActivity.noInternetConnectionDialog(mContext);
                    }

                    @Override
                    public void onNext(SignInSuccessData signInSuccessData) {
                        mActivity.closeProgressDialog();
                        if (signInSuccessData.getToken() != null) {
                            Prefs.putBoolean(Constants.PREFS_LOGIN_STATUS, true);
                            Prefs.putString(Constants.PREFS_LOGIN_TOKEN, signInSuccessData.getToken().toString());
                            mActivity.successDialogAndCloseActivity(mContext, getString(R.string.success), getString(R.string.success_sign_in_text));
                        }
                    }
                });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        super.onValidationFailed(errors);
    }


    @OnClick(R.id.signInButton)
    public void onClick() {
        if (Util.checkInternetConnection(mContext))
            validateForm();
        else
            noInternetConnectionDialog();
    }
}
