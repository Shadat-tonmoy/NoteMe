package com.stcodesapp.noteit.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.stcodesapp.noteit.models.Note;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    long insert(Note note);


    @Query("SELECT * FROM notes")
    List<Note> getAllNoes();

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);


}
