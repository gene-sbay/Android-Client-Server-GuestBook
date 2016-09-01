package com.demo.guestbook.model.sharedprefs;

import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.util.Logr;

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

    public boolean removeLocalGuestEntryById(String removeGuestEntryId) {

        verifyLocalGuestEntries();

        int removeIndex = -1;
        for (int i = 0; i< mLocalGuestEntries.size(); i++) {

            GuestEntry localGuest = mLocalGuestEntries.get(i);

            if (removeGuestEntryId.equals(localGuest.getId())) {
                removeIndex = i;
                break;
            }
        }

        if (removeIndex == -1) {
            Logr.d("APP STATE ERROR - Fix me!  Failed to find GuestEntry "
                    +"expected to exist for id = "
                    + removeGuestEntryId);
            return false;
        }

        mLocalGuestEntries.remove(removeIndex);
        return true;
    }

    public void addLocalGuestEntry(GuestEntry guestEntry) {
        verifyLocalGuestEntries();
        mLocalGuestEntries.add(guestEntry);
    }

    public void updateLocalGuestEntry(GuestEntry guestEntry) {
        verifyLocalGuestEntries();
        int updateIndex = -1;
        for (int i = 0; i< mLocalGuestEntries.size(); i++) {

            GuestEntry localGuest = mLocalGuestEntries.get(i);

            if (guestEntry.getId().equals(localGuest.getId())) {
                updateIndex = i;
                break;
            }
        }

        if (updateIndex == -1) {
            Logr.d("APP STATE ERROR - Fix me!  Failed to find GuestEntry "
                    +"expected to exist for id = "
                    + guestEntry.getId());
            return;
        }

        mLocalGuestEntries.remove(updateIndex);
        mLocalGuestEntries.add(updateIndex, guestEntry);
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