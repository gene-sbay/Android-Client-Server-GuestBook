package com.demo.guestbook.remote;


import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.util.Const;
import com.demo.guestbook.util.DeviceUtil;

import java.util.Calendar;

public class FirebaseEndPoint {

    com.firebase.client.Firebase mRootRef;

    public void connect() {
        mRootRef = new com.firebase.client.Firebase(Const.Firebase.PROJECT_URL);
    }

    public void save(GuestEntry guestEntry) {

        String nodeId = getNextDatabaseId();

        com.firebase.client.Firebase guestLogRef = mRootRef.child(Const.Firebase.GUEST_LOG).child(nodeId);

        guestLogRef.setValue(guestEntry);
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
