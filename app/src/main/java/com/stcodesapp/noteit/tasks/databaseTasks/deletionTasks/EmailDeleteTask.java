package com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;

public class EmailDeleteTask extends BaseDeletionTasks<Email> {

    public interface Listener{
        void onEmailDeleted(Email email);
    }

    private Listener listener;

    public EmailDeleteTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(Email email) {
        super.onPostExecute(email);
        listener.onEmailDeleted(email);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
