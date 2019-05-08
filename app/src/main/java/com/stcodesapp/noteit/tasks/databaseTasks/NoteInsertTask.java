package com.stcodesapp.noteit.tasks.databaseTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.dao.NotesDao;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Note;

public class NoteInsertTask extends AsyncTask<Note,Void,Long> {

    public interface Listener{
        void onNoteInserted(long insertedId);
    }

    private NoteDatabase noteDatabase;
    private Context context;
    private NotesDao notesDao;
    private Listener listener;



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
    protected Long doInBackground(Note... notes) {
        Log.e("Note","Inserting....");
        return notesDao.insert(notes[0]);
    }

    @Override
    protected void onPostExecute(Long insertedId) {
        super.onPostExecute(insertedId);
        listener.onNoteInserted(insertedId);
        Toast.makeText(context, R.string.note_added, Toast.LENGTH_SHORT).show();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
