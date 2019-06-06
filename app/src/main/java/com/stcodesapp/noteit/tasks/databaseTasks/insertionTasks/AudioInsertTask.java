package com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Email;

public class AudioInsertTask extends AsyncTask<Audio, Void, Integer> {

    public interface Listener{
        void onAudioInserted(int numberOfAudio);
    }


    private final Context context;
    private NoteDatabase noteDatabase;
    private Listener listener;


    public AudioInsertTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        noteDatabase = NoteDatabase.getInstance(context);
    }

    @Override
    protected Integer doInBackground(Audio... audio) {
        if(audio.length==1)
            audio[0].setId(noteDatabase.audioDao().insertSingleAudio(audio[0]));
        else noteDatabase.audioDao().insertAudio(audio);
        return audio.length;
    }

    @Override
    protected void onPostExecute(Integer numberOfAudio) {
        super.onPostExecute(numberOfAudio);
        listener.onAudioInserted(numberOfAudio);

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
