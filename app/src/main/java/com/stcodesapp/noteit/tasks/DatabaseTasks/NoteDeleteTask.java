package com.stcodesapp.noteit.tasks.DatabaseTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.dao.NotesDao;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.entities.Note;

public class NoteDeleteTask extends AsyncTask<Note,Void,Void> {

    private NoteDatabase noteDatabase;
    private NotesDao notesDao;
    private Context context;

    public NoteDeleteTask(Context context)
    {
        this.context = context;
        noteDatabase = NoteDatabase.getInstance(context);
        notesDao = noteDatabase.notesDao();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.e("Note","Deleting....");
        notesDao.delete(notes[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, R.string.note_deleted, Toast.LENGTH_SHORT).show();
    }
}
