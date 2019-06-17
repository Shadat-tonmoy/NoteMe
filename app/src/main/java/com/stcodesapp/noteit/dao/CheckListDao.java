package com.stcodesapp.noteit.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.stcodesapp.noteit.models.CheckList;

import java.util.List;

@Dao
public interface CheckListDao {

    @Insert
    long insertSingleCheckList(CheckList checkList);

    @Insert
    long[] insertCheckList(CheckList... checkList);

    @Delete
    void deleteCheckList(CheckList checkList);

    @Query("select * from checklist where note_id=:noteId")
    List<CheckList> getCheckListForNote(long noteId);
}