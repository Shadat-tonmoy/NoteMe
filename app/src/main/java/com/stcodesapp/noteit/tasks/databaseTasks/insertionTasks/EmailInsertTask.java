package com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.stcodesapp.noteit.dao.EmailDao;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Email;

public class EmailInsertTask extends AsyncTask<Email, Void, Integer> {

    public interface Listener{
        void onEmailInserted(int numberOfEmail);
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
    protected Integer doInBackground(Email... emails) {
        noteDatabase.emailDao().insertEmails(emails);
        return emails.length;
    }

    @Override
    protected void onPostExecute(Integer numberOfEmail) {
        super.onPostExecute(numberOfEmail);
        listener.onEmailInserted(numberOfEmail);

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
