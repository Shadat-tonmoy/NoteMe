package com.stcodesapp.noteit.listeners.databaseTasksListener;

import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.EmailInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.NoteInsertTask;

import java.util.List;

public class DatabaseTasksListener implements EmailInsertTask.Listener, NoteInsertTask.Listener {

    private DatabaseTasks databaseTasks;
    private NoteComponents noteComponents;

    public DatabaseTasksListener(DatabaseTasks databaseTasks, NoteComponents noteComponents) {
        this.databaseTasks = databaseTasks;
        this.noteComponents = noteComponents;
    }


    @Override
    public void onEmailInserted() {

    }

    @Override
    public void onNoteInserted(long insertedId) {
        noteComponents.setNoteIdToEmails(insertedId);
        List<Email> emails = noteComponents.getEmails();
        Email[] emailArray = emails.toArray(new Email[0]);
        databaseTasks.getEmailInsertTask(this).execute(emailArray);

    }
}
