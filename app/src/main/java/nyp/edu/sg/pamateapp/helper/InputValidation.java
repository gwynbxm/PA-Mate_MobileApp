/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.helper;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by GBXM on 19/12/2017.
 */

public class InputValidation {
    private Context context;

    public InputValidation(Context context) {
        this.context = context;
    }

    public boolean editTextFilled(EditText editText, String message) {
        String value = editText.getText().toString().trim();

        if (TextUtils.isEmpty(value)) {
            editText.setError(message);
            hideKeyboard(editText);
            return false;
        }
        return true;
    }

    public boolean editTextMatches(EditText editText1, EditText editText2) {
        String value1 = editText1.getText().toString().trim();
        String value2 = editText2.getText().toString().trim();

        if (!value1.contentEquals(value2)) {
            hideKeyboard(editText2);
            return false;
        }
        return true;
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
