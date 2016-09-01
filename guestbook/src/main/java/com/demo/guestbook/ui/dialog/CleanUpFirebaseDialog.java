package com.demo.guestbook.ui.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.demo.guestbook.model.mapper.GuestEntryMapper;
import com.demo.guestbook.remote.FirebaseEndPoint;
import com.demo.guestbook.util.Logr;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.List;

public class CleanUpFirebaseDialog {

    private FirebaseEndPoint mFirebaseEndPoint;

    private Activity mActivity;

    public CleanUpFirebaseDialog(Activity activity) {
        mActivity = activity;
        mFirebaseEndPoint = new FirebaseEndPoint();
    }

    public void show() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder
                .setTitle("Clean Up Firebase ~ Irreversible Action!")
                .setMessage("Are you sure you want to clear invalid data? It can't be undone.")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        mFirebaseEndPoint.guestLogGetAll(new GetAllListener());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    class GetAllListener implements FirebaseEndPoint.DataSnapshotListener {

        @Override
        public void onDataChange(DataSnapshot guestLogSnapshot) {

            List<DataSnapshot> invalidGuestSnapshots = new GuestEntryMapper().getInvalidGuestEntryDataSnapshots(guestLogSnapshot);

            for (DataSnapshot invalidGuestSnapshot : invalidGuestSnapshots) {
                String message = "Sending remove request for id = " + invalidGuestSnapshot.getKey();
                Logr.d(message);
                mFirebaseEndPoint.remove(invalidGuestSnapshot, new CleanUpListener());
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

            Logr.d("Unexpected error in GetAllListener! " + firebaseError.getDetails());
        }
    }


    class CleanUpListener implements FirebaseEndPoint.SetValueListener {

        @Override
        public void onFirebaseSuccess() {
            String message = "Clean up request is successful.";
            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            Logr.d(message);
        }

        @Override
        public void onFirebaseError(FirebaseError firebaseError) {

            Logr.d("Unexpected error in CleanUpListener! " + firebaseError.getDetails());
        }
    }
}
