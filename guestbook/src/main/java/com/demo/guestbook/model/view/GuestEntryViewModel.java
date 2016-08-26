package com.demo.guestbook.model.view;

import com.demo.guestbook.ui.binding.BindableString;

/**
 * A Simple POJO class containing a BaseObservable String.
 * Databinding convention specifies to make view instance members public
 */
public class GuestEntryViewModel {

    public BindableString firstName = new BindableString();
    public BindableString lastName = new BindableString();
    public BindableString birthday = new BindableString();
    public BindableString zipcode = new BindableString();

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
