package com.stcodesapp.noteit.listeners;

import android.net.Uri;
import android.view.View;

import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.tasks.functionalTasks.FileIOTasks;

public class AudioListener implements View.OnClickListener {

    private Audio audio;
    private FileIOTasks fileIOTasks;
    private Uri audioUri;

    public AudioListener(Audio audio, FileIOTasks fileIOTasks, Uri audioUri) {
        this.audio = audio;
        this.fileIOTasks = fileIOTasks;
        this.audioUri = audioUri;
    }


    @Override
    public void onClick(View v) {
        fileIOTasks.openAudioFile(audioUri);
    }
}
