package com.demo.guestbook.remote;


import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.ui.util.DialogUtil;
import com.demo.guestbook.util.Const;
import com.demo.guestbook.util.DeviceUtil;
import com.demo.guestbook.util.Logr;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;

public class FirebaseEndPoint {

    public interface Listener {
        void onFirebaseSuccess();
        void onFirebaseError(FirebaseError firebaseError);
    }

    com.firebase.client.Firebase mRootRef;

    public void connect() {
        mRootRef = new com.firebase.client.Firebase(Const.Firebase.PROJECT_URL);
    }

    public void save(GuestEntry guestEntry, final Listener firebaseListener) {

        String nodeId = getNextDatabaseId();

        com.firebase.client.Firebase guestLogRef = mRootRef.child(Const.Firebase.GUEST_LOG).child(nodeId);

        guestLogRef.setValue(guestEntry, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Logr.e("Failed to setValue via Firebase");
                    firebaseListener.onFirebaseError(firebaseError);
                }
                else {
                    Logr.d("Data saved successfully.");
                    firebaseListener.onFirebaseSuccess();
                }
            }
        });
    }

    private String getNextDatabaseId() {

        long timeL = Calendar.getInstance().getTimeInMillis();
        String deviceId = DeviceUtil.getDeviceId();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(timeL);
        stringBuffer.append('-');
        stringBuffer.append(deviceId);
        return stringBuffer.toString();
    }
}
