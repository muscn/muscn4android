package com.awecode.muscn.views.signin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.api_error.APIError;
import com.awecode.muscn.model.http.signin.SignInData;
import com.awecode.muscn.model.http.signin.SignInSuccessData;
import com.awecode.muscn.model.http.signup.SignUpPostData;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.prefs.PrefsHelper;
import com.awecode.muscn.views.base.AppCompatBaseFragment;
import com.awecode.muscn.views.signup.SignUpActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

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
    SignUpPostData mSignUpResponse;

    @BindView(R.id.fbLoginButton)
    LoginButton fbLoginButton;
    private CallbackManager mCallbackManager;

    public SignInFragment() {
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }

    public static SignInFragment newInstance(SignUpPostData signUpResponse) {
        SignInFragment fragment = new SignInFragment();
        fragment.setData(signUpResponse);
        return fragment;
    }

    private void setData(SignUpPostData signUpPostData) {
        this.mSignUpResponse = signUpPostData;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_sign_in;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SignUpActivity) mContext).setToolbarTitle(getString(R.string.sign_in));
        if (mSignUpResponse != null && mSignUpResponse.getEmail() != null) {
            emailEditText.setText(mSignUpResponse.getEmail());
        }

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

        fbLoginConfigure();
    }

    /**
     * Login with facebook
     */
    private void fbLoginConfigure() {
        mCallbackManager = CallbackManager.Factory.create();

        fbLoginButton.setReadPermissions("email");
        fbLoginButton.setFragment(this);

        // Callback registration
        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v(TAG, "login success");
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject,
                                                    GraphResponse response) {
                                JSONObject json = response.getJSONObject();
                                try {
                                    if (json != null) {
                                        Log.d(TAG, json.toString());
                                        String text = "<b>Name :</b> " + json.getString("name") + "<br><br><b>Email :</b> " + json.getString("email") + "<br><br><b>Profile link :</b> " + json.getString("link") + "<br><br><b>Id :</b> " + json.getString("id");

                                        Log.v(TAG, text);
                                        Log.v(TAG, "email from json " + json.getString("email"));

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//                                Profile profile = Profile.getCurrentProfile();
//                                profile.getProfilePictureUri(20,20);

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.v("test", "login cancel");

            }

            @Override
            public void onError(FacebookException exception) {
                Log.v("test", "login error");

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
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
        signInData.setUsername(emailEditText.getText().toString());
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
                            PrefsHelper.saveLoginStatus(true);
                            PrefsHelper.saveLoginToken(signInSuccessData.getToken().toString());
                            PrefsHelper.saveLoginResponse(signInSuccessData);
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
                if (apiError.getNon_field_errors() != null) {
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
