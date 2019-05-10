package com.stcodesapp.noteit.listeners;

import android.util.Log;

import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.AudioInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.ContactInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.EmailInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.NoteInsertTask;

import java.util.List;

public class DatabaseTasksListener implements EmailInsertTask.Listener, NoteInsertTask.Listener, ContactInsertTask.Listener, AudioInsertTask.Listener {

    private DatabaseTasks databaseTasks;
    private NoteComponents noteComponents;
    private long insertedNoteId;

    public DatabaseTasksListener(DatabaseTasks databaseTasks, NoteComponents noteComponents) {
        this.databaseTasks = databaseTasks;
        this.noteComponents = noteComponents;
    }

    @Override
    public void onNoteInserted(long insertedId) {
        setInsertedNoteId(insertedId);
        noteComponents.setNoteIdToNoteComponents(insertedId);
        List<Email> emails = noteComponents.getEmails();
        Email[] emailArray = emails.toArray(new Email[0]);
        databaseTasks.getEmailInsertTask(this).execute(emailArray);

    }

    @Override
    public void onEmailInserted(int numberOfEmail) {
        List<Contact> contacts  = noteComponents.getChosenContacts();
        Contact[] contactArray = contacts.toArray(new Contact[0]);
        databaseTasks.getContactInsertTask(this).execute(contactArray);

    }


    @Override
    public void onContactInserted(int numberOfContact) {
        List<Audio> audios  = noteComponents.getChosenAudios();
        Audio[] audioArray = audios.toArray(new Audio[0]);
        databaseTasks.getAudioInsertTask(this).execute(audioArray);

    }
    @Override
    public void onAudioInserted(int numberOfEmail) {

    }



    public void setInsertedNoteId(long insertedNoteId) {
        this.insertedNoteId = insertedNoteId;
    }

    public long getInsertedNoteId() {
        return insertedNoteId;
    }

}
