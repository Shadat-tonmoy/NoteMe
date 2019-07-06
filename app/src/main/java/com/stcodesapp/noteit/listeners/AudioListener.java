package com.stcodesapp.noteit.listeners;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileDeletingTask;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileIOTasks;
import com.stcodesapp.noteit.ui.activities.NoteFieldActivity;

import java.io.File;

public class AudioListener implements View.OnClickListener {

    private Audio audio;
    private FileIOTasks fileIOTasks;
    private Uri audioUri;
    private Activity activity;
    private DatabaseTasks databaseTasks;
    private View audioHolder;
    private TasksFactory tasksFactory;

    public AudioListener(Audio audio, Uri audioUri, Activity activity,View audioHolder,TasksFactory tasksFactory) {
        this.audio = audio;
        this.fileIOTasks = tasksFactory.getFileIOTasks();
        this.audioUri = audioUri;
        this.activity = activity;
        this.databaseTasks = tasksFactory.getDatabaseTasks();
        this.tasksFactory = tasksFactory;
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
        if(audio.isFilePath())
        {
            FileDeletingTask fileDeletingTask = tasksFactory.getFileDeletingTask(new FileDeletingTask.Listener() {
                @Override
                public void onFileDeleted(File file) {
//                    Log.e("FileDeleted", file.getAbsolutePath());
                }
            });
            fileDeletingTask.execute(new File(audio.getAudioUri()));
        }
    }

    private void openAudio()
    {
        fileIOTasks.openAudioFile(audioUri);
    }
}
