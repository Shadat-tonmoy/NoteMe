package com.stcodesapp.noteit.factory;

import android.app.Activity;
import android.net.Uri;
import android.view.View;

import com.google.android.flexbox.FlexboxLayout;
import com.stcodesapp.noteit.listeners.AudioListener;
import com.stcodesapp.noteit.listeners.ContactListener;
import com.stcodesapp.noteit.listeners.DatabaseSelectionTasksListener;
import com.stcodesapp.noteit.listeners.EmailListener;
import com.stcodesapp.noteit.listeners.RemoveImageListener;
import com.stcodesapp.noteit.listeners.DatabaseInsertTasksListener;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.FileIOTasks;

public class ListeningTasks {

    private Activity activity;

    public ListeningTasks(Activity activity) {
        this.activity = activity;
    }

    public RemoveImageListener getRemoveImageListener(FlexboxLayout container, View image)
    {
        return new RemoveImageListener(container,image);
    }

    public ContactListener getContactListener(Contact contact)
    {
        return new ContactListener(contact, activity);
    }

    public EmailListener getEmailListener(Email email)
    {
        return new EmailListener(email, activity);
    }

    public AudioListener getAudioListener(Audio audio, FileIOTasks fileIOTasks, Uri audioUri)
    {
        return new AudioListener(audio, fileIOTasks, audioUri);
    }

    public DatabaseInsertTasksListener getDBInsertTasksListener(DatabaseTasks databaseTasks, NoteComponents noteComponents){
        return new DatabaseInsertTasksListener(databaseTasks, noteComponents);
    }

    public DatabaseSelectionTasksListener getDBSelectTasksListener(DatabaseTasks databaseTasks,NoteComponents noteComponents){
        return new DatabaseSelectionTasksListener(databaseTasks,noteComponents);
    }


}
