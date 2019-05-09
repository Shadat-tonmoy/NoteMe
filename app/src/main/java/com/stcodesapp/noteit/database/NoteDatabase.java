package com.stcodesapp.noteit.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.dao.ContactDao;
import com.stcodesapp.noteit.dao.EmailDao;
import com.stcodesapp.noteit.dao.NotesDao;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Note;

@Database(entities = {Note.class, Email.class, Contact.class},version = 2,exportSchema = false )
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();

    public abstract EmailDao emailDao();

    public abstract ContactDao contactDao();

    private static volatile NoteDatabase noteDatabase = null;

    public static NoteDatabase getInstance(Context context)
    {
        if(noteDatabase == null)
        {
            synchronized (NoteDatabase.class)
            {
                noteDatabase = Room.databaseBuilder(context,
                        NoteDatabase.class,
                        Constants.APP_DATABASE)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return noteDatabase;
    }
}
