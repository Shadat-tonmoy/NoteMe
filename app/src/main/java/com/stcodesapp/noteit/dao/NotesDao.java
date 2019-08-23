package com.stcodesapp.noteit.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.stcodesapp.noteit.models.Note;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    long insert(Note note);



    @Insert
    long[] insertAllNote(Note... note);


    @Query("SELECT * FROM notes order by note_id desc")
    List<Note> getAllNoes();


    @Query("SELECT * FROM notes where isImportant")
    List<Note> getImportantNoes();

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("delete from notes")
    int deleteAllNote();


}
