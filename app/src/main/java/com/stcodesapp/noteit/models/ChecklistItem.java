package com.stcodesapp.noteit.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcelable;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = CheckList.class,
        parentColumns = {"checkListId"},
        childColumns = {"checkListId"},
        onDelete = CASCADE
))
public class ChecklistItem implements Serializable{

    private String field1,field2;
    private boolean isChecked;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "checkListItemId")
    private long id=0;
    private long checkListId;



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

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }


    public long getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(long checkListId) {
        this.checkListId = checkListId;
    }

    @Override
    public String toString() {
        return "ChecklistItem{" +
                "field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", isChecked=" + isChecked +
                ", id=" + id +
                '}';
    }
}
