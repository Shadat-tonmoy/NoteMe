package com.stcodesapp.noteit.listeners;

import android.app.Activity;
import android.net.Uri;
import android.view.View;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.FileIOTasks;
import com.stcodesapp.noteit.ui.activities.NoteFieldActivity;

public class AudioListener implements View.OnClickListener {

    private Audio audio;
    private FileIOTasks fileIOTasks;
    private Uri audioUri;
    private Activity activity;
    private DatabaseTasks databaseTasks;
    private View audioHolder;

    public AudioListener(Audio audio, FileIOTasks fileIOTasks, Uri audioUri, Activity activity, DatabaseTasks databaseTasks, View audioHolder) {
        this.audio = audio;
        this.fileIOTasks = fileIOTasks;
        this.audioUri = audioUri;
        this.activity = activity;
        this.databaseTasks = databaseTasks;
        this.audioHolder = audioHolder;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.audio_remove_btn:
                removeAudio();
                break;
            case R.id.audio_holder:
                openAudio();
                break;
        }
    }

    private void removeAudio()
    {
        audioHolder.setVisibility(View.GONE);
        databaseTasks.getAudioDeleteTask(((NoteFieldActivity)activity).getNoteFieldController()).execute(audio);
    }

    private void openAudio()
    {
        fileIOTasks.openAudioFile(audioUri);
    }
}
