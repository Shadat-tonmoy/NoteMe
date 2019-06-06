package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;

import java.util.List;

public class AllEmailSelectionTasks extends AsyncTask<Void, Void, List<Email>>{



    public interface Listener{
        void onAllEmailFetched(List<Email> emails);
    }

    private Context context;
    private NoteDatabase noteDatabase;
    private Listener listener;

    public AllEmailSelectionTasks(Context context, Listener listener)
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
    protected List<Email> doInBackground(Void... voids) {
        return noteDatabase.emailDao().getAllEmails();
    }

    @Override
    protected void onPostExecute(List<Email> results) {
        super.onPostExecute(results);
        if(listener!=null)
            listener.onAllEmailFetched(results);
    }
}
