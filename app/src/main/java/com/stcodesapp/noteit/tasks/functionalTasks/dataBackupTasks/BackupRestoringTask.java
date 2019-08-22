package com.stcodesapp.noteit.tasks.functionalTasks.dataBackupTasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
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

import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BackupRestoringTask extends AsyncTask<Integer,Void,Void>
{

    public interface Listener
    {
        void onBackupRestoreFinished();

    }

    private Activity activity;
    private NoteDatabase noteDatabase;
    private Listener listener;
    private FileIOTasks fileIOTasks;


    public BackupRestoringTask(Activity activity, FileIOTasks fileIOTasks) {
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
        restoreDatabase(backupType);
        return null;
    }

    @Override
    protected void onPostExecute(Void backup) {
        super.onPostExecute(backup);
        if(listener!=null)
        {
            listener.onBackupRestoreFinished();
        }

    }

    private void restoreDatabase(int backupType)
    {
        if(backupType==Constants.LOCAL_STORAGE_RESTORE)
            readJSONFile();
        else
        {
            //store json into google drive
        }
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

    private boolean readJSONFile()
    {
//        File file = fileIOTasks.getFileForSaving(Constants.BACKUP_DIRECTORY,Constants.BACKUP_FILE_NAME,Constants.JSON_FILE_EXT);
        File file = fileIOTasks.getFileForSaving(Constants.BACKUP_DIRECTORY,Constants.BACKUP_FILE_NAME,Constants.JSON_FILE_EXT);
        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(new FileReader(file));
            Backup backup = gson.fromJson(jsonReader,Backup.class);
            Logger.logMessage("BackupFromJSON",backup.toString());
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

