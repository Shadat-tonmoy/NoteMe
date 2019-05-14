package com.stcodesapp.noteit.listeners;

import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.AudioInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.ContactInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.EmailInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.ImageInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.NoteInsertTask;

import java.util.List;

public class DatabaseInsertTasksListener implements EmailInsertTask.Listener, NoteInsertTask.Listener, ContactInsertTask.Listener, AudioInsertTask.Listener, ImageInsertTask.Listener {

    private DatabaseTasks databaseTasks;
    private NoteComponents noteComponents;
    private long insertedNoteId;

    public DatabaseInsertTasksListener(DatabaseTasks databaseTasks, NoteComponents noteComponents) {
        this.databaseTasks = databaseTasks;
        this.noteComponents = noteComponents;
    }

    @Override
    public void onNoteInserted(long insertedId) {
        setInsertedNoteId(insertedId);
        noteComponents.setNoteIdToNoteComponents(insertedId);
        insertEmail();

    }

    @Override
    public void onEmailInserted(int numberOfEmail) {
        insertContact();
    }


    @Override
    public void onContactInserted(int numberOfContact) {
        insertAudio();
    }
    @Override
    public void onAudioInserted(int numberOfEmail) {
        insertImage();
    }

    @Override
    public void onImageInserted(int numberOfImages) {

    }

    public void setInsertedNoteId(long insertedNoteId) {
        this.insertedNoteId = insertedNoteId;
    }

    public long getInsertedNoteId() {
        return insertedNoteId;
    }

    private void insertAudio()
    {
        List<Audio> audios  = noteComponents.getChosenAudios();
        Audio[] audioArray = audios.toArray(new Audio[0]);
        databaseTasks.getAudioInsertTask(this).execute(audioArray);
    }

    private void insertImage()
    {
        List<Image> images = noteComponents.getChosenImages();
        Image[] imageArray = images.toArray(new Image[0]);
        databaseTasks.getImageInsertTask(this).execute(imageArray);
    }

    private void insertContact()
    {
        List<Contact> contacts  = noteComponents.getChosenContacts();
        Contact[] contactArray = contacts.toArray(new Contact[0]);
        databaseTasks.getContactInsertTask(this).execute(contactArray);
    }

    private void insertEmail()
    {
        List<Email> emails = noteComponents.getEmails();
        Email[] emailArray = emails.toArray(new Email[0]);
        databaseTasks.getEmailInsertTask(this).execute(emailArray);
    }
}
