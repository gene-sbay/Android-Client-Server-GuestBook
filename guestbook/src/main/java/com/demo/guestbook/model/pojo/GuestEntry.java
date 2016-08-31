package com.demo.guestbook.model.pojo;

import com.demo.guestbook.model.validator.GuestEntryValidator;

/**
 * A Simple POJO class containing a BaseObservable String.
 */
public class GuestEntry {

    public final static int NAME_MINIMUM_CHARS = 2;
    public final static String NAME_FIELD_SUFFIX = "Name";

    private String id;
    private String firstName;
    private String lastName;
    private String birthday;
    private String zipcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
