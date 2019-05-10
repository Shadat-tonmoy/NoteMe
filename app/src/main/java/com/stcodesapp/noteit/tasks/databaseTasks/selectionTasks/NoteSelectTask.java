package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.dao.NotesDao;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Note;

import java.util.List;

public class NoteSelectTask extends AsyncTask<Void,Void, List<Note>> {

    public interface Listener{
        void onNoteFetched(List<Note> fetchedNotes);
    }

    private Listener listener;
    private Context context;
    private NoteDatabase noteDatabase;

    public NoteSelectTask(Context context)
    {
        this.context = context;
        noteDatabase = NoteDatabase.getInstance(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Note> doInBackground(Void... voids) {
        return noteDatabase.notesDao().getAllNoes();
    }

    @Override
    protected void onPostExecute(List<Note> fetchedNote) {
        super.onPostExecute(fetchedNote);
        listener.onNoteFetched(fetchedNote);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
