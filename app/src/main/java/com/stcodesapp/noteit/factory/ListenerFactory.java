package com.stcodesapp.noteit.factory;

import android.app.Activity;
import android.net.Uri;
import android.view.View;

import com.google.android.flexbox.FlexboxLayout;
import com.stcodesapp.noteit.listeners.AudioListener;
import com.stcodesapp.noteit.listeners.ContactListener;
import com.stcodesapp.noteit.listeners.EmailListener;
import com.stcodesapp.noteit.listeners.RemoveImageListener;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.tasks.functionalTasks.FileIOTasks;

public class ListenerFactory {

    private Activity activity;

    public ListenerFactory(Activity activity) {
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

    public EmailListener getEEmailListener(Email email)
    {
        return new EmailListener(email, activity);
    }

    public AudioListener getAudioListener(Audio audio, FileIOTasks fileIOTasks, Uri audioUri)
    {
        return new AudioListener(audio, fileIOTasks, audioUri);
    }


}
