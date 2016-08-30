package com.demo.guestbook.prefs;

import com.demo.guestbook.model.pojo.GuestEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Stored in SharedPrefs as JSON string
 */
public class AppState {

    private List<GuestEntry> mLocalGuestEntries;

    public List<GuestEntry> getLocalGuestEntries() {
        return mLocalGuestEntries;
    }

    public void addLocalGuestEntry(GuestEntry guestEntry) {
        if (mLocalGuestEntries == null) {
            mLocalGuestEntries = new ArrayList<>();
        }

        mLocalGuestEntries.add(guestEntry);
    }
}