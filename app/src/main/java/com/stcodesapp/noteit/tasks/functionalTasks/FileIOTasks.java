package com.stcodesapp.noteit.tasks.functionalTasks;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.constants.Tags;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
        Log.e("DataDir",Environment.getDataDirectory().getAbsolutePath()+"");
    }

    public void openFilePickerForImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, RequestCode.OPEN_IMAGE_FILE);
    }

    public void openContactPicker()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        activity.startActivityForResult(intent, RequestCode.OPEN_CONTACT_LIST);
    }

    public void readContact(Intent intent)
    {
        Uri contactData = intent.getData();
        String number = "";
        String name = "";
        Cursor cursor = activity.getContentResolver().query(contactData, null, null, null, null);
        Log.e("Cursor",cursor.getCount()+" is Size ");
        cursor.moveToFirst();
        String hasPhone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
        Cursor phones = activity.getContentResolver().query
                (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = " + contactId, null, null);
        while (phones.moveToNext()) {
            number = phones.getString(phones.getColumnIndex
                    (ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("[-() ]", "");
            name = phones.getString(phones.getColumnIndex
                    (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)).replaceAll("[-() ]", "");
            Log.e("Contact","Number "+number+" Name "+name);
        }
        phones.close();
        cursor.close();


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

    public File getFileForSaving(String fileName)
    {
        /*if(isExternalStorageWritable())
        {
            File storage = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED)[0];
            File fileDirectory = new File(Utils.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY);
            File directory = new File(fileDirectory.getAbsolutePath());
            if(!directory.exists())
                directory.mkdirs();

            File file = new File(directory, fileName+ Constants.MP3_FILE_EXT);
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }*/
        return null;
    }

    public File getFileFromStream(String fileName, OutputStream outputStream)
    {
        /*if(isExternalStorageWritable())
        {
            File storage = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED)[0];
            File fileDirectory = new File(Utils.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY);
            File directory = new File(fileDirectory.getAbsolutePath());
            if(!directory.exists())
                directory.mkdirs();

            File file = new File(directory, fileName+ Constants.MP3_FILE_EXT);
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }*/
        return null;
    }

    public boolean isFileAlreadyExists(String fileName)
    {
        /*File storage = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED)[0];
        File fileDirectory = new File(Utils.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY);
        File directory = new File(fileDirectory.getAbsolutePath());
        if(!directory.exists())
            directory.mkdirs();

        File file = new File(directory, fileName+ Constants.MP3_FILE_EXT);
        if(file.exists()) {
            return true;
        }
        else
        */
        return false;
    }


    public String getStoragePath()
    {
        /*File storage = activity.getExternalFilesDirs(Environment.MEDIA_MOUNTED)[0];
        File fileDirectory = new File(Utils.getStoragePath(storage.getAbsolutePath())+Constants.FILE_DIRECTORY);
        return fileDirectory.getAbsolutePath();*/
        return null;
    }
    public void openAudioFile(String filePath)
    {
        /*Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        File file = new File(filePath);
        Uri fileURI = FileProvider.getUriForFile(
            activity,
            activity.getApplicationContext()
                    .getPackageName() + ".provider", file);
        intent.setDataAndType(fileURI, Constants.OPEN_AUDIO_FILE_TYPE);
        allowURIReadPermission(intent,fileURI);
        activity.startActivity(intent);*/
    }

    private void allowURIReadPermission(Intent intent,Uri uri)
    {
        List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            activity.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

}