package com.awecode.muscn.views.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.awecode.muscn.MyApplication;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by surensth on 5/23/17.
 */

public abstract class AppCompatBaseFragment extends Fragment implements Validator.ValidationListener{
    public MuscnApiInterface mApiInterface;
    public Context mContext;
    public AppCompatBaseActivity mActivity;
    public FragmentManager mFragmentManager;
    protected Validator mValidator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mActivity = (AppCompatBaseActivity) getActivity();
        mFragmentManager = mActivity.getSupportFragmentManager();

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApiInterface = getApiInterface();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public abstract int getLayout();

    public void toast(String message) {
        Util.toast(mContext, message);
    }

    protected int getDimen(int id) {
        return (int) mContext.getResources().getDimension(id);
    }

    public MuscnApiInterface getApiInterface() {
        return mApiInterface = ((MyApplication) getActivity().getApplication()).getApiInterface();
    }

    protected void openFragment(Fragment fragment) {
        ((BaseActivity) mContext).openFragment(fragment);
    }

    public void noInternetConnectionDialog() {
        ((AppCompatBaseActivity) mContext).noInternetConnectionDialog(mContext);
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());
            // Display error messages ;)
            if (view instanceof EditText)
                ((EditText) view).setError(message);
            else
                toast(message);

        }
    }

    public void validateForm() {
        if (mValidator == null) {
            mValidator = new Validator(this);
            mValidator.setValidationListener(this);
        }
        mValidator.validate();
    }

}
