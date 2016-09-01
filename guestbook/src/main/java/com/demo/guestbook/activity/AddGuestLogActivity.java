package com.demo.guestbook.activity;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
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
import com.demo.guestbook.model.validator.GuestEntryValidator;
import com.demo.guestbook.model.view.GuestEntryViewModel;
import com.demo.guestbook.remote.FirebaseEndPoint;
import com.demo.guestbook.ui.dialog.DeleteGuestEntryDialog;
import com.demo.guestbook.ui.util.DatePickerHelper;
import com.demo.guestbook.ui.util.DialogUtil;
import com.demo.guestbook.ui.util.InputUtil;
import com.demo.guestbook.util.Const;
import com.demo.guestbook.util.TheApp;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Since there are listeners from the TabsActivity for server updates,
 * making a server update here will cause the UI to go back to the Tabs view
 */

public class AddGuestLogActivity extends BaseUpNavigationAppCompatActivity
    implements FirebaseEndPoint.SetValueListener {

    private GuestEntryViewModel mGuestEntryViewModel;
    private ActivityAddGuestLogBinding mActivityAddGuestLogBinding;
    private GuestEntry mGuestEntry;
    private DatePickerHelper mDatePickerHelper;
    private String mEditNodeId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mEditNodeId = intent.getStringExtra(Const.Extra.GUEST_ENTRY_ID);

        // Default theme should be set before content view is added
        FlatUI.setDefaultTheme(Const.APP_THEME);

        Firebase.setAndroidContext(this);

        setEditor();

        initLayout();
        initSubmitHandlers();

        // Will call run method once view is drawn
        mActivityAddGuestLogBinding.deleteBtn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isInEditMode()) {
                    InputUtil.hideKeyboard(mActivityAddGuestLogBinding.edittextBirthday);
                }
            }
        });

    }



    private boolean isInEditMode() {
        return mEditNodeId != null;
    };
    
    private void setEditor() {

        String submitText = TheApp.findString(R.string.submit);
        int deleteButtonVisibility = View.GONE;

        mActivityAddGuestLogBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_guest_log);

        if (isInEditMode() ) {

            submitText = TheApp.findString(R.string.update);
            deleteButtonVisibility = View.VISIBLE;

            GuestEntryMapper mapper = new GuestEntryMapper();
            final GuestEntry guestEntry = AppStateDao.getAppState().getLocalGuestEntryById(mEditNodeId);
            mGuestEntryViewModel = mapper.map(guestEntry);

            mActivityAddGuestLogBinding.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DeleteGuestEntryDialog(AddGuestLogActivity.this, guestEntry).show();
                }
            });
        }
        else {
            mGuestEntryViewModel = new GuestEntryViewModel();
        }

        mActivityAddGuestLogBinding.submitBtn.setText(submitText);
        mActivityAddGuestLogBinding.deleteBtn.setVisibility(deleteButtonVisibility);

        // No need to add a setContentView(), we will use DataBinding to set the contentView
        mActivityAddGuestLogBinding.setGuestEntryViewModel(mGuestEntryViewModel);
    }

    private void initLayout() {
        // converts the default values to dp to be compatible with different screen sizes
        FlatUI.initDefaultValues(this);

        setupActionBar();

        mDatePickerHelper = new DatePickerHelper(this, R.id.edittext_birthday);
        mDatePickerHelper.setCurrentDateOnView();
        mDatePickerHelper.addListenerOnButton();
    }

    private void initSubmitHandlers() {

        Button submitButton = (Button) findViewById(R.id.submit_btn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputUtil.hideKeyboard(v);
                updateFirebase();
            }
        });

        mActivityAddGuestLogBinding.edittextZipcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputUtil.hideKeyboard(v);
                    updateFirebase();
                    return true;
                }

                return false;
            }
        });
    }

    private void updateFirebase() {

        GuestEntryMapper guestEntryMapper = new GuestEntryMapper();
        mGuestEntry = guestEntryMapper.map(mGuestEntryViewModel);

        GuestEntryValidator guestEntryValidator = new GuestEntryValidator(mGuestEntry);
        boolean isValid = guestEntryValidator.isValid();
        if ( ! isValid ) {
            String title = "Validation input error";
            String errorMessage = guestEntryValidator.getErrorMessageString();
            new DialogUtil().getErrorAlertDialog(AddGuestLogActivity.this, title, errorMessage).show();

            // Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        // using a known Id with the .save() method, will serve as an update in Firebase
        if (isInEditMode()) {
            mGuestEntry.setId(mEditNodeId);
        }

        FirebaseEndPoint firebaseEndPoint = new FirebaseEndPoint();
        firebaseEndPoint.connect();
        firebaseEndPoint.save(mGuestEntry, this);
    }

    @Override
    public void onFirebaseSuccess() {
        String message = getString(R.string.success_message, mGuestEntry.getFirstName());
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        AppState appState = AppStateDao.getAppState();

        if (isInEditMode()) {

            appState.updateLocalGuestEntry(mGuestEntry);
        }
        else {

            appState.addLocalGuestEntry(mGuestEntry);
        }

        AppStateDao.sharedPrefs_updateAppState(appState);
    }

    @Override
    public void onFirebaseError(FirebaseError firebaseError) {

        String title = "Firebase Error";
        String message = getString(R.string.add_guest_entry_error, firebaseError.getMessage());
        new DialogUtil().getErrorAlertDialog(AddGuestLogActivity.this, title, message).show();
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
