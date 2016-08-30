package com.demo.guestbook.model.sharedprefs;

import com.demo.guestbook.model.pojo.GuestEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Stored in SharedPrefs as JSON string
 */
public class AppState {

    private List<GuestEntry> mLocalGuestEntries;

    public List<GuestEntry> getLocalGuestEntries() {
        verifyLocalGuestEntries();
        return mLocalGuestEntries;
    }

    public void addLocalGuestEntry(GuestEntry guestEntry) {
        verifyLocalGuestEntries();
        mLocalGuestEntries.add(guestEntry);
    }

    private void verifyLocalGuestEntries() {
        if (mLocalGuestEntries == null) {
            mLocalGuestEntries = new ArrayList<>();
        }
    }
}