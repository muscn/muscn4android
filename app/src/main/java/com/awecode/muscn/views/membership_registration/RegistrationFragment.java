package com.awecode.muscn.views.membership_registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.esewa.EsewaResponse;
import com.awecode.muscn.model.http.partners.PartnersResult;
import com.awecode.muscn.model.http.signin.SignInSuccessData;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.error.ErrorUtils;
import com.awecode.muscn.util.saripaar.EditTextNotEmptyRule;
import com.awecode.muscn.util.spinner.NoDefaultSpinner;
import com.awecode.muscn.views.base.AppCompatBaseFragment;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.esewa.android.sdk.payment.ESewaPaymentActivity;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    @Override
    public int getLayout() {
        return R.layout.fragment_registration;
    }


    private static final String TAG = RegistrationFragment.class.getSimpleName();

    @NotEmpty
    @BindView(R.id.fullnameEditText)
    EditText fullnameEditText;

    @NotEmpty
    @Length(min = 10, message = "Must be minimum of 10 digit.")
    @BindView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;

    @BindView(R.id.pickupLocationSpinner)
    NoDefaultSpinner pickupLocationSpinner;


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


    @BindView(R.id.bankDepositImageView)
    ImageView bankDepositImageView;

    @BindView(R.id.membershipFeeTextView)
    TextView membershipFeeTextView;

    @BindView(R.id.messageTextView)
    TextView messageTextView;

    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private ESewaConfiguration mEsewConfiguration;
    private static final int REQUEST_CODE_PAYMENT = 112;
    private static final int REQUEST_CODE_PICKER = 113;

    private String mBankDepositImgFilePath = "";
    private PaymentType mPaymentType;
    private PartnersResult mSelectedPickupLocation;
    private Boolean mEsewaPaymentStatus = false;
    private String mEswaResponse = "";


    public static RegistrationFragment newInstance() {
        RegistrationFragment fragment = new RegistrationFragment();
        return fragment;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pickupLocationSpinner.setPrompt("Select Pickup Location");
        setupForFormValidation();
        requestUserDetails();
    }

    /**
     * done button click in receipt number edittext
     *
     * @param actionId
     * @return
     */
    @OnEditorAction({R.id.receiptNoEditText, R.id.phoneNumberEditText})
    public boolean confirmPinDoneKeyboard(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            hideKeyboard();
            mValidator.validate();
            return true;
        }
        return false;
    }

    /**
     * request for user details- membership fee, payment status
     */
    private void requestUserDetails() {
        mActivity.showProgressDialog("Please Wait...");
        mApiInterface.getMembershipDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SignInSuccessData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.closeProgressDialog();
                        toast("Network error. Please try again.");
                        getFragmentManager().popBackStack();

                    }

                    @Override
                    public void onNext(SignInSuccessData response) {
                        mActivity.closeProgressDialog();
                        handleUserDetailRequestSuccess(response);

                    }
                });
    }

    private void handleUserDetailRequestSuccess(SignInSuccessData response) {

        updateUserDetail(response);

        //hide registration form if status is pending approval or member
        if (!Util.userNeedMemberRegistration()) {
            bottomLayout.setVisibility(View.GONE);
            messageTextView.setVisibility(View.VISIBLE);

            fullnameEditText.setFocusableInTouchMode(false);
            fullnameEditText.setFocusable(false);
            phoneNumberEditText.setFocusableInTouchMode(false);
            phoneNumberEditText.setFocusable(false);
        }


        //populate data in view
        populateUserDetailInView(response);
    }


    /**
     * populate membership fee, fullname and mobile number in textview
     *
     * @param data
     */
    private void populateUserDetailInView(SignInSuccessData data) {
        fullnameEditText.setText(data.getFullName());
        phoneNumberEditText.setText(data.getMobile());
        membershipFeeTextView.setText("Membership Fee: NRS " + data.getMembershipFee());

        //populate pickup locations

        populatePickupLocations(data.getPickupLocations());
    }

    private void populatePickupLocations(final List<PartnersResult> dataList) {

        List<String> dataStrList = new ArrayList<String>();
        for (PartnersResult partners : dataList)
            dataStrList.add(partners.getName() + ", " + partners.getShortAddress());

        ArrayAdapter spinnerAdapter = new ArrayAdapter(mContext, R.layout.spinner_row_selected,
                dataStrList);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_row);
        pickupLocationSpinner.setAdapter(spinnerAdapter);

        pickupLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedPickupLocation = dataList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @OnClick({R.id.esewaButton, R.id.receiptButton,
            R.id.bankDepositButton})
    public void BtnClicked(View view) {
        hideKeyboard();
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

    /**
     * open image picker view
     */
    private void openImagePicker() {
        ImagePicker.create(this)
                .returnAfterFirst(true) // set whether pick or camera action should return immediate result or not. For pick image only work on single mode
                .folderMode(true) // folder mode (false by default)
                .folderTitle("Choose Image") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .limit(1) // max images can be selected (99 by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .theme(R.style.ImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
                .enableLog(true) // disabling log
                .start(REQUEST_CODE_PICKER); // start image picker activity with request code
    }


    /**
     * Toggle bank deposit view, also hide receipt view
     */
    private void handleBankDepositBtnClicked() {
        //reset receipt layout
        if (receiptLayout.getVisibility() == View.VISIBLE) {
            receiptLayout.setVisibility(View.GONE);
            removeReceiptNoFieldValidation();
        }

        mPaymentType = PaymentType.BANK_DEPOSIT;

        openImagePicker();
    }

    /**
     * Hide bank deposit view, reset image view
     */
    private void resetBankDepositView() {
        bankDepositImageView.setVisibility(View.GONE);
        bankDepositImageView.setImageBitmap(null);
        bankDepositImageView.destroyDrawingCache();
        mBankDepositImgFilePath = "";
    }

    /**
     * Toggle receipt view, also hide bank deposit view
     */
    private void handleReceiptBtnClicked() {

        //reset receipt image
        resetBankDepositView();

        removeReceiptNoFieldValidation();

        if (receiptLayout.getVisibility() == View.VISIBLE) {
            //hide receipt layout
            receiptLayout.setVisibility(View.GONE);

            //hide submit button
            submitButton.setVisibility(View.GONE);
        } else {
            mPaymentType = PaymentType.RECEIPT;
            //show receipt layout
            receiptLayout.setVisibility(View.VISIBLE);

            //show keyboard in receipt number input field
            receiptNoEditText.requestFocus();
            Util.showKeyboard(receiptNoEditText);

            //show submit button
            submitButton.setVisibility(View.VISIBLE);

            //add not empty receipt input field validation
            mValidator.put(receiptNoEditText, new EditTextNotEmptyRule("Receipt Number cannot be empty."));

        }
    }

    /**
     * reset receipt input field - remove field validation
     */
    private void removeReceiptNoFieldValidation() {
        mValidator.removeRules(receiptNoEditText);
        receiptNoEditText.setError(null);
        receiptNoEditText.setText("");
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
        resetReceiptBankDeposit();

        mPaymentType = PaymentType.ESEWA;
        //start esewa payment
        startEsewaPayment();
    }

    /**
     * reset receipt and bank depsoit when esewa clicked
     */
    private void resetReceiptBankDeposit() {
        resetBankDepositView();
        receiptLayout.setVisibility(View.GONE);
        removeReceiptNoFieldValidation();
    }


    /**
     * start esewa payment
     */
    private void startEsewaPayment() {
        //config esewa with client id and secret key first
        setupEsewaConfig();

        SignInSuccessData data = getUserDetail();
        String productName = "Membership";
        if (data.getStatus().equalsIgnoreCase("Expired"))
            productName = "Renewal";

        String productId = "m_" + data.getUserId() + "_" + String.valueOf(System.currentTimeMillis());

        ESewaPayment eSewaPayment = new ESewaPayment(data.getMembershipFee(),
                productName,
                productId,
                Constants.ESEWA_CALLBACK_URL);
        Intent intent = new Intent(mContext, ESewaPaymentActivity.class);
        intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION, mEsewConfiguration);
        intent.putExtra(ESewaPayment.ESEWA_PAYMENT, eSewaPayment);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //from esewa
        if (requestCode == REQUEST_CODE_PAYMENT) {
            //payment success
            if (resultCode == RESULT_OK) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                handlePostEsewaPayment(s);
                //payment cancel by user
            } else if (resultCode == RESULT_CANCELED) {
                toast("It seems you cancelled the payment.");
                //invalid parameter passed to esewa sdk
            } else if (resultCode == ESewaPayment.RESULT_EXTRAS_INVALID) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Log.i("Proof   of   Payment", s);
            }
            //from image file picker
        } else if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            if (images.size() > 0) {
                //show submit button
                submitButton.setVisibility(View.VISIBLE);

                mBankDepositImgFilePath = images.get(0).getPath();
                //load selected image in image view
                loadImageInView(mBankDepositImgFilePath);
            }
        }
    }

    private void handlePostEsewaPayment(String response) {


        EsewaResponse esewaResponse = new Gson().fromJson(response, EsewaResponse.class);
        if (response != null
                && esewaResponse.getTransactionDetails() != null
                && esewaResponse.getTransactionDetails().getStatus().equalsIgnoreCase("COMPLETE")) {
            mEswaResponse = response;
            Log.i("Proof   of   Payment", response);
            toast(getString(R.string.esewa_payment_success_mg));
            mEsewaPaymentStatus = true;
            //show submit button
            submitButton.setVisibility(View.VISIBLE);


            mValidator.validate();
        }
    }

    /**
     * Load selected image in imageview
     *
     * @param path
     */
    private void loadImageInView(String path) {
        try {
            //show submit button
            submitButton.setVisibility(View.VISIBLE);
            //show imageview
            bankDepositImageView.setVisibility(View.VISIBLE);
            //load image in view
            Picasso.with(mContext)
                    .load(new File(path))
                    .resizeDimen(R.dimen.bank_deposit_img_width, R.dimen.bank_deposit_img_height)
                    .centerCrop()
                    .into(bankDepositImageView);

            if (getActivity() != null && RegistrationFragment.this.isVisible())
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {

                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
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
                .environment(ESewaConfiguration.ENVIRONMENT_PRODUCTION);
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


    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        if (mPaymentType == null) {
            toast("Please select any payment options first.");
            return;
        }
        hideKeyboard();
        switch (mPaymentType) {
            case ESEWA:
                requestMembershipRegistrationRequest();
                break;
            case RECEIPT:
                requestMembershipRegistrationRequest();
                break;
            case BANK_DEPOSIT:
                if (TextUtils.isEmpty(mBankDepositImgFilePath)) {
                    toast("Please pick image of bank receipt.");
                    openImagePicker();
                    return;
                }
                requestMembershipRegistrationRequest();

                break;
        }

    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        super.onValidationFailed(errors);
    }

    private void requestMembershipRegistrationRequest() {

        String fullName = fullnameEditText.getText().toString();
        String mobileNumber = phoneNumberEditText.getText().toString();
        if (TextUtils.isEmpty(fullName)) {
            fullnameEditText.setError(getString(R.string.this_field_cannot_be_empty));
            return;
        }
        if (TextUtils.isEmpty(mobileNumber)) {
            phoneNumberEditText.setError(getString(R.string.this_field_cannot_be_empty));
            return;
        }

        if (mSelectedPickupLocation == null) {
            toast("Please select a pickup location.");
            return;
        }

        mActivity.showProgressDialog("Please wait...");

        MultipartBody.Part imageFile = null;
        Map<String, RequestBody> map = new HashMap<>();
        //add fullname and mobile number to request
        map.put("full_name", RequestBody.create(MediaType.parse("text/plain"), fullName));
        map.put("mobile", RequestBody.create(MediaType.parse("text/plain"), mobileNumber));
        map.put("pickup_location", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mSelectedPickupLocation.getId())));


        if (mPaymentType == PaymentType.BANK_DEPOSIT) {
            File file = new File(mBankDepositImgFilePath);
            if (file == null || !file.exists()) {
                toast("Please choose bank deposit image.");
                openImagePicker();
                return;
            }
            //send deposit status true
            map.put("deposit", RequestBody.create(MediaType.parse("text/plain"), "true"));
            //add bank deposit image to request
            // map.put("voucher_image", RequestBody.create(MediaType.parse("multipart/form-data"), file));

            imageFile = MultipartBody.Part.createFormData("voucher_image", file.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));

        } else if (mPaymentType == PaymentType.RECEIPT) {
            //send deposit status true
            map.put("receipt", RequestBody.create(MediaType.parse("text/plain"), "true"));
            map.put("receipt_no", RequestBody.create(MediaType.parse("text/plain"), receiptNoEditText.getText().toString()));
        } else if (mPaymentType == PaymentType.ESEWA) {
            if (!mEsewaPaymentStatus) {
                toast("Please click esewa to start payment.");
                return;
            }
            map.put("esewa", RequestBody.create(MediaType.parse("text/plain"), "true"));
            map.put("esewa_response", RequestBody.create(MediaType.parse("text/plain"), mEswaResponse));
        }

        try {
            Observable<SignInSuccessData> call = mApiInterface.postMembershipData(map, imageFile);
            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<SignInSuccessData>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            mActivity.closeProgressDialog();
                            mActivity.showDialog(ErrorUtils.parseError(e));
                        }

                        @Override
                        public void onNext(SignInSuccessData response) {
                            mActivity.closeProgressDialog();
                            if (response != null) {
                                updateUserDetail(response);
                                mActivity.successDialogAndCloseActivity(mContext,
                                        getString(R.string.membership_registration_success_msg));
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public enum PaymentType {
        ESEWA,
        RECEIPT,
        BANK_DEPOSIT
    }
}
