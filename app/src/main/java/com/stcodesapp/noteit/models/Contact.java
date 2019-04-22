package com.stcodesapp.noteit.models;

import java.io.Serializable;

public class Contact implements Serializable {

    private String phoneNumber, displayName;

    public Contact(String phoneNumber, String displayName) {
        this.phoneNumber = phoneNumber;
        this.displayName = displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
