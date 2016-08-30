package com.demo.guestbook.model.mapper;

import com.demo.guestbook.model.pojo.GuestEntry;
import com.demo.guestbook.model.view.GuestEntryViewModel;
import com.demo.guestbook.ui.binding.BindableString;

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
}
