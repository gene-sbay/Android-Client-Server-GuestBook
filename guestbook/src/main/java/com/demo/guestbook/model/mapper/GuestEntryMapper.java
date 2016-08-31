package com.demo.guestbook.model.mapper;

import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.model.view.GuestEntryViewModel;
import com.demo.guestbook.remote.FirebaseEndPoint;
import com.demo.guestbook.util.Const;
import com.firebase.client.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A Simple POJO class containing a BaseObservable String.
 */
public class GuestEntryMapper {

    public GuestEntry map(GuestEntryViewModel guestEntryViewModel) {
        GuestEntry guestEntry = new GuestEntry();
        guestEntry.setFirstName(guestEntryViewModel.firstName.get());
        guestEntry.setLastName(guestEntryViewModel.lastName.get());
        guestEntry.setBirthday(guestEntryViewModel.birthday.get());
        guestEntry.setZipcode(guestEntryViewModel.zipcode.get());
        return guestEntry;
    }


    public GuestEntryViewModel map(GuestEntry guestEntry) {
        GuestEntryViewModel guestEntryViewModel = new GuestEntryViewModel();
        guestEntryViewModel.firstName.set(guestEntry.getFirstName());
        guestEntryViewModel.lastName.set(guestEntry.getLastName());
        guestEntryViewModel.birthday.set(guestEntry.getBirthday());
        guestEntryViewModel.zipcode.set(guestEntry.getZipcode());
        return guestEntryViewModel;
    }

    public GuestEntry map(DataSnapshot guestEntryDataSnapshot) {
        GuestEntry guestEntry = new GuestEntry();
        guestEntry.setId(getValue("id", guestEntryDataSnapshot));
        guestEntry.setFirstName(getValue("firstName", guestEntryDataSnapshot));
        guestEntry.setLastName(getValue("lastName", guestEntryDataSnapshot));
        guestEntry.setBirthday(getValue("birthday", guestEntryDataSnapshot));
        guestEntry.setZipcode(getValue("zipcode", guestEntryDataSnapshot));
        return guestEntry;
    }

    private String getValue(String key , DataSnapshot dataSnapshot) {
        return getChildStringValue(key, dataSnapshot);
    }

    public static String getChildStringValue(String key, DataSnapshot dataSnapshot) {

        String value = "";

        DataSnapshot valueObject = dataSnapshot.child(key);

        try {
            if (valueObject != null && valueObject.getValue() != null) {
                value = valueObject.getValue().toString();
            }
        }
        catch (NullPointerException npEx) {
            npEx.printStackTrace();
        }

        return value;
    }

    public List<GuestEntry> getAllGuestEntries(DataSnapshot guestLogSnapshot) {

        List<GuestEntry> guestEntries = new ArrayList<>();

        Iterable<DataSnapshot> guestEntrySnapshots = guestLogSnapshot.getChildren();

        for (DataSnapshot guestEntryDataSnapshot : guestEntrySnapshots) {

            GuestEntry guestEntry = map(guestEntryDataSnapshot);
            guestEntries.add(guestEntry);
        }

        return guestEntries;
    }

    public List<String> getInvalidGuestEntryIds(DataSnapshot guestLogSnapshot) {

        List<String> invalidGuestEntryIds = new ArrayList<>();

        Iterable<DataSnapshot> dataSnapshots = guestLogSnapshot.getChildren();
        for (DataSnapshot guestEntryDataSnapshot : dataSnapshots) {

            Iterable<DataSnapshot> dataSnapshotFields = guestEntryDataSnapshot.getChildren();

            boolean idFieldFound = false;

            for (DataSnapshot dataSnapshotField : dataSnapshotFields) {

                String childKey = dataSnapshotField.getKey();
                if (childKey.equals(Const.Field.ID)) {
                    idFieldFound = true;
                    break;
                }
            }

            if ( ! idFieldFound ) {
                String nodeId = guestEntryDataSnapshot.getKey();
                invalidGuestEntryIds.add(nodeId);
            }
        }

        return invalidGuestEntryIds;
    }
}
