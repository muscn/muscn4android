package com.awecode.muscn.views.membership_registration;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;

import com.awecode.muscn.R;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.base.AppCompatBaseFragment;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by munnadroid on 7/18/17.
 */

public class RegistrationFragment extends AppCompatBaseFragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = RegistrationFragment.class.getSimpleName();

    @NotEmpty
    @BindView(R.id.fullnameEditText)
    EditText fullnameEditText;

    @NotEmpty
    @BindView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;

    @NotEmpty
    @BindView(R.id.dateOfBirthEditText)
    EditText dateOfBirthEditText;

    @NotEmpty
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

    @OnClick(R.id.dateOfBirthEditText)
    public void dateOfBirthClicked(View view) {
        showDOBDatePicker();
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
}
