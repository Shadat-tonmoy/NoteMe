package com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks;

import android.content.Context;
import android.widget.Toast;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.BaseSelectionTasks;

import java.util.List;

public class ContactDeleteTask extends BaseDeletionTasks<Contact> {

    public interface Listener{
        void onContactDeleted(Contact contact);
    }

    private Listener listener;

    public ContactDeleteTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(Contact contact) {
        super.onPostExecute(contact);
        listener.onContactDeleted(contact);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
