package com.stcodesapp.noteit.listeners;

import android.util.Log;

import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AudioSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ContactSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.EmailSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ImageSelectTask;

import java.util.List;

public class DatabaseSelectionTasksListener implements EmailSelectTask.Listener, AudioSelectTask.Listener, ImageSelectTask.Listener, ContactSelectTask.Listener {


    private DatabaseTasks databaseTasks;
    private Long noteId;
    public DatabaseSelectionTasksListener(DatabaseTasks databaseTasks, Long noteId) {
        this.databaseTasks = databaseTasks;
        this.noteId = noteId;
    }

    @Override
    public void onEmailFetched(List<Email> fetchedEmails) {
        Log.e("Email","Fetched Successfully of size "+fetchedEmails.size());
        for(int i=0;i<fetchedEmails.size();i++)
        {
            Email email = (Email) fetchedEmails.get(i);
            Log.e("Email",email.toString());
        }
        startFetchingAudio();

    }

    @Override
    public void onAudioFetched(List<Audio> fetchedAudio) {
        Log.e("Audio","Fetched Successfully of size "+fetchedAudio.size());
        for(int i=0;i<fetchedAudio.size();i++)
        {
            Audio audio = fetchedAudio.get(i);
            Log.e("Audio",audio.toString());
        }
        startFetchingContact();

    }

    @Override
    public void onContactFetched(List<Contact> fetchedContact) {
        Log.e("Contact","Fetched Successfully of size "+fetchedContact.size());
        for(int i=0;i<fetchedContact.size();i++)
        {
            Contact contact=  fetchedContact.get(i);
            Log.e("Contact",contact.toString());
        }
        startFetchingImage();

    }

    @Override
    public void onImageFetched(List<Image> fetchedImage) {
        Log.e("Image","Fetched Successfully of size "+fetchedImage.size());
        for(int i=0;i<fetchedImage.size();i++)
        {
            Image image = fetchedImage.get(i);
            Log.e("Image",image.toString());
        }
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
}
