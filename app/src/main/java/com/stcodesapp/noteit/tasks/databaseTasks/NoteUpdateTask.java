package com.stcodesapp.noteit.tasks.databaseTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.dao.NotesDao;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Note;

public class NoteUpdateTask extends AsyncTask<Note,Void, Note> {

    private NoteDatabase noteDatabase;
    private NotesDao notesDao;
    private Context context;

    public NoteUpdateTask(Context context)
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
    protected Note doInBackground(Note... notes) {
        Log.e("Note","Updating...."+notes[0].isImportant());
        notesDao.update(notes[0]);
        return notes[0];
    }

    @Override
    protected void onPostExecute(Note note) {
        super.onPostExecute(note);
//        Toast.makeText(context, R.string.note_updated, Toast.LENGTH_SHORT).show();
    }
}
