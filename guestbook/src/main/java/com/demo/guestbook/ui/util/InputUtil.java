package com.demo.guestbook.ui.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.demo.guestbook.util.TheApp;

public class InputUtil {
    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) TheApp.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
