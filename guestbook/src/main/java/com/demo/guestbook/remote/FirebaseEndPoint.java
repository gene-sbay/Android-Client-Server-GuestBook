package com.demo.guestbook.remote;

import android.app.Activity;
import android.app.ProgressDialog;

import com.demo.guestbook.R;
import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.util.Const;
import com.demo.guestbook.util.Logr;
import com.demo.guestbook.util.TheApp;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FirebaseEndPoint {

    public interface SetValueListener {
        void onFirebaseSuccess();
        void onFirebaseError(FirebaseError firebaseError);
    }

    public interface DataSnapshotListener {
        void onDataChange(DataSnapshot snapshot);
        void onCancelled(FirebaseError firebaseError);
    }

    com.firebase.client.Firebase mRootRef;

    public void connect() {
        mRootRef = new com.firebase.client.Firebase(Const.Firebase.PROJECT_URL);
    }

    public void save(GuestEntry guestEntry, final SetValueListener firebaseSetValueListener) {

        String nodeId = guestEntry.getId();

        com.firebase.client.Firebase guestLogRef = mRootRef.child(Const.Firebase.GUEST_LOG).child(nodeId);

        guestLogRef.setValue(guestEntry, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Logr.e("Failed to setValue via Firebase");
                    firebaseSetValueListener.onFirebaseError(firebaseError);
                }
                else {
                    Logr.d("Data saved successfully.");
                    firebaseSetValueListener.onFirebaseSuccess();
                }
            }
        });
    }


    private ProgressDialog getVisibleProgressDialog(Activity activity) {
        return ProgressDialog.show(activity,
                TheApp.findString(R.string.app_name),
                "Loading app data ...",
                true, false);
    }

    public void testGetAll(final DataSnapshotListener dataSnapshotListener) {

        Firebase guestLogRef = new com.firebase.client.Firebase("https://guest-bookly.firebaseio.com/guest_log");

        guestLogRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                dataSnapshotListener.onDataChange(snapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Logr.e("The read failed: " + firebaseError.getMessage());
                dataSnapshotListener.onCancelled(firebaseError);
            }
        });
    }

}
