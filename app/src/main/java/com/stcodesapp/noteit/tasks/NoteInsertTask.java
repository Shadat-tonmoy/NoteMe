package com.stcodesapp.noteit.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.dao.NotesDao;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.entities.Note;

public class NoteInsertTask extends AsyncTask<Note,Void,Void> {

    private NoteDatabase noteDatabase;
    private Context context;
    private NotesDao notesDao;


    public NoteInsertTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        noteDatabase = NoteDatabase.getInstance(context);
        notesDao = noteDatabase.notesDao();
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.e("Note","Inserting....");
        notesDao.insert(notes[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, R.string.note_added, Toast.LENGTH_SHORT).show();
    }
}
