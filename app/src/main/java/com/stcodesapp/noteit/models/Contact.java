package com.stcodesapp.noteit.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.tasks.UtilityTasks;

import java.io.Serializable;

@Entity(tableName = "contact")
public class Contact implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    private long id;
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
        if(!UtilityTasks.isValidString(displayName))
            displayName = Constants.UNNAMED;
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
