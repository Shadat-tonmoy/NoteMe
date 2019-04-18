package com.stcodesapp.noteit.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.stcodesapp.noteit.dao.NotesDao;
import com.stcodesapp.noteit.entities.Note;

@Database(entities = {Note.class},version = 1,exportSchema = false )
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();

    private static volatile NoteDatabase noteDatabase = null;

    public static NoteDatabase getInstance(Context context)
    {
        if(noteDatabase == null)
        {
            synchronized (NoteDatabase.class)
            {
                noteDatabase = Room.databaseBuilder(context,
                        NoteDatabase.class,
                        "noteDatabase")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return noteDatabase;
    }
}
