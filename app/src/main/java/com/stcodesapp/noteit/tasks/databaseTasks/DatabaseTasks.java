package com.stcodesapp.noteit.tasks.databaseTasks;

import android.content.Context;

import com.stcodesapp.noteit.models.Audio;

public class DatabaseTasks {

    private final Context context;

    public DatabaseTasks(Context context) {
        this.context = context;
    }

    public NoteInsertTask getNoteInsertTask(NoteInsertTask.Listener listener)
    {
        NoteInsertTask noteInsertTask = new NoteInsertTask(context);
        noteInsertTask.setListener(listener);
        return noteInsertTask;
    }

    public EmailInsertTask getEmailInsertTask(EmailInsertTask.Listener listener)
    {
        EmailInsertTask emailInsertTask = new EmailInsertTask(context);
        emailInsertTask .setListener(listener);
        return emailInsertTask ;
    }

    public ContactInsertTask getContactInsertTask(ContactInsertTask.Listener listener)
    {
        ContactInsertTask contactInsertTask = new ContactInsertTask(context);
        contactInsertTask.setListener(listener);
        return contactInsertTask;
    }

    public AudioInsertTask getAudioInsertTask(AudioInsertTask.Listener listener)
    {
        AudioInsertTask audioInsertTask = new AudioInsertTask(context);
        audioInsertTask.setListener(listener);
        return audioInsertTask;
    }
    public ImageInsertTask getImageInsertTask(ImageInsertTask.Listener listener)
    {
        ImageInsertTask imageInsertTask = new ImageInsertTask(context);
        imageInsertTask.setListener(listener);
        return imageInsertTask;
    }
}
