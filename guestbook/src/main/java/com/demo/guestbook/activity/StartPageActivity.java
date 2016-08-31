package com.demo.guestbook.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.guestbook.R;
import com.demo.guestbook.remote.FirebaseEndPoint;
import com.demo.guestbook.util.TheApp;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


/**
 * A start page that loads the following:
 * 1. SharedPref data
 * 2. Remote data if it's available
 */
public class StartPageActivity extends AppCompatActivity
    implements FirebaseEndPoint.DataSnapshotListener {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Firebase.setAndroidContext(this);

        progressDialog = getVisibleProgressDialog();

        new FirebaseEndPoint().testGetAll(this);
    }

    private ProgressDialog getVisibleProgressDialog() {
        return ProgressDialog.show(StartPageActivity.this,
                TheApp.findString(R.string.app_name),
                "Loading app data ...",
                true, false);
    }


    @Override
    public void onDataChange(DataSnapshot snapshot) {

        /*
                        long childrenCount = snapshot.getChildrenCount();
                Iterable<DataSnapshot> dataSnapshots = snapshot.getChildren();
                for (DataSnapshot dataSnapshot : dataSnapshots) {

                    String key = dataSnapshot.getKey();

                    // String val = dataSnapshot.child("id").getValue().toString();
                    //Logr.d("val = " + val);

                    Iterable<DataSnapshot> dataSnapshotKids = dataSnapshot.getChildren();

                    for (DataSnapshot dataSnapshotKid : dataSnapshotKids) {

                        String childKey = dataSnapshotKid.getKey();
                        Object valObj = dataSnapshotKid.getValue();
                        if (valObj != null) {
                            String val = valObj.toString();
                            Logr.d("val = " + val);
                        }
                    }
                }

                Object dataSnapshot = snapshot.getValue();
                System.out.println(dataSnapshot);

         */

        progressDialog.dismiss();
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

        progressDialog.dismiss();
    }
}

