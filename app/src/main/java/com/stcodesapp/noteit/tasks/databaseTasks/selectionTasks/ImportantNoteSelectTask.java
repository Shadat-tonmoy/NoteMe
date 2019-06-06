package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Note;

import java.util.List;

public class ImportantNoteSelectTask extends AsyncTask<Void,Void, List<Note>> {

    public interface Listener{
        void onImportantNoteFetched(List<Note> fetchedNotes);
    }

    private Listener listener;
    private Context context;
    private NoteDatabase noteDatabase;

    public ImportantNoteSelectTask(Context context)
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
        return noteDatabase.notesDao().getImportantNoes();
    }

    @Override
    protected void onPostExecute(List<Note> fetchedNote) {
        super.onPostExecute(fetchedNote);
        listener.onImportantNoteFetched(fetchedNote);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }


}
