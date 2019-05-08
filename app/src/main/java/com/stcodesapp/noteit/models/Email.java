package com.stcodesapp.noteit.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "email",
        foreignKeys = @ForeignKey(
                entity = Note.class,
                parentColumns = {"note_id"},
                childColumns = {"note_id"},
                onDelete = ForeignKey.CASCADE
        )
)
public class Email implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "email_id")
    private long id;
    @ColumnInfo(name = "note_id")
    private long noteId;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", emailName='" + emailName + '\'' +
                ", emailID='" + emailID + '\'' +
                '}';
    }
}
