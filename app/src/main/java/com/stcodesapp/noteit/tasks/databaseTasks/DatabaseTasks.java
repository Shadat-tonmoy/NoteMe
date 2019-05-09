package com.stcodesapp.noteit.tasks.databaseTasks;

import android.content.Context;

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
}
