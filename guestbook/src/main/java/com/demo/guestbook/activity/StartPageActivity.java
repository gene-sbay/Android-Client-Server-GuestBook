package com.demo.guestbook.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.guestbook.R;
import com.demo.guestbook.model.mapper.GuestEntryMapper;
import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.model.sharedprefs.AppStateDao;
import com.demo.guestbook.remote.FirebaseEndPoint;
import com.demo.guestbook.util.TheApp;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.List;


/**
 * A start page that loads the following:
 * 1. SharedPref data
 * 2. Remote data if it's available
 */
public class StartPageActivity extends AppCompatActivity
        implements FirebaseEndPoint.DataSnapshotListener {

    private ProgressDialog mProgressDialog;
    private FirebaseEndPoint mFirebaseEndPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Firebase.setAndroidContext(this);

        mProgressDialog = getVisibleProgressDialog();

        mFirebaseEndPoint = new FirebaseEndPoint();

        mFirebaseEndPoint.guestLogGetAll(this);
    }

    private ProgressDialog getVisibleProgressDialog() {
        return ProgressDialog.show(StartPageActivity.this,
                TheApp.findString(R.string.app_name),
                "Loading app data ...",
                true, false);
    }


    @Override
    public void onDataChange(DataSnapshot guestLogSnapshot) {

        GuestEntryMapper mapper = new GuestEntryMapper();

        List<GuestEntry> allServerGuestEntries = mapper.getAllGuestEntries(guestLogSnapshot);
        AppStateDao.getAppState().setServerGuestEntries(allServerGuestEntries);
        
        mProgressDialog.dismiss();
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

        mProgressDialog.dismiss();
    }
}

