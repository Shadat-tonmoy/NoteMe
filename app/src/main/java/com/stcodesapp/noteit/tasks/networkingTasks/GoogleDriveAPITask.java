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
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.database.Backup;
import com.stcodesapp.noteit.tasks.functionalTasks.dataBackupTasks.BackupConvertionTask;
import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleDriveAPITask extends AsyncTask<Void,Void,Void>
{

    private Activity activity;
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private Drive driveService;
    private BackupConvertionTask backupConvertionTask;

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
    protected Void doInBackground(Void... voids) {
        saveBackupFile();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
//        InputStream in = GoogleDriveAPITask.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        InputStream in = activity.getAssets().open(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(UtilityTasks.getTempDirectoryPath(activity))))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public void test()
    {
        try
        {
            final NetHttpTransport HTTP_TRANSPORT =  new com.google.api.client.http.javanet.NetHttpTransport();

            Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            // Print the names and IDs for up to 10 files.
            FileList result = service.files().list()
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
            List<File> files = result.getFiles();
            if (files == null || files.isEmpty()) {
                System.out.println("No files found.");
            } else {
                System.out.println("Files:");
                for (File file : files) {
                    System.out.printf("%s (%s)\n", file.getName(), file.getId());
                }
            }

        }catch (Exception e)
        {
            Logger.logMessage("ExceptionInAPI",e+" ");
        }

    }

    public void queryFiles()
    {
        try {
            /*Drive.Files files = driveService.files();
            Drive.Files.List list = files.list();*/
            FileList fileList = driveService.files().list().setSpaces("drive").execute();
            List<File> files = fileList.getFiles();
            Logger.logMessage("Files",fileList+" size "+files.size());
        }catch (UserRecoverableAuthIOException e)
        {
            Logger.logMessage("ExecptionInAPI",e+" ");
            activity.startActivityForResult(e.getIntent(), RequestCode.REQUEST_AUTHORIZATION_FOR_GOOGLE_DRIVE);

        }catch (Exception e)
        {
            Logger.logMessage("ExecptionInAPI2",e+" ");
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

        }catch (Exception e)
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
                    .setId(Constants.CLOUD_BACKUP_FILE_ID)
                    .setName(Constants.CLOUD_BACKUP_FILE_NAME);

            File googleFile = driveService.files().create(metadata).execute();
            if (googleFile == null) {
                throw new IOException("Null result when requesting file creation.");
            }
            return googleFile.getId();

        }catch (Exception e)
        {
            Logger.logMessage("ExceptionInCreatingFile",e+" ");
        }
        return Constants.CLOUD_BACKUP_FILE_ID;

    }


    public void setDriveService(Drive driveService) {
        this.driveService = driveService;
    }
}
