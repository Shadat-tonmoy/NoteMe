package com.stcodesapp.noteit.listeners.databaseTasksListener;

import android.util.Log;

import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.ContactInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.EmailInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.NoteInsertTask;

import java.util.List;

public class DatabaseTasksListener implements EmailInsertTask.Listener, NoteInsertTask.Listener, ContactInsertTask.Listener {

    private DatabaseTasks databaseTasks;
    private NoteComponents noteComponents;
    private long insertedNoteId;

    public DatabaseTasksListener(DatabaseTasks databaseTasks, NoteComponents noteComponents) {
        this.databaseTasks = databaseTasks;
        this.noteComponents = noteComponents;
    }


    @Override
    public void onEmailInserted(int numberOfEmail) {
        Log.e("Email","Inserted");
        noteComponents.setNoteIdToContacts(getInsertedNoteId());
        List<Contact> contacts  = noteComponents.getChosenContacts();
        Contact[] contactArray = contacts.toArray(new Contact[0]);
        databaseTasks.getContactInsertTask(this).execute(contactArray);

    }

    @Override
    public void onNoteInserted(long insertedId) {
        setInsertedNoteId(insertedId);
        noteComponents.setNoteIdToEmails(insertedId);
        List<Email> emails = noteComponents.getEmails();
        Email[] emailArray = emails.toArray(new Email[0]);
        databaseTasks.getEmailInsertTask(this).execute(emailArray);

    }

    @Override
    public void onContactInserted(int numberOfContact) {
        Log.e("Contact",numberOfContact+" inserted...");

    }

    public void setInsertedNoteId(long insertedNoteId) {
        this.insertedNoteId = insertedNoteId;
    }

    public long getInsertedNoteId() {
        return insertedNoteId;
    }


}
