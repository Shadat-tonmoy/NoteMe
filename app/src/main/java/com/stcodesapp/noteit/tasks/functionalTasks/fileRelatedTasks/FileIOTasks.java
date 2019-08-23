package com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.tasks.utilityTasks.AppPermissionTrackingTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;

import java.io.File;
import java.io.IOException;
import java.io.File;
import java.util.List;

public class FileIOTasks {

    private Activity activity;

    public FileIOTasks(Activity activity) {
        this.activity = activity;
    }

    public void openFilePicker()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Tags.REQUEST_CODE, RequestCode.OPEN_IMAGE_FILE);
        intent.setType("text/plain");
        activity.startActivityForResult(intent, RequestCode.OPEN_IMAGE_FILE);
//        Log.e("DataDir",Environment.getDataDirectory().getAbsolutePath()+"");
    }

    public void openFilePickerForImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, RequestCode.OPEN_IMAGE_FILE);
    }

    public void openFilePickerForAudio()
    {
        Intent intent;

        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType(Constants.AUDIO_DATA_TYPE);
            activity.startActivityForResult(intent, RequestCode.OPEN_AUDIO_LIST);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(Constants.AUDIO_DATA_TYPE);
            activity.startActivityForResult(intent, RequestCode.OPEN_AUDIO_LIST);
        }
    }

    public void openContactPicker()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        activity.startActivityForResult(intent, RequestCode.OPEN_CONTACT_LIST);
    }

    public Contact readContact(Intent intent)
    {
        Uri contactData = intent.getData();
        String number,name;
        Contact contact = null;
        if(contactData!=null)
        {
            Cursor cursor = activity.getContentResolver().query(contactData, null, null, null, null);
            if(cursor!=null)
            {
                cursor.moveToFirst();
                String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                Cursor phones = activity.getContentResolver().query
                        (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = " + contactId, null, null);
                if(phones!=null)
                {
                    while (phones.moveToNext())
                    {
                        number = phones.getString(phones.getColumnIndex
                                (ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("[-() ]", "");
                        name = phones.getString(phones.getColumnIndex
                                (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        contact = new Contact(number,name);
                    }
                    phones.close();
                }
                cursor.close();
            }
        }
        return contact;
    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public Image getImageFromURI(Uri data,boolean isCaptured)
    {
        Image image = null;
        if(data!=null)
            image = new Image(data.toString(),isCaptured);
        return image;

    }
    public Audio getAudioFileFromURI(Uri data)
    {
        Audio audio = null;
        try {

            String[] filePathColumn = {MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.SIZE};
            Cursor cursor = activity.getContentResolver().query(data, null, null, null, null);
            if(cursor.moveToFirst()){
                int nameIndex = cursor.getColumnIndex(filePathColumn[0]);
                int sizeIndex= cursor.getColumnIndex(filePathColumn[1]);
                String name = cursor.getString(nameIndex);
                String size = cursor.getString(sizeIndex);
                audio = new Audio(name,size, data.toString(),false);
            }
            cursor.close();
        }
        catch (Exception e)
        {
//            Log.e("Exception",e.getMessage());
        }
        return audio;

    }


    public Audio getAudioFileForRecordedVoice(String fileName, Uri data)
    {
        Audio audio = null;
        audio = new Audio(fileName,Constants.ZERO_STRING,data.toString(),true);
        return audio;

    }


    public String getStoragePath()
    {
        File storage = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED)[0];
        File fileDirectory = new File(UtilityTasks.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY);
        return fileDirectory.getAbsolutePath();
    }
    public void openAudioFile(Uri fileURI)
    {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(fileURI, Constants.OPEN_AUDIO_FILE_TYPE);
        File file = new File(fileURI.getPath());
        allowURIReadPermission(intent,fileURI);
        activity.startActivity(intent);
    }

    public Uri getUriForRecordedAudio(String filePath)
    {
        File audioFile = new File(filePath);
        Cursor cursor = activity.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media._ID },
                MediaStore.Audio.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();

            Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (audioFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Audio.Media.DATA, filePath);
                Uri uri =  activity.getContentResolver().insert(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
                return uri;
            } else {
                return null;
            }
        }
        return null;

    }

    public void openAudioFile(String fileId)
    {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        File file = new File("file:/"+fileId);
        Uri fileURI = Uri.parse(fileId);
//        Log.e("Audio",fileURI.getPath());
        intent.setDataAndType(fileURI, Constants.OPEN_AUDIO_FILE_TYPE);
        allowURIReadPermission(intent,fileURI);
        activity.startActivity(intent);
    }

    private void allowURIReadPermission(Intent intent,Uri uri)
    {
        try {
            List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                activity.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

        }catch (Exception e)
        {
//            Log.e("Exceepion",e.getMessage());
        }

    }

    public boolean isSDCardAvailable()
    {
        File[] storages = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED);
        return storages.length>1;
    }

    public File getFileForSaving(String directoryName, String fileName,String extension)
    {
        File[] storages = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED);
        for(File storage:storages)
        {
            Logger.logMessage("Storage",storage.getAbsolutePath());
        }

        if(isExternalStorageWritable())
        {
            if(AppPermissionTrackingTasks.hasWriteExternalStoragePermission(activity))
            {
                File storage = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED)[0];
                File fileDirectory = new File(UtilityTasks.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY);
                if(directoryName!=null)
                    fileDirectory = new File(UtilityTasks.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY+directoryName+"/");
                File directory = new File(fileDirectory.getAbsolutePath());
                if(!directory.exists())
                {
                    boolean result = directory.mkdirs();
                    Logger.logMessage("ResultOf","CreatingDir "+result+" of "+directory.getAbsolutePath());
                }

                File file = new File(directory, fileName+ extension);
                Logger.logMessage("WillCreateFile",file.getAbsolutePath());
                if(!file.exists()) {
                    try {
                        boolean result  = file.createNewFile();
                        Logger.logMessage("ResultOf","CreatingFile "+result);
                    } catch (IOException e) {
                        Logger.logMessage("Exception",e.getMessage());
                        e.printStackTrace();
                    }
                }
                return file;
            }

        }
        return null;
    }


    public File getFileForReading(String directoryName, String fileName,String extension)
    {
        if(isExternalStorageWritable())
        {
            if(AppPermissionTrackingTasks.hasWriteExternalStoragePermission(activity))
            {
                File storage = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED)[0];
                File fileDirectory = new File(UtilityTasks.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY);
                if(directoryName!=null)
                    fileDirectory = new File(UtilityTasks.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY+directoryName+"/");
                File directory = new File(fileDirectory.getAbsolutePath());
                File file = new File(directory, fileName+ extension);
                return file;
            }

        }
        return null;
    }

    public File getFileForSavingInSDCard(String directoryName, String fileName,String extension)
    {
        File[] storages = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED);
        for(File storage:storages)
        {
            Logger.logMessage("Storage",storage.getAbsolutePath());
        }

        if(isExternalStorageWritable())
        {
            if(AppPermissionTrackingTasks.hasWriteExternalStoragePermission(activity))
            {
                File storage = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED)[1];
                File fileDirectory = new File(UtilityTasks.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY);
                if(directoryName!=null)
                    fileDirectory = new File(UtilityTasks.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY+directoryName+"/");
                File directory = new File(fileDirectory.getAbsolutePath());
                if(!directory.exists())
                {
                    boolean result = directory.mkdirs();
                    Logger.logMessage("ResultOf","CreatingDir "+result+" of "+directory.getAbsolutePath());
                }

                File file = new File(directory, fileName+ extension);
                Logger.logMessage("WillCreateFile",file.getAbsolutePath());
                if(!file.exists()) {
                    try {
                        boolean result  = file.createNewFile();
                        Logger.logMessage("ResultOf","CreatingFile "+result);
                    } catch (IOException e) {
                        Logger.logMessage("Exception",e.getMessage());
                        e.printStackTrace();
                    }
                }
                return file;
            }

        }
        return null;
    }

    public File getFileForReadingInSDCard(String directoryName, String fileName,String extension)
    {
        if(isExternalStorageWritable())
        {
            if(AppPermissionTrackingTasks.hasWriteExternalStoragePermission(activity))
            {
                File storage = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED)[1];
                File fileDirectory = new File(UtilityTasks.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY);
                if(directoryName!=null)
                    fileDirectory = new File(UtilityTasks.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY+directoryName+"/");
                File directory = new File(fileDirectory.getAbsolutePath());
                File file = new File(directory, fileName+ extension);
                return file;
            }

        }
        return null;
    }


    public String getDirectoryPath(String pathType)
    {
        if(isExternalStorageWritable())
        {
//            File[] drives = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED);
            File[] drives = activity.getApplication().getExternalFilesDirs(Environment.DIRECTORY_DOCUMENTS);
            File storage = drives[0];
            if(drives.length>1)
                storage = drives[1];
            File fileDirectory = new File(storage.getAbsolutePath()+Constants.RECORDING_FILE_PATH);
            if(pathType.equals(Constants.IMAGE_FILE_PATH))
                fileDirectory = new File(storage.getAbsolutePath()+Constants.IMAGE_FILE_PATH);
            File directory = new File(fileDirectory.getAbsolutePath());
            if(!directory.exists())
            {
                boolean directryResult = directory.mkdirs();
//                Log.e("Directory","CreationResult For "+directory.getAbsolutePath());

            }
            return directory.getAbsolutePath();
        }
        return null;
    }

    public boolean isRecordedAudioFileAlreadyExists(String fileName)
    {
        File file = new File(getDirectoryPath(Constants.RECORDING_FILE_PATH), fileName+Constants.RECORDING_FILE_TYPE);
        if(file.exists()) {
            return true;
        }
        else return false;
    }
}