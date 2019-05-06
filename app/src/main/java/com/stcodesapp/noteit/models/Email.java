package com.stcodesapp.noteit.models;

import java.io.Serializable;

public class Email implements Serializable {

    private String emailName, emailID;

    public Email(String emailName, String emailID) {
        this.emailName = emailName;
        this.emailID = emailID;
    }

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailName='" + emailName + '\'' +
                ", emailID='" + emailID + '\'' +
                '}';
    }
}
