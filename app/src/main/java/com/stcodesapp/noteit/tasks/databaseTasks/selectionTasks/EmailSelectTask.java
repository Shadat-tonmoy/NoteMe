package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Email;

import java.util.List;

public class EmailSelectTask extends BaseSelectionTasks<Email> {

    public interface Listener{
        void onEmailFetched(List<Email> fetchedEmails);
    }

    private Listener listener;

    public EmailSelectTask(Context context, ComponentType componentType)
    {
        super(context, componentType);
    }

    @Override
    protected void onPostExecute(List<Email> results) {
        listener.onEmailFetched(results);

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
