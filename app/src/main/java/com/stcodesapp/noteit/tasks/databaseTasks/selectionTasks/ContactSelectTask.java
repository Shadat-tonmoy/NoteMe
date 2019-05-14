package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;

import java.util.List;

public class ContactSelectTask extends NoteComponentSelectionTasks<Contact>{

    public interface Listener{
        void onContactFetched(List<Contact> fetchedContact);
    }

    private Listener listener;

    public ContactSelectTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(List<Contact> results) {
        listener.onContactFetched(results);

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
