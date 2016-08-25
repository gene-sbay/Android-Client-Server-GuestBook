package com.demo.guestbook.pojo;

import com.demo.guestbook.ui.binding.BindableString;

/**
 * A Simple POJO class containing a BaseObservable String.
 */
public class GuestEntry {

    public BindableString firstName = new BindableString();
    public BindableString lastName = new BindableString();
    public BindableString birthday = new BindableString();
    public BindableString zipcode = new BindableString();

    @Override
    public String toString() {
        return "GuestEntry {" +
                "firstName=" + firstName.get() +
                ", lastName=" + lastName.get() +
                ", birthday=" + birthday.get() +
                ", zipcode=" + zipcode.get() +
                '}';
    }

}
