package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Email;

import java.util.List;

public class AllAudioSelectionTasks extends AsyncTask<Long, Void, List<Audio>>{



    public interface Listener{
        void onAllAudioFetchedFetched(List<Audio> audio,int purpose);
    }

    private Context context;
    private NoteDatabase noteDatabase;
    private Listener listener;
    private int purpose;

    public AllAudioSelectionTasks(Context context, Listener listener,int purpose)
    {
        this.context = context;
        noteDatabase = NoteDatabase.getInstance(context);
        this.listener = listener;
        this.purpose = purpose;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Audio> doInBackground(Long... noteID) {
        long noteId = noteID[0];
        if(noteId == Constants.INVALID)
            return noteDatabase.audioDao().getAllAudio();
        else return noteDatabase.audioDao().getAllAudioForNote(noteId);
    }

    @Override
    protected void onPostExecute(List<Audio> results) {
        super.onPostExecute(results);
        if(listener!=null)
            listener.onAllAudioFetchedFetched(results,purpose);
    }
}
