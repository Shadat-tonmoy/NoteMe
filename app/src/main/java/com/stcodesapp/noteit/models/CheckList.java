package com.stcodesapp.noteit.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = Note.class,
        parentColumns = {"note_id"},
        childColumns = {"note_id"},
        onDelete = CASCADE
))
public class CheckList implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long checkListId;
    @ColumnInfo(name = "note_id")
    private long noteId;
    @Ignore
    private List<ChecklistItem> checklistItems;
    private String checkListTitle,checkListSecondFieldTitle;


    public CheckList() {

    }


    public CheckList(long checkListId, long noteId, List<ChecklistItem> checklistItems, String checkListTitle, String checkListSecondFieldTitle) {
        this.checkListId = checkListId;
        this.noteId = noteId;
        this.checklistItems = checklistItems;
        this.checkListTitle = checkListTitle;
        this.checkListSecondFieldTitle = checkListSecondFieldTitle;
    }

    public CheckList(String checkListTitle) {
        this.checkListTitle = checkListTitle;
    }

    public CheckList(String checkListTitle, String checkListSecondFieldTitle) {
        this.checkListTitle = checkListTitle;
        this.checkListSecondFieldTitle = checkListSecondFieldTitle;
    }

    public long getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(long checkListId) {
        this.checkListId = checkListId;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public List<ChecklistItem> getChecklistItems() {
        return checklistItems;
    }

    public void setChecklistItems(List<ChecklistItem> checklistItems) {
        this.checklistItems = checklistItems;
    }

    public String getCheckListTitle() {
        return checkListTitle;
    }

    public void setCheckListTitle(String checkListTitle) {
        this.checkListTitle = checkListTitle;
    }

    public String getCheckListSecondFieldTitle() {
        return checkListSecondFieldTitle;
    }

    public void setCheckListSecondFieldTitle(String checkListSecondFieldTitle) {
        this.checkListSecondFieldTitle = checkListSecondFieldTitle;
    }
}
