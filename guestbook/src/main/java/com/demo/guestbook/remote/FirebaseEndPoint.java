package com.demo.guestbook.remote;

import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.util.Const;
import com.demo.guestbook.util.Logr;
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

    public Firebase getGuestEntryNode(String guestEntryId) {
        Firebase guestLogRef = new com.firebase.client.Firebase(Const.Firebase.GUEST_LOG_URL);
        Firebase guestEntryNode = guestLogRef.child(guestEntryId);
        return guestEntryNode;
    }

    public void save(GuestEntry guestEntry, final SetValueListener firebaseSetValueListener) {

        String nodeId = guestEntry.getId();

        Firebase guestEntryRef = getGuestEntryNode(nodeId);

        guestEntryRef.setValue(guestEntry, new Firebase.CompletionListener() {
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

    public void remove(DataSnapshot dataSnapshot, final SetValueListener firebaseSetValueListener) {

        dataSnapshot.getRef().removeValue(new Firebase.CompletionListener() {
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

    public void remove(String guestEntryId, final SetValueListener firebaseSetValueListener) {

        Firebase guestEntryRef  = getGuestEntryNode(guestEntryId);

        guestEntryRef.removeValue(new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Logr.e("Failed to removeValue via Firebase");
                    firebaseSetValueListener.onFirebaseError(firebaseError);
                }
                else {
                    Logr.d("Data removed successfully.");
                    firebaseSetValueListener.onFirebaseSuccess();
                }
            }
        });
    }

    public void guestLogGetAll(final DataSnapshotListener dataSnapshotListener) {

        Firebase guestLogRef = new com.firebase.client.Firebase(Const.Firebase.GUEST_LOG_URL);

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
