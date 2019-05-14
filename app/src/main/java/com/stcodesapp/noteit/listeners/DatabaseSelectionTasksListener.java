package com.stcodesapp.noteit.listeners;

import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AudioSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ContactSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.EmailSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ImageSelectTask;

import java.util.List;

public class DatabaseSelectionTasksListener implements EmailSelectTask.Listener, AudioSelectTask.Listener, ImageSelectTask.Listener, ContactSelectTask.Listener {


    public interface Listener{
        void onNoteComponentsFetched(NoteComponents noteComponents);
    }


    private DatabaseTasks databaseTasks;
    private Long noteId;
    private NoteComponents noteComponents;
    private Listener listener;


    public DatabaseSelectionTasksListener(DatabaseTasks databaseTasks, Long noteId) {
        this.databaseTasks = databaseTasks;
        this.noteId = noteId;
        this.noteComponents = new NoteComponents();
    }

    @Override
    public void onEmailFetched(List<Email> fetchedEmails) {
        noteComponents.setEmails(fetchedEmails);
        startFetchingAudio();

    }

    @Override
    public void onAudioFetched(List<Audio> fetchedAudio) {
        noteComponents.setChosenAudios(fetchedAudio);
        startFetchingContact();

    }

    @Override
    public void onContactFetched(List<Contact> fetchedContact) {
        noteComponents.setChosenContacts(fetchedContact);
        startFetchingImage();

    }

    @Override
    public void onImageFetched(List<Image> fetchedImage) {
        noteComponents.setChosenImages(fetchedImage);
        listener.onNoteComponentsFetched(noteComponents);

    }

    private void startFetchingContact()
    {
        databaseTasks.getContactSelectTask(this).execute(noteId);
    }

    private void startFetchingImage()
    {
        databaseTasks.getImageSelectTask(this).execute(noteId);
    }

    private void startFetchingAudio()
    {
        databaseTasks.getAudioSelectTask(this).execute(noteId);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
