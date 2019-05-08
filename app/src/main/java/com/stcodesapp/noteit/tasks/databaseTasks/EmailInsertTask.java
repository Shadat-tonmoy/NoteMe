package com.stcodesapp.noteit.tasks.databaseTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.stcodesapp.noteit.dao.EmailDao;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Email;

public class EmailInsertTask extends AsyncTask<Email, Void, Void> {

    public interface Listener{
        void onEmailInserted();
    }


    private final Context context;
    private NoteDatabase noteDatabase;
    private Listener listener;


    public EmailInsertTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        noteDatabase = NoteDatabase.getInstance(context);
    }

    @Override
    protected Void doInBackground(Email... emails) {
        noteDatabase.emailDao().insertEmails(emails);
        Log.e("Email","Inserting....");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onEmailInserted();

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
