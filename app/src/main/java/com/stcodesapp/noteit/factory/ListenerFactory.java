package com.stcodesapp.noteit.factory;

import android.app.Activity;
import android.view.View;

import com.google.android.flexbox.FlexboxLayout;
import com.stcodesapp.noteit.listeners.ContactListener;
import com.stcodesapp.noteit.listeners.RemoveImageListener;
import com.stcodesapp.noteit.models.Contact;

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
}
