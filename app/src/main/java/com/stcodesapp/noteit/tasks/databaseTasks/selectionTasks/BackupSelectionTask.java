package com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.database.Backup;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.Note;

import java.util.List;

public class BackupSelectionTask extends AsyncTask<Void,Void,Backup>
{

    public interface Listener
    {
        void onBackupElementsFetched(Backup backup);

    }

    private Activity activity;
    private NoteDatabase noteDatabase;
    private Listener listener;


    public BackupSelectionTask(Activity activity) {
        this.activity = activity;
        this.noteDatabase = NoteDatabase.getInstance(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Backup doInBackground(Void... voids) {
        return getBackupElements();
    }

    @Override
    protected void onPostExecute(Backup backup) {
        super.onPostExecute(backup);
//        Logger.logMessage("Backup",backup.toString());
        convertBackupToJSON(backup);
        if(listener!=null)
            listener.onBackupElementsFetched(backup);

    }

    private Backup getBackupElements()
    {
        List<Note> notes = noteDatabase.notesDao().getAllNoes();
        List<Contact> contacts = noteDatabase.contactDao().getAllContacts();
        List<Email> emails = noteDatabase.emailDao().getAllEmails();
        List<Audio> audio = noteDatabase.audioDao().getAllAudio();
        List<Image> images = noteDatabase.imageDao().getAllImage();
        return new Backup(notes,contacts,emails,audio,images);
    }


    private void convertBackupToJSON(Backup backup)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String jsonString = gson.toJson(backup);
        Logger.logMessage("JSONString",jsonString);
        convertJSONToBackup(jsonString);
    }

    private void convertJSONToBackup(String jsonString)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Backup backup = gson.fromJson(jsonString,Backup.class);
        Logger.logMessage("Backup",backup.toString());
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}

