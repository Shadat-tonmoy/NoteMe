package com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;

public class ContactInsertTask extends AsyncTask<Contact, Void, Integer> {

    public interface Listener{
        void onContactInserted(int numberOfContact);
    }


    private final Context context;
    private NoteDatabase noteDatabase;
    private Listener listener;


    public ContactInsertTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        noteDatabase = NoteDatabase.getInstance(context);
    }

    @Override
    protected Integer doInBackground(Contact... contacts) {
        noteDatabase.contactDao().insertContact(contacts);
        return contacts.length;
    }

    @Override
    protected void onPostExecute(Integer numberOfContact) {
        super.onPostExecute(numberOfContact);
        listener.onContactInserted(numberOfContact);

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
