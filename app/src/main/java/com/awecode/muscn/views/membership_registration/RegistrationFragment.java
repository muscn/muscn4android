package com.awecode.muscn.views.membership_registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.api_error.APIError;
import com.awecode.muscn.model.http.signup.SignUpPostData;
import com.awecode.muscn.model.membership.MembershipResponse;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.prefs.PrefsHelper;
import com.awecode.muscn.util.saripaar.EditTextNotEmptyRule;
import com.awecode.muscn.views.base.AppCompatBaseFragment;
import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.esewa.android.sdk.payment.ESewaPaymentActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by munnadroid on 7/18/17.
 */

public class RegistrationFragment extends AppCompatBaseFragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = RegistrationFragment.class.getSimpleName();

    @NotEmpty
    @BindView(R.id.fullnameEditText)
    EditText fullnameEditText;

    @NotEmpty
    @Length(min = 10, message = "Must be minimum of 10 digit.")
    @BindView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;


    @BindView(R.id.dateOfBirthEditText)
    EditText dateOfBirthEditText;

    @BindView(R.id.addressEditText)
    EditText addressEditText;

    @BindView(R.id.receiptLayout)
    LinearLayout receiptLayout;

    @BindView(R.id.receiptNoEditText)
    EditText receiptNoEditText;

    @BindView(R.id.submitButton)
    Button submitButton;

    private Boolean mIsInputFieldFilled = false;
    private ESewaConfiguration mEsewConfiguration;
    private static final int REQUEST_CODE_PAYMENT = 112;


    public static RegistrationFragment newInstance() {
        RegistrationFragment fragment = new RegistrationFragment();
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_registration;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupForFormValidation();
    }

    @OnClick({R.id.esewaButton, R.id.receiptButton, R.id.bankDepositButton})
    public void paymentOptionsBtnClicked(View view) {
        switch (view.getId()) {
            case R.id.esewaButton:
                handleEsewaBtnClicked();
                break;
            case R.id.receiptButton:
                handleReceiptBtnClicked();
                break;
            case R.id.bankDepositButton:
                handleBankDepositBtnClicked();
                break;
        }

    }

    private void handleBankDepositBtnClicked() {
        toast("Feature coming soon.");
    }

    private void handleReceiptBtnClicked() {

        //reset receipt input field - remove field validation
        mValidator.removeRules(receiptNoEditText);
        receiptNoEditText.setError(null);
        
        if (receiptLayout.getVisibility() == View.VISIBLE) {
            //hide receipt layout
            receiptLayout.setVisibility(View.GONE);

            //hide submit button
            submitButton.setVisibility(View.GONE);
        } else {
            //show receipt layout
            receiptLayout.setVisibility(View.VISIBLE);

            //show submit button
            submitButton.setVisibility(View.VISIBLE);

            //add not empty receipt input field validation
            mValidator.put(receiptNoEditText, new EditTextNotEmptyRule("Receipt Number cannot be empty."));

        }
    }

    /**
     * handle esewa button click
     */
    private void handleEsewaBtnClicked() {
        //check internet connection
        if (!Util.checkInternetConnection(mContext)) {
            noInternetConnectionDialog();
            return;
        }

        if (!mIsInputFieldFilled) {
            toast("Please enter full name and mobile number first.");
            return;
        }

        //start esewa payment
        startEsewaPayment();
    }


    /**
     * start esewa payment
     */
    private void startEsewaPayment() {
        //config esewa with client id and secret key first
        setupEsewaConfig();

        ESewaPayment eSewaPayment = new ESewaPayment("100", "Membership Registration", PrefsHelper.getLoginResponse().getEmail(), "");
        Intent intent = new Intent(mContext, ESewaPaymentActivity.class);
        intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION, mEsewConfiguration);
        intent.putExtra(ESewaPayment.ESEWA_PAYMENT, eSewaPayment);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            //payment success
            if (resultCode == RESULT_OK) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Log.i("Proof   of   Payment", s);
                toast("SUCCESSFUL   PAYMENT");
                //payment cancel by user
            } else if (resultCode == RESULT_CANCELED) {
                toast("It seems you cancelled the payment.");
                //invalid parameter passed to esewa sdk
            } else if (resultCode == ESewaPayment.RESULT_EXTRAS_INVALID) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Log.i("Proof   of   Payment", s);
            }
        }
    }


    /**
     * Config esewa with client ID and secret key
     */
    private void setupEsewaConfig() {
        if (mEsewConfiguration != null)
            return;
        mEsewConfiguration = new ESewaConfiguration().clientId(Constants.ESEWA_CLIENT_ID)
                .secretKey(Constants.ESEWA_SECRET_KEY)
                .environment(ESewaConfiguration.ENVIRONMENT_TEST);
    }

    /**
     * set validation mode
     */
    private void setupForFormValidation() {
        //set form validation mode
        mValidator.setValidationMode(Validator.Mode.BURST);
    }


    /**
     * DOB and submit button clicked
     *
     * @param view
     */
    @OnClick({R.id.dateOfBirthEditText, R.id.submitButton})
    public void dobFieldSubmitBtnClicked(View view) {
        switch (view.getId()) {
            case R.id.dateOfBirthEditText:
                showDOBDatePicker();
                break;
            case R.id.submitButton:
                mValidator.validate();
                break;
        }
    }

    /**
     * show date of birth datepicker
     */
    private void showDOBDatePicker() {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                1990,
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateOfBirthEditText.setText(year + "-" +
                Util.twoDigitFormat(monthOfYear + 1) + "-" +
                Util.twoDigitFormat(dayOfMonth));
    }

    @OnEditorAction(R.id.submitButton)
    public boolean addressDoneBtnClicked(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            mValidator.validate();
            return true;
        }
        return false;
    }

    @OnTextChanged({R.id.fullnameEditText, R.id.phoneNumberEditText})
    public void inputFieldTextChanged(Editable editable) {
        mValidator.validate();

    }


    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        mIsInputFieldFilled = true;

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        super.onValidationFailed(errors);
        mIsInputFieldFilled = false;
    }

    private void requestMembershipRegistrationRequest() {
        mActivity.showProgressDialog("Please wait...");
        SignUpPostData signUpPostData = new SignUpPostData();
        signUpPostData.setFullName(fullnameEditText.getText().toString());
        signUpPostData.setMobile(phoneNumberEditText.getText().toString());

//        signUpPostData.setDateOfBirth(dateOfBirthEditText.getText().toString());
//        signUpPostData.setAddress(addressEditText.getText().toString());

        Observable<MembershipResponse> call = mApiInterface.postMembershipData(signUpPostData);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MembershipResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.closeProgressDialog();
                        APIError apiError = null;
                        String errorMessage = null;
                        if (e instanceof HttpException) {
                            apiError = Util.parseError(e);
                            if (apiError != null)
                                errorMessage = apiError.getError();
                            if (!TextUtils.isEmpty(errorMessage)) {
                                if (errorMessage.contains("users_user_username_key"))
                                    showErrorDialog(getString(R.string.duplicate_username));
                                else if (errorMessage.contains("users_user_email_key"))
                                    showErrorDialog(getString(R.string.duplicate_email));
                            }
                        } else
                            noInternetConnectionDialog();
                    }

                    @Override
                    public void onNext(MembershipResponse signUpPostData) {
                        mActivity.closeProgressDialog();
                        if (signUpPostData != null)
                            mActivity.successDialogAndCloseActivity(mContext, "Registration success. Thank you!");
                    }
                });
    }

}
