package com.stcodesapp.noteit.tasks.functionalTasks;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.models.Contact;

import java.io.File;
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

    public void getAudioFileFromURI(Uri data)
    {
        try {
            String[] filePathColumn = {"_display_name","last_modified","_size","document_id"};
//            for(String column : filePathColumn)
//            {
//                Log.e("Column","II "+column);
//            }
            Cursor cursor = activity.getContentResolver().query(data, null, null, null, null);
            if(cursor.moveToFirst()){
                String[] columns = cursor.getColumnNames();
                for(String column : columns)
                {
                    Log.e("Supported","Column "+column);
                }
                int nameIndex = cursor.getColumnIndex(filePathColumn[0]);
                int modifiedIndex= cursor.getColumnIndex(filePathColumn[1]);
                int sizeIndex= cursor.getColumnIndex(filePathColumn[2]);
                int docIndex= cursor.getColumnIndex(filePathColumn[3]);
                Log.e("Indices",nameIndex+" "+modifiedIndex+" "+sizeIndex);
                String name = cursor.getString(nameIndex);
                String modified = cursor.getString(modifiedIndex);
                String size = cursor.getString(sizeIndex);
                String document = cursor.getString(docIndex);
                Log.e("Params",name+" "+modified+" "+size+" "+document);
//                Log.e("FlePath","File Pah is "+yourRealPath);
            } else {
                //boooo, cursor doesn't have rows ...
            }
            cursor.close();
        }
        catch (Exception e)
        {
            Log.e("Exception",e.getMessage());
        }

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
    public void openAudioFile(Uri fileURI)
    {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(fileURI, Constants.OPEN_AUDIO_FILE_TYPE);
        /*Uri fileURI = FileProvider.getUriForFile(
                activity,
                activity.getApplicationContext()
                        .getPackageName() + ".provider", file);*/
        File file = new File(fileURI.getPath());
        Log.e("Authority",fileURI.getAuthority()+" is File "+file.getAbsolutePath());
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
            Log.e("Exceepion",e.getMessage());
        }

    }

}