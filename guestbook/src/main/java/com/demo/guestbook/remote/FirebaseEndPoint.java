package com.demo.guestbook.remote;


import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.util.Const;

public class FirebaseEndPoint {

    com.firebase.client.Firebase mRootRef;

    public void connect() {
        mRootRef = new com.firebase.client.Firebase(Const.Firebase.PROJECT_URL);
    }

    public void save(GuestEntry guestEntry) {

        com.firebase.client.Firebase guestLogRef = mRootRef.child(Const.Firebase.GUEST_LOG).child("randomID");;

        guestLogRef.setValue(guestEntry);
    }
}
