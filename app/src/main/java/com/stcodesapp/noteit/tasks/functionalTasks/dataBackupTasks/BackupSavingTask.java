package com.stcodesapp.noteit.tasks.functionalTasks.dataBackupTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.database.Backup;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileIOTasks;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class BackupSavingTask extends AsyncTask<Integer,Void,Void>
{

    public interface Listener
    {
        void onBackupProcessFinished();

    }

    private Activity activity;
    private NoteDatabase noteDatabase;
    private Listener listener;
    private FileIOTasks fileIOTasks;


    public BackupSavingTask(Activity activity, FileIOTasks fileIOTasks) {
        this.activity = activity;
        this.noteDatabase = NoteDatabase.getInstance(activity);
        this.fileIOTasks = fileIOTasks;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Integer... backupTypes)
    {
        int backupType = backupTypes[0];
        int localStorageOption = backupTypes[1];
        backupDatabase(backupType,localStorageOption);
        return null;
    }

    @Override
    protected void onPostExecute(Void backup) {
        super.onPostExecute(backup);
        if(listener!=null)
        {
            listener.onBackupProcessFinished();
        }

    }

    private Backup backupDatabase(int backupType,int localStorageOption)
    {
        List<Note> notes = noteDatabase.notesDao().getAllNoes();
        List<Contact> contacts = noteDatabase.contactDao().getAllContacts();
        List<Email> emails = noteDatabase.emailDao().getAllEmails();
        List<Audio> audio = noteDatabase.audioDao().getAllAudio();
        List<Image> images = noteDatabase.imageDao().getAllImage();
        Backup backup = new Backup(notes,contacts,emails,audio,images);
        String backupJSON = convertBackupToJSON(backup);
        if(backupType==Constants.LOCAL_STORAGE_BACKUP)
            writeJSONToFile(backupJSON,localStorageOption);
        else
        {
            //store json into google drive
        }
        return backup;
    }


    private String convertBackupToJSON(Backup backup)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String jsonString = gson.toJson(backup);
        Logger.logMessage("JSONString",jsonString);
        convertJSONToBackup(jsonString);
        return jsonString;
    }

    private void convertJSONToBackup(String jsonString)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Backup backup = gson.fromJson(jsonString,Backup.class);
        Logger.logMessage("Backup",backup.toString());
    }

    private boolean writeJSONToFile(String jsonString, int localStorageOption)
    {
//        File file = fileIOTasks.getFileForSaving(Constants.BACKUP_DIRECTORY,Constants.BACKUP_FILE_NAME,Constants.JSON_FILE_EXT);
        File file = fileIOTasks.getFileForSaving(Constants.BACKUP_DIRECTORY,Constants.BACKUP_FILE_NAME,Constants.JSON_FILE_EXT);
        if(localStorageOption==Constants.LOCAL_STORAGE_SD_CARD)
        {
            if(fileIOTasks.isSDCardAvailable())
                file = fileIOTasks.getFileForSavingInSDCard(Constants.BACKUP_DIRECTORY,Constants.BACKUP_FILE_NAME,Constants.JSON_FILE_EXT);
            else
            {
                Toast.makeText(activity, activity.getString(R.string.sd_card_not_available), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonString);
            fileWriter.flush();
            return true;
        }catch (Exception e)
        {
            Logger.logMessage("Exception",e.getMessage());
            return false;
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}

