package com.stcodesapp.noteit.tasks.functionalTasks.dataBackupTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
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

import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BackupRestoringTask extends AsyncTask<Integer,Void,Boolean>
{

    public interface Listener
    {
        void onBackupRestoreFinished();

        void onBackupRestoreFailed();

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
    protected Boolean doInBackground(Integer... backupTypes)
    {
        int backupType = backupTypes[0];
        int localStorageType = backupTypes[1];
        return restoreDatabase(backupType,localStorageType);

    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(listener!=null)
        {
            if(result)
                listener.onBackupRestoreFinished();
            else listener.onBackupRestoreFailed();
        }

    }

    private boolean restoreDatabase(int backupType,int localStorageType)
    {
        if(backupType==Constants.LOCAL_STORAGE_RESTORE)
        {
            return readJSONFile(localStorageType);
        }
        else
        {
            return false;
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

    private boolean readJSONFile(int localStorageType)
    {
        File file = fileIOTasks.getFileForReading(Constants.BACKUP_DIRECTORY,Constants.BACKUP_FILE_NAME,Constants.JSON_FILE_EXT);
        if(localStorageType==Constants.LOCAL_STORAGE_SD_CARD)
        {
            file = fileIOTasks.getFileForReadingInSDCard(Constants.BACKUP_DIRECTORY,Constants.BACKUP_FILE_NAME,Constants.JSON_FILE_EXT);
        }
        if(file==null || !file.exists())
        {
//            Toast.makeText(activity, activity.getResources().getString(R.string.backup_file_not_found), Toast.LENGTH_LONG).show();
            return false;
        }
        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(new FileReader(file));
            Backup backup = gson.fromJson(jsonReader,Backup.class);
            Logger.logMessage("BackupFromJSON",backup.toString());
            clearExistingDB();
            insertBackupData(backup);
            return true;
        }catch (Exception e)
        {
            Logger.logMessage("Exception",e.getMessage());
            return false;
        }
    }

    private void clearExistingDB()
    {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(activity);
        noteDatabase.clearAllTables();
    }

    private void insertBackupData(Backup backup)
    {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(activity);
        Note[] notes = backup.getNotes().toArray(new Note[0]);
        Audio[] audios = backup.getAudio().toArray(new Audio[0]);
        Contact[] contacts = backup.getContacts().toArray(new Contact[0]);
        Email[] emails = backup.getEmails().toArray(new Email[0]);
        Image[] images = backup.getImages().toArray(new Image[0]);
        noteDatabase.notesDao().insertAllNote(notes);
        noteDatabase.audioDao().insertAudio(audios);
        noteDatabase.contactDao().insertContact(contacts);
        noteDatabase.emailDao().insertEmails(emails);
        noteDatabase.imageDao().insertImage(images);
        Logger.logMessage("BackupElements",notes.length+" "+audios.length+" "+contacts.length+" "+emails.length+" "+images.length);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}

