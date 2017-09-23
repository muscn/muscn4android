package com.awecode.muscn.util.saripaar;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.awecode.muscn.R;
import com.mobsandgeeks.saripaar.QuickRule;

/**
 * Created by munnadroid on 9/23/17.
 */

public class EditTextNotEmptyRule extends QuickRule<EditText> {

    public EditTextNotEmptyRule() {
    }

    private String mMessage;

    public EditTextNotEmptyRule(String message) {
        this.mMessage = message;
    }

    @Override
    public boolean isValid(EditText editText) {
        String str = editText.getText().toString().trim();
        return !TextUtils.isEmpty(str);
    }

    @Override
    public String getMessage(Context context) {
        if (TextUtils.isEmpty(mMessage))
            mMessage = context.getString(R.string.field_cannot_be_empty);
        return mMessage;
    }
}
