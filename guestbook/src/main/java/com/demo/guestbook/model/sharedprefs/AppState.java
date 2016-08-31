package com.demo.guestbook.model.sharedprefs;

import com.demo.guestbook.model.pojo.GuestEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Stored in SharedPrefs as JSON string
 */
public class AppState {

    private List<GuestEntry> mLocalGuestEntries;
    private List<GuestEntry> mServerGuestEntries;

    public List<GuestEntry> getLocalGuestEntries() {
        verifyLocalGuestEntries();
        return mLocalGuestEntries;
    }

    public GuestEntry getLocalGuestEntryById(String id) {

        verifyLocalGuestEntries();

        GuestEntry searchGuestEntry = null;
        for (GuestEntry guestEntry : mLocalGuestEntries) {

            if (id.equals(guestEntry.getId())) {
                searchGuestEntry = guestEntry;
                break;
            }
        }
        return searchGuestEntry;
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

    public List<GuestEntry> getServerGuestEntries() {
        verifyServerGuestEntries();
        return mServerGuestEntries;
    }

    public void setServerGuestEntries(List<GuestEntry> serverGuestEntries) {
        verifyServerGuestEntries();
        mServerGuestEntries = serverGuestEntries;
    }

    private void verifyServerGuestEntries() {
        if (mServerGuestEntries == null) {
            mServerGuestEntries = new ArrayList<>();
        }
    }
}