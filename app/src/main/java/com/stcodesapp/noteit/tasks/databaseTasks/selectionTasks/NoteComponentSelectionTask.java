package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.NoteComponents;

import java.util.List;

public class NoteComponentSelectionTask extends AsyncTask<Long, Void, NoteComponents>{


    public interface Listener {
        void onNoteComponentFetched(NoteComponents noteComponents);

    }

    private NoteDatabase noteDatabase;
    private NoteComponents noteComponents;
    private Listener listener;

    public NoteComponentSelectionTask(Context context, NoteComponents noteComponents)
    {
        noteDatabase = NoteDatabase.getInstance(context);
        this.noteComponents = noteComponents;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected NoteComponents doInBackground(Long... longs) {
        long noteId = longs[0];
        noteComponents.setChosenAudios(noteDatabase.audioDao().getAllAudioForNote(noteId));
        noteComponents.setChosenImages(noteDatabase.imageDao().getAllImageForNote(noteId));
        noteComponents.setEmails(noteDatabase.emailDao().getAllEmailForNote(noteId));
        noteComponents.setChosenContacts(noteDatabase.contactDao().getAllContactsForNote(noteId));
        return noteComponents;
    }

    @Override
    protected void onPostExecute(NoteComponents noteComponents) {
        super.onPostExecute(noteComponents);
        listener.onNoteComponentFetched(noteComponents);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
