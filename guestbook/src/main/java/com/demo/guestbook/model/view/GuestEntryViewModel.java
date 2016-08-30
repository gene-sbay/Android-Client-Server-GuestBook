package com.demo.guestbook.model.view;

import com.demo.guestbook.R;
import com.demo.guestbook.ui.binding.BindableString;
import com.demo.guestbook.util.TheApp;

/**
 * A Simple POJO class containing a BaseObservable String.
 * Databinding convention specifies to make view instance members public
 */
public class GuestEntryViewModel {

    public BindableString firstName = new BindableString();
    public BindableString lastName = new BindableString();
    public BindableString birthday = new BindableString();
    public BindableString zipcode = new BindableString();

    public GuestEntryViewModel() {
        birthday.set(TheApp.findString(R.string.please_set));
    }

    @Override
    public String toString() {
        return "GuestEntryViewModel {" +
                "firstName=" + firstName.get() +
                ", lastName=" + lastName.get() +
                ", birthday=" + birthday.get() +
                ", zipcode=" + zipcode.get() +
                '}';
    }

}
