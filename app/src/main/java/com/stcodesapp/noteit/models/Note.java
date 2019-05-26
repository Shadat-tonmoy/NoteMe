package com.stcodesapp.noteit.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.stcodesapp.noteit.constants.BackgroundColors;
import com.stcodesapp.noteit.constants.Constants;

import java.io.Serializable;


@Entity(tableName = "notes")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private long id;

    private String noteTitle,noteText,backgroundColor;

    private long creationTime;

    private boolean isImportant;

    private int priority = 0;

    private int contactPriority, emailPriority, audioPriority, imagePriority;

    public Note() {
        this.isImportant = false;
        this.backgroundColor = BackgroundColors.WHITE;
    }

    public Note(long id, String noteTitle, String noteText, long creationTime, boolean isImportant) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.creationTime = creationTime;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        this.isImportant = important;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getContactPriority() {
        return contactPriority;
    }

    public void updateContactPriority() {
        if(getContactPriority() == Constants.ZERO)
        {
            incrementPriority();
            setContactPriority(getPriority());
        }
    }

    public void setContactPriority(int contactPriority) {
        this.contactPriority = contactPriority;
    }

    public int getEmailPriority() {
        return emailPriority;
    }

    public void updateEmailPriority() {
        if(getEmailPriority() == Constants.ZERO)
        {
            incrementPriority();
            setEmailPriority(getPriority());
        }
    }

    public void setEmailPriority(int emailPriority) {
        this.emailPriority = emailPriority;
    }

    public int getAudioPriority() {
        return audioPriority;
    }

    public void updateAudioPriority() {
        if(getAudioPriority() == Constants.ZERO)
        {
            incrementPriority();
            setAudioPriority(getPriority());
        }
    }

    public void updateImagePriority() {
        if(getImagePriority() == Constants.ZERO)
        {
            incrementPriority();
            setImagePriority(getPriority());
        }
    }


    public void setAudioPriority(int audioPriority) {
        this.audioPriority = audioPriority;
    }

    public int getImagePriority() {
        return imagePriority;
    }

    public void setImagePriority(int imagePriority) {
        this.imagePriority = imagePriority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void incrementPriority()
    {
        this.priority++;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteText='" + noteText + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", creationTime=" + creationTime +
                ", isImportant=" + isImportant +
                ", contactPriority=" + contactPriority +
                ", emailPriority=" + emailPriority +
                ", audioPriority=" + audioPriority +
                ", imagePriority=" + imagePriority +
                '}';
    }
}

