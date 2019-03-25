package com.stcodesapp.noteit.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;


@Entity(tableName = "notes")
public class Note implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;

    private String noteTitle,noteText,backgroundColor,imageName,audioName,tag,phoneNumbers;

    private long creationTime;

    private boolean important;

    public Note() {
    }

    public Note(String id, String noteTitle, String noteText, long creationTime, boolean important) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.creationTime = creationTime;
        this.important = important;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteText='" + noteText + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", imageName='" + imageName + '\'' +
                ", audioName='" + audioName + '\'' +
                ", tag='" + tag + '\'' +
                ", phoneNumbers='" + phoneNumbers + '\'' +
                ", creationTime=" + creationTime +
                ", important=" + important +
                '}';
    }
}

