package com.stcodesapp.noteit.tasks.networkingTasks;

import android.app.Activity;
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
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.database.Backup;
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

public class GoogleDriveAPITask extends AsyncTask<Integer,Void,Void>
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
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private Drive driveService;
    private BackupConvertionTask backupConvertionTask;
    private BackupToCloudListener backupToCloudListener;
    private RestoreFromCloudListener restoreFromCloudListener;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";

    public GoogleDriveAPITask(Activity activity, BackupConvertionTask backupConvertionTask)
    {
        this.activity = activity;
        this.backupConvertionTask = backupConvertionTask;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Integer... driveEventTypes)
    {
        int driveEventType = driveEventTypes[0];
        switch (driveEventType)
        {
            case EventTypes.BACKUP_TO_CLOUD_STORAGE_BUTTON_CLICKED:
                saveBackupFile();
                break;
            case EventTypes.RESTORE_FROM_CLOUD_STORAGE_BUTTON_CLICKED:
                readFile();
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
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

    public void readFile()
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
                Logger.logMessage("BackupContent",contents);
            }catch (Exception e)
            {

            }
        }
        else
        {
            Logger.logMessage("BackupFile","Not Found");
        }
    }

    public void saveBackupFile()
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

        }
        catch (UserRecoverableAuthIOException e)
        {
            activity.startActivityForResult(e.getIntent(), RequestCode.REQUEST_AUTHORIZATION_FOR_GOOGLE_DRIVE);

        }
        catch (Exception e)
        {
            Logger.logMessage("ExceptionInSavingFile",e+" ");

        }

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
}
