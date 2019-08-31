package com.stcodesapp.noteit.tasks.networkingTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.database.Backup;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.tasks.functionalTasks.DialogManagementTask;
import com.stcodesapp.noteit.tasks.functionalTasks.dataBackupTasks.BackupConvertionTask;
import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleDriveAPITask extends AsyncTask<Integer,Void,Boolean>
{

    public interface BackupToCloudListener
    {
        void onBackupToCloudSuccess();

        void onBackupToCloudFailed();
    }

    public interface RestoreFromCloudListener
    {
        void onRestoreFromCloudSuccess();

        void onRestoreFromCloudFailed();
    }

    private Activity activity;
    private Drive driveService;
    private BackupConvertionTask backupConvertionTask;
    private BackupToCloudListener backupToCloudListener;
    private RestoreFromCloudListener restoreFromCloudListener;
    private ProgressDialog progressDialog;
    private int driveEventType;

    public GoogleDriveAPITask(Activity activity, BackupConvertionTask backupConvertionTask)
    {
        this.activity = activity;
        this.backupConvertionTask = backupConvertionTask;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog();
    }

    @Override
    protected Boolean doInBackground(Integer... driveEventTypes)
    {
        int driveEventType = driveEventTypes[0];
        this.driveEventType = driveEventType;
        switch (driveEventType)
        {
            case EventTypes.BACKUP_TO_CLOUD_STORAGE_BUTTON_CLICKED:
                return saveBackupFile();
            case EventTypes.RESTORE_FROM_CLOUD_STORAGE_BUTTON_CLICKED:
                return readFile();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        hideProgressDialog();

        switch (driveEventType)
        {
            case EventTypes.BACKUP_TO_CLOUD_STORAGE_BUTTON_CLICKED:
            {
                if(backupToCloudListener!=null)
                {
                    if(result)
                        backupToCloudListener.onBackupToCloudSuccess();
                    else backupToCloudListener.onBackupToCloudFailed();
                }
            }
            case EventTypes.RESTORE_FROM_CLOUD_STORAGE_BUTTON_CLICKED:
            {
                if(restoreFromCloudListener!=null)
                {
                    if(result)
                        restoreFromCloudListener.onRestoreFromCloudSuccess();
                    else restoreFromCloudListener.onRestoreFromCloudFailed();
                }
            }
        }
    }


    public String  queryFiles()
    {
        try {
            FileList fileList = driveService.files().list().setSpaces("drive").execute();
            List<File> files = fileList.getFiles();
            for(File file:files)
            {
                if(file.getName().equals(Constants.CLOUD_BACKUP_FILE_NAME))
                {
                   return file.getId();
                }
            }
        }catch (UserRecoverableAuthIOException e)
        {
            activity.startActivityForResult(e.getIntent(), RequestCode.REQUEST_AUTHORIZATION_FOR_GOOGLE_DRIVE);

        }catch (Exception e)
        {
            Logger.logMessage("ExecptionInAPI2",e+" ");
        }
        return Constants.EMPTY_STRING;
    }

    public boolean readFile()
    {
        final String fileID = queryFiles();
        if(!fileID.equals(Constants.EMPTY_STRING))
        {
            try
            {
                InputStream inputStream = driveService.files().get(fileID).executeMediaAsInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String contents = stringBuilder.toString();
                Backup backup = backupConvertionTask.getBackupFromJSON(contents);
                clearExistingDB();
                insertBackupData(backup);
                Logger.logMessage("BackupContent",contents);
                return true;
            }catch (Exception e)
            {
                return false;

            }
        }
        else
        {
            Logger.logMessage("BackupFile","Not Found");
            return false;
        }
    }

    public boolean saveBackupFile()
    {
        try
        {
            String fileId = createBackupFile();
            String backupContent = backupConvertionTask.getJSONFromBackup();
            File metadata = new File().setName(Constants.CLOUD_BACKUP_FILE_NAME);
            // Convert content to an AbstractInputStreamContent instance.
            ByteArrayContent contentStream = ByteArrayContent.fromString("text/plain", backupContent);
            // Update the metadata and contents.
            File file = new File().setName(Constants.CLOUD_BACKUP_FILE_NAME);
            driveService.files().update(fileId, metadata, contentStream).execute();
            Logger.logMessage("SavedSuccess",backupContent);
            return true;

        }
        catch (UserRecoverableAuthIOException e)
        {
            activity.startActivityForResult(e.getIntent(), RequestCode.REQUEST_AUTHORIZATION_FOR_GOOGLE_DRIVE);

        }
        catch (Exception e)
        {
            Logger.logMessage("ExceptionInSavingFile",e+" ");
            return false;

        }
        return false;

    }

    public String createBackupFile()
    {
        try
        {
            File metadata = new File()
                    .setParents(Collections.singletonList("root"))
                    .setMimeType("text/plain")
                    .setName(Constants.CLOUD_BACKUP_FILE_NAME);

            File googleFile = driveService.files().create(metadata).execute();
            if (googleFile == null) {
                throw new IOException("Null result when requesting file creation.");
            }
            return googleFile.getId();

        }
        catch (UserRecoverableAuthIOException e)
        {
            activity.startActivityForResult(e.getIntent(), RequestCode.REQUEST_AUTHORIZATION_FOR_GOOGLE_DRIVE);

        }
        catch (Exception e)
        {
            Logger.logMessage("ExceptionInCreatingFile",e+" ");
        }
        return Constants.CLOUD_BACKUP_FILE_ID;

    }


    public void setDriveService(Drive driveService) {
        this.driveService = driveService;
    }

    public void setBackupToCloudListener(BackupToCloudListener backupToCloudListener) {
        this.backupToCloudListener = backupToCloudListener;
    }

    public void setRestoreFromCloudListener(RestoreFromCloudListener restoreFromCloudListener) {
        this.restoreFromCloudListener = restoreFromCloudListener;
    }
    
    private void showProgressDialog()
    {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        String message = activity.getResources().getString(R.string.backup_progress_message);
        if(driveEventType == EventTypes.RESTORE_FROM_CLOUD_STORAGE_BUTTON_CLICKED)
            message = activity.getResources().getString(R.string.restore_progress_message);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    private void hideProgressDialog()
    {
        if(progressDialog!=null && progressDialog.isShowing())
            progressDialog.dismiss();
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
    
    
}
