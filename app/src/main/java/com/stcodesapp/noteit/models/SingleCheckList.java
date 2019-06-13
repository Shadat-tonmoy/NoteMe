package com.stcodesapp.noteit.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "single_check_list", foreignKeys = @ForeignKey(
        entity = Note.class,
        parentColumns = {"note_id"},
        childColumns = {"note_id"},
        onDelete = CASCADE
))
public class SingleCheckList implements Serializable {

    private String title;
    private boolean isChecked;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "single_checklist_id")
    private long id;

    @ColumnInfo(name = "note_id")
    private long noteId;


    public SingleCheckList() {

    }

    public SingleCheckList(String title, boolean isChecked) {
        this.title = title;
        this.isChecked = isChecked;
    }

    public SingleCheckList(String title, boolean isChecked, long id, long noteId) {
        this.title = title;
        this.isChecked = isChecked;
        this.id = id;
        this.noteId = noteId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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
        return "SingleCheckList{" +
                "title='" + title + '\'' +
                ", isChecked=" + isChecked +
                ", id=" + id +
                ", noteId=" + noteId +
                '}';
    }
}
