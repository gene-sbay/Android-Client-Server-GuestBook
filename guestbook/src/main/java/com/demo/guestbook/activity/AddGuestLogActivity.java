package com.demo.guestbook.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.demo.guestbook.ui.util.DialogUtil;
import com.demo.guestbook.util.Const;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;

/**
 * Launcher Activity displaying two EditTexts. Both EditTexts point to same class object. Since we
 * have used 2 way authentication, once we update the text in either of EditText, it will automatically
 * be reflected in another EditText.
 */

public class AddGuestLogActivity extends BaseUpNavigationAppCompatActivity
    implements FirebaseEndPoint.Listener {

    private GuestEntryViewModel mGuestEntryViewModel;
    private ActivityAddGuestLogBinding mActivityMainBinding;
    private GuestEntry mGuestEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Default theme should be set before content view is added
        FlatUI.setDefaultTheme(Const.APP_THEME);

        // No need to add a setContentView(), we will use DataBinding to set the contentView
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_guest_log);
        mGuestEntryViewModel = new GuestEntryViewModel();
        mActivityMainBinding.setGuestEntryViewModel(mGuestEntryViewModel);

        int numChars = 15;
        String placeHolder = "";
        for (int i = 0; i < numChars; i++) {
            placeHolder += " ";
        }
        mActivityMainBinding.edittextZipcode.setText(placeHolder);
        Firebase.setAndroidContext(this);

        initFlatUi();
        initSubmitHandlers();

        setupActionBar();

        tvDisplayDate = (TextView) findViewById(R.id.tvDate);
        //dpResult = (DatePicker) findViewById(R.id.dpResult);


        setCurrentDateOnView();
        addListenerOnButton();

        Firebase guestLogRef = new com.firebase.client.Firebase("https://guest-bookly.firebaseio.com/guest_log");
        guestLogRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long childrenCount = snapshot.getChildrenCount();
                Iterable<DataSnapshot> dataSnapshots = snapshot.getChildren();
                Object dataSnapshot = snapshot.getValue();
                System.out.println(dataSnapshot);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private void initFlatUi() {
        // converts the default values to dp to be compatible with different screen sizes
        FlatUI.initDefaultValues(this);

    }

    private void initSubmitHandlers() {

        Button submitButton = (Button) findViewById(R.id.submit_btn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFirebase();
            }
        });

        mActivityMainBinding.edittextZipcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

    // // //


    private TextView tvDisplayDate;
    //private DatePicker dpResult;
    private Button btnChangeDate;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;

    // display current date
    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        //tvDisplayDate.setText(new StringBuilder()
        //        // Month is 0 based, just add 1
        //        .append(month + 1).append("-").append(day).append("-")
        //        .append(year).append(" "));

        // set current date into datepicker
        //dpResult.init(year, month, day, null);

    }

    public void addListenerOnButton() {

        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);

        btnChangeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            tvDisplayDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            //dpResult.init(year, month, day, null);

        }
    };
}
