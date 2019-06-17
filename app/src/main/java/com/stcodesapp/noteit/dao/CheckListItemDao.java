package com.stcodesapp.noteit.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.ChecklistItem;

import java.util.List;

@Dao
public interface CheckListItemDao {

    @Insert
    void insertCheckListItem(ChecklistItem... checklistItem);

    @Insert
    long insertSingleCheckListItem(ChecklistItem checklistItem);

    @Delete
    void deleteCheckListItem(ChecklistItem checklistItem);

    @Query("select * from checklistitem where checkListId=:checkListId")
    List<ChecklistItem> getCheckListItems(long checkListId);

}
