package com.stcodesapp.noteit.tasks.databaseTasks;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.ContactDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.EmailDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.AudioInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.ContactInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.EmailInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.ImageInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.NoteInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AudioSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ContactSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.EmailSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ImageSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.NoteComponentSelectionTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.NoteSelectTask;

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

    public NoteSelectTask getNoteSelectTask(NoteSelectTask.Listener listener)
    {
        NoteSelectTask noteSelectTask = new NoteSelectTask(context);
        noteSelectTask.setListener(listener);
        return noteSelectTask;
    }

    public EmailSelectTask getEmailSelectTask(EmailSelectTask.Listener listener)
    {
        EmailSelectTask emailSelectTask = new EmailSelectTask(context, ComponentType.EMAIL);
        emailSelectTask.setListener(listener);
        return emailSelectTask;
    }

    public EmailDeleteTask getEmailDeleteTask(EmailDeleteTask.Listener listener)
    {
        EmailDeleteTask emailDeleteTask = new EmailDeleteTask(context, ComponentType.EMAIL);
        emailDeleteTask.setListener(listener);
        return emailDeleteTask;
    }

    public AudioSelectTask getAudioSelectTask(AudioSelectTask.Listener listener)
    {
        AudioSelectTask AudioSelectTask = new AudioSelectTask(context, ComponentType.AUDIO);
        AudioSelectTask.setListener(listener);
        return AudioSelectTask;
    }

    public ImageSelectTask getImageSelectTask(ImageSelectTask.Listener listener)
    {
        ImageSelectTask ImageSelectTask = new ImageSelectTask(context, ComponentType.IMAGE);
        ImageSelectTask.setListener(listener);
        return ImageSelectTask;
    }


    public ContactSelectTask getContactSelectTask(ContactSelectTask.Listener listener)
    {
        ContactSelectTask ContactSelectTask = new ContactSelectTask(context, ComponentType.CONTACT);
        ContactSelectTask.setListener(listener);
        return ContactSelectTask;
    }


    public ContactDeleteTask getContactDeleteTask(ContactDeleteTask.Listener listener)
    {
        ContactDeleteTask contactDeleteTask = new ContactDeleteTask(context, ComponentType.CONTACT);
        contactDeleteTask.setListener(listener);
        return contactDeleteTask;
    }

    public NoteComponentSelectionTask getNoteComponentSelectionTask(NoteComponentSelectionTask.Listener listener,NoteComponents noteComponents)
    {
        NoteComponentSelectionTask noteComponentSelectionTask = new NoteComponentSelectionTask(context,noteComponents);
        noteComponentSelectionTask.setListener(listener);
        return noteComponentSelectionTask;

    }



}
