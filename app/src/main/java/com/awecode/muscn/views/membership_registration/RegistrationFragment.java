package com.awecode.muscn.views.membership_registration;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.api_error.APIError;
import com.awecode.muscn.model.http.signup.SignUpPostData;
import com.awecode.muscn.model.membership.MembershipResponse;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.base.AppCompatBaseFragment;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by munnadroid on 7/18/17.
 */

public class RegistrationFragment extends AppCompatBaseFragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = RegistrationFragment.class.getSimpleName();

    @NotEmpty
    @Order(1)
    @BindView(R.id.fullnameEditText)
    EditText fullnameEditText;

    @NotEmpty
    @Order(2)
    @BindView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;


    @BindView(R.id.dateOfBirthEditText)
    EditText dateOfBirthEditText;

    @BindView(R.id.addressEditText)
    EditText addressEditText;

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
        mValidator.setValidationMode(Validator.Mode.IMMEDIATE);
    }

    @OnClick({R.id.dateOfBirthEditText, R.id.submitButton})
    public void dateOfBirthClicked(View view) {
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

    @OnFocusChange({R.id.fullnameEditText, R.id.dateOfBirthEditText,
            R.id.phoneNumberEditText, R.id.addressEditText})
    public void editTextFocusChanged(View view, boolean hasFocus) {
        if (hasFocus)
            mValidator.validateBefore(view);
    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        membershipRegistrationRequest();
    }

    private void membershipRegistrationRequest() {
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
