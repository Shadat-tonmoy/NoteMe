package com.stcodesapp.noteit.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;

@Entity(tableName = "images", foreignKeys = @ForeignKey(
        entity = Note.class,
        parentColumns = {"note_id"},
        childColumns = {"note_id"},
        onDelete = ForeignKey.CASCADE
))
public class Image {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "image_id")
    private long id;
    @ColumnInfo(name = "note_id")
    private long noteId;
    @ColumnInfo(name = "uri")
    private String imageURI;
    private boolean isCaptured;

    public Image(String imageURI) {
        this.imageURI = imageURI;
    }

    public Image(String imageURI,boolean isCaptured) {
        this.imageURI = imageURI;
        this.isCaptured = isCaptured;
    }

    public Image() {
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

    public String getImageURI() {
        return imageURI;
    }

    public String getImageFilePath()
    {
       return UtilityTasks.getFilePathFromURI(imageURI);
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }


    public boolean isCaptured() {
        return isCaptured;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", noteId=" + noteId +
                ", imageURI='" + imageURI + '\'' +
                ", isCaptured=" + isCaptured +
                '}';
    }
}
