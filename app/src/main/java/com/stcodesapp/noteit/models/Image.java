package com.stcodesapp.noteit.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

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

    public Image(String imageURI) {
        this.imageURI = imageURI;
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

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
