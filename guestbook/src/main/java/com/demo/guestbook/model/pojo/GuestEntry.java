package com.demo.guestbook.model.pojo;

/**
 * A Simple POJO class containing a BaseObservable String.
 */
public class GuestEntry {

    private String firstName = new String();
    private String lastName = new String();
    private String birthday = new String();
    private String zipcode = new String();

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
