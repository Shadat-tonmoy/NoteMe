package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Contact;

import java.util.List;

public class AllContactSelectionTasks extends AsyncTask<Void, Void, List<Contact>>{



    public interface Listener{
        void onAllContactFetched(List<Contact> contacts);
    }

    private Context context;
    private NoteDatabase noteDatabase;
    private Listener listener;

    public AllContactSelectionTasks(Context context, Listener listener)
    {
        this.context = context;
        noteDatabase = NoteDatabase.getInstance(context);
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Contact> doInBackground(Void... voids) {
        return noteDatabase.contactDao().getAllContacts();
    }

    @Override
    protected void onPostExecute(List<Contact> results) {
        super.onPostExecute(results);
        if(listener!=null)
            listener.onAllContactFetched(results);
    }
}
