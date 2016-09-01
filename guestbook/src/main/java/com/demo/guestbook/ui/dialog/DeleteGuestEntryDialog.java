package com.demo.guestbook.ui.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.demo.guestbook.model.mapper.GuestEntryMapper;
import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.model.sharedprefs.AppState;
import com.demo.guestbook.model.sharedprefs.AppStateDao;
import com.demo.guestbook.remote.FirebaseEndPoint;
import com.demo.guestbook.util.Logr;
import com.demo.guestbook.util.TheApp;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.List;

public class DeleteGuestEntryDialog {

    private FirebaseEndPoint mFirebaseEndPoint;

    private Activity mActivity;
    private GuestEntry mGuestEntry;

    public DeleteGuestEntryDialog(Activity activity, GuestEntry guestEntry) {
        mActivity = activity;
        mGuestEntry = guestEntry;
        mFirebaseEndPoint = new FirebaseEndPoint();
    }

    public void show() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder
                .setTitle("Delete Guest Entry ~ Irreversible Action!")
                .setMessage("Are you sure you want to delete this Guest Entry? It can't be undone.")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        mFirebaseEndPoint.remove(mGuestEntry.getId(), new DeleteGuestEntryListener());
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

    class DeleteGuestEntryListener implements FirebaseEndPoint.SetValueListener {

        @Override
        public void onFirebaseSuccess() {
            String message = "Delete Guest Entry request is successful.";
            Toast.makeText(TheApp.getAppContext(), message, Toast.LENGTH_SHORT).show();
            Logr.d(message);

            AppState appState = AppStateDao.getAppState();
            appState.removeLocalGuestEntryById(mGuestEntry.getId());
            AppStateDao.sharedPrefs_updateAppState(appState);
        }

        @Override
        public void onFirebaseError(FirebaseError firebaseError) {

            Logr.d("Unexpected error in DeleteGuestEntryListener! " + firebaseError.getDetails());
        }
    }
}
