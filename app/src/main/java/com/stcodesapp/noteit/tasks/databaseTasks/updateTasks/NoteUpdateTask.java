package com.stcodesapp.noteit.tasks.databaseTasks.updateTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.AudioInsertTask;

public class NoteUpdateTask extends AsyncTask<Note, Void, Void> {


    private final Context context;
    private NoteDatabase noteDatabase;
    private AudioInsertTask.Listener listener;


    public NoteUpdateTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        noteDatabase = NoteDatabase.getInstance(context);
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDatabase.notesDao().update(notes[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void res) {

    }
}