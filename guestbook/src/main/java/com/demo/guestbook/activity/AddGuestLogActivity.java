package com.demo.guestbook.activity;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.demo.guestbook.R;
import com.demo.guestbook.activity.base.BaseUpNavigationAppCompatActivity;
import com.demo.guestbook.databinding.ActivityAddGuestLogBinding;
import com.demo.guestbook.model.mapper.GuestEntryMapper;
import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.model.sharedprefs.AppState;
import com.demo.guestbook.model.sharedprefs.AppStateDao;
import com.demo.guestbook.model.view.GuestEntryViewModel;
import com.demo.guestbook.remote.FirebaseEndPoint;
import com.demo.guestbook.ui.util.DatePickerHelper;
import com.demo.guestbook.ui.util.DialogUtil;
import com.demo.guestbook.util.Const;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Launcher Activity displaying two EditTexts. Both EditTexts point to same class object. Since we
 * have used 2 way authentication, once we update the text in either of EditText, it will automatically
 * be reflected in another EditText.
 */

public class AddGuestLogActivity extends BaseUpNavigationAppCompatActivity
    implements FirebaseEndPoint.Listener {

    private GuestEntryViewModel mGuestEntryViewModel;
    private ActivityAddGuestLogBinding mActivityAddGuestLogBinding;
    private GuestEntry mGuestEntry;
    private DatePickerHelper mDatePickerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Default theme should be set before content view is added
        FlatUI.setDefaultTheme(Const.APP_THEME);

        // No need to add a setContentView(), we will use DataBinding to set the contentView
        mActivityAddGuestLogBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_guest_log);
        mGuestEntryViewModel = new GuestEntryViewModel();
        mActivityAddGuestLogBinding.setGuestEntryViewModel(mGuestEntryViewModel);

        Firebase.setAndroidContext(this);

        initLayout();
        initSubmitHandlers();

        new FirebaseEndPoint().testGetAll();
    }

    private void initLayout() {
        // converts the default values to dp to be compatible with different screen sizes
        FlatUI.initDefaultValues(this);

        setupActionBar();

        mDatePickerHelper = new DatePickerHelper(this, R.id.birthday);
        mDatePickerHelper.setCurrentDateOnView();
        mDatePickerHelper.addListenerOnButton();
    }

    private void initSubmitHandlers() {

        Button submitButton = (Button) findViewById(R.id.submit_btn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                updateFirebase();
            }
        });

        mActivityAddGuestLogBinding.edittextZipcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    updateFirebase();
                    return true;
                }

                return false;
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void updateFirebase() {

        GuestEntryMapper guestEntryMapper = new GuestEntryMapper();
        mGuestEntry = guestEntryMapper.map(mGuestEntryViewModel);

        FirebaseEndPoint firebaseEndPoint = new FirebaseEndPoint();
        firebaseEndPoint.connect();
        firebaseEndPoint.save(mGuestEntry, this);
    }

    @Override
    public void onFirebaseSuccess() {
        String message = getString(R.string.success_message, mGuestEntry.getFirstName());
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        AppState appState = AppStateDao.getAppState();
        appState.addLocalGuestEntry(mGuestEntry);
        AppStateDao.sharedPrefs_updateAppState(appState);
    }

    @Override
    public void onFirebaseError(FirebaseError firebaseError) {

        String message =  getString(R.string.add_guest_entry_error, firebaseError.getMessage());
        new DialogUtil().getErrorAlertDialog(AddGuestLogActivity.this, message);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DatePickerHelper.DATE_DIALOG_ID:
                // set date picker as current date
                //return new DatePickerDialog(this, datePickerListener, year, month, day);
                return mDatePickerHelper.getDatePickerDialog();
        }
        return null;
    }
}
