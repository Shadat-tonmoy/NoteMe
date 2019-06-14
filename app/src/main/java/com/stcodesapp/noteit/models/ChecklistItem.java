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
public class ChecklistItem implements Serializable{

    private String field1,field2;
    private boolean isChecked;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "single_checklist_id")
    private long id;

    @ColumnInfo(name = "note_id")
    private long noteId;


    public ChecklistItem() {

    }

    public ChecklistItem(String field1, String field2, boolean isChecked) {
        this.field1 = field1;
        this.field2 = field2;
        this.isChecked = isChecked;
    }

    public ChecklistItem(String field1, boolean isChecked) {
        this.field1 = field1;
        this.isChecked = isChecked;
    }

    public ChecklistItem(String field1, boolean isChecked, long id, long noteId) {
        this.field1 = field1;
        this.isChecked = isChecked;
        this.id = id;
        this.noteId = noteId;
    }


    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
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

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    @Override
    public String toString() {
        return "ChecklistItem{" +
                "field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", isChecked=" + isChecked +
                ", id=" + id +
                ", noteId=" + noteId +
                '}';
    }
}
