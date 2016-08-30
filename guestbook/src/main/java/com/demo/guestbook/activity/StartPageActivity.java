package com.demo.guestbook.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.guestbook.R;
import com.demo.guestbook.remote.FirebaseEndPoint;
import com.demo.guestbook.ui.util.DialogUtil;
import com.demo.guestbook.util.TheApp;
import com.firebase.client.Firebase;


/**
 * A start page that loads the following:
 * 1. SharedPref data
 * 2. Remote data if it's available
 */
public class StartPageActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Firebase.setAndroidContext(this);

        new StartupBackgroundTask().execute();
    }

    private ProgressDialog getVisibleProgressDialog() {
        return ProgressDialog.show(StartPageActivity.this,
                            TheApp.findString(R.string.app_name),
                            "Loading app data ...",
                            true, false);
    }

    class StartupBackgroundTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            mProgressDialog = getVisibleProgressDialog();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result == false) {
                DialogUtil.getErrorAlertDialog(StartPageActivity.this, "Error on Start Up").show();
                return;
            }

            mProgressDialog.dismiss();

            Intent intent = new Intent(StartPageActivity.this, GuestBookTabsActivity.class);
            startActivity(intent);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            new FirebaseEndPoint().testGetAll();

            // Place holder sleep . Will replace with Firebase service look up

            try {
                Thread.sleep(2000);
            }
            catch (Exception e) {

            }
            return true;
        }
    }

}

