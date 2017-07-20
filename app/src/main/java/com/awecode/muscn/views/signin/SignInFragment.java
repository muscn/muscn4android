package com.awecode.muscn.views.signin;

import android.text.TextUtils;
import android.widget.EditText;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.api_error.APIError;
import com.awecode.muscn.model.http.signin.SignInData;
import com.awecode.muscn.model.http.signin.SignInSuccessData;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.prefs.PrefsHelper;
import com.awecode.muscn.views.base.AppCompatBaseFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
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
 * Created by surensth on 5/24/17.
 */

public class SignInFragment extends AppCompatBaseFragment {
    private static final String TAG = "SignInFragment";

    @NotEmpty(messageResId = R.string.not_empty_error_text)
    @Email
    @BindView(R.id.emailEditText)
    EditText emailEditText;

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
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        signInRequest();

    }

    //    sign in request
    private void signInRequest() {
        mActivity.showProgressDialog("Please wait...");
        final SignInData signInData = new SignInData();
        signInData.setEmail(emailEditText.getText().toString());
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
                        mActivity.closeProgressDialog();
                        handleSignInRequestError(e);

                    }

                    @Override
                    public void onNext(SignInSuccessData signInSuccessData) {
                        mActivity.closeProgressDialog();
                        if (signInSuccessData.getToken() != null) {
                            PrefsHelper.getLoginStatus();
                            PrefsHelper.saveLoginToken(signInSuccessData.getToken().toString());
                            mActivity.successDialogAndCloseActivity(mContext, getString(R.string.success_sign_in_text));
                        }
                    }
                });
    }

    private void handleSignInRequestError(Throwable e) {
        APIError apiError = null;
        String errorMessage = null;
        if (e instanceof HttpException) {
            apiError = Util.parseError(e);
            if (apiError != null) {
                if (apiError.getNon_field_errors() != null
                        && !TextUtils.isEmpty(errorMessage)) {
                    errorMessage = apiError.getNon_field_errors().get(0);
                    if (errorMessage.contains(getString(R.string.login_error)))
                        showErrorDialog(getString(R.string.username_password_incorrect_text));
                } else if (apiError.getDetail() != null &&
                        !TextUtils.isEmpty(apiError.getDetail()))
                    showErrorDialog(apiError.getDetail());
            }
        } else
            noInternetConnectionDialog();
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
