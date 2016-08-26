package com.demo.guestbook.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.demo.guestbook.R;
import com.demo.guestbook.databinding.ActivityGuestBookBinding;
import com.demo.guestbook.model.mapper.GuestEntryMapper;
import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.model.view.GuestEntryViewModel;
import com.demo.guestbook.remote.FirebaseEndPoint;
import com.demo.guestbook.util.Const;
import com.firebase.client.Firebase;

/**
 * Launcher Activity displaying two EditTexts. Both EditTexts point to same class object. Since we
 * have used 2 way authentication, once we update the text in either of EditText, it will automatically
 * be reflected in another EditText.
 */

public class GuestBookActivity extends AppCompatActivity {

    private GuestEntryViewModel mGuestEntryViewModel;
    private ActivityGuestBookBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No need to add a setContentView(), we will use DataBinding to set the contentView
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_guest_book);
        mGuestEntryViewModel = new GuestEntryViewModel();
        mActivityMainBinding.setGuestEntryViewModel(mGuestEntryViewModel);

        Firebase.setAndroidContext(this);

        initFlatUi();
        initSubmitHandlers();
    }

    private void initFlatUi() {
        // converts the default values to dp to be compatible with different screen sizes
        FlatUI.initDefaultValues(this);

        // Default theme should be set before content view is added
        FlatUI.setDefaultTheme(Const.APP_THEME);
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
        GuestEntry guestEntry = guestEntryMapper.map(mGuestEntryViewModel);

        FirebaseEndPoint firebaseEndPoint = new FirebaseEndPoint();
        firebaseEndPoint.connect();
        firebaseEndPoint.save(guestEntry);

        String message = getString(R.string.success_message, guestEntry.getFirstName());

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
