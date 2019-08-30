package com.stcodesapp.noteit.tasks.functionalTasks.dataBackupTasks;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.database.Backup;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.Note;

import java.util.List;

public class BackupConvertionTask
{
    private Activity activity;
    private NoteDatabase noteDatabase;

    public BackupConvertionTask(Activity activity) {
        this.activity = activity;
        this.noteDatabase = NoteDatabase.getInstance(activity);
    }

    public String getJSONFromBackup()
    {
        List<Note> notes = noteDatabase.notesDao().getAllNoes();
        List<Contact> contacts = noteDatabase.contactDao().getAllContacts();
        List<Email> emails = noteDatabase.emailDao().getAllEmails();
        List<Audio> audio = noteDatabase.audioDao().getAllAudio();
        List<Image> images = noteDatabase.imageDao().getAllImage();
        Backup backup = new Backup(notes,contacts,emails,audio,images);
        String backupJSON = createJSONFromBackup(backup);
        return backupJSON;
    }

    private String createJSONFromBackup(Backup backup)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String jsonString = gson.toJson(backup);
        Logger.logMessage("JSONString",jsonString);
        return jsonString;
    }

    public Backup getBackupFromJSON(String jsonString)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Backup backup = gson.fromJson(jsonString,Backup.class);
        Logger.logMessage("Backup",backup.toString());
        return backup;
    }
}
