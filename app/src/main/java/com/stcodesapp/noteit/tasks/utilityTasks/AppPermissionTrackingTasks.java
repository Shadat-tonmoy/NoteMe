package com.stcodesapp.noteit.tasks.utilityTasks;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.stcodesapp.noteit.constants.RequestCode;

public class AppPermissionTrackingTasks {

    private Activity activity;
    private static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String READ_CONTACT = Manifest.permission.READ_CONTACTS;
    private static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String ACCESS_CAMERA = Manifest.permission.CAMERA;


    public AppPermissionTrackingTasks(Activity activity) {
        this.activity = activity;
    }

    public static boolean hasWriteExternalStoragePermission(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (activity.checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RequestCode.WRITE_EXTERNAL_STORAGE_PERMISSION);
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    public static boolean hasReadExternalStoragePermission(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (activity.checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RequestCode.READ_EXTERNAL_STORAGE_PERMISSION);
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    public static boolean hasContactReadPermission(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (activity.checkSelfPermission(READ_CONTACT) == PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, RequestCode.READ_CONTACT_PERMISSION);
                return false;
            }
        }
        else
        {
            return true;
        }
    }


    public static boolean hasCameraAccessPermission(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (activity.checkSelfPermission(ACCESS_CAMERA) == PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, RequestCode.OPEN_CAMERA_PERMISSION);
                return false;
            }
        }
        else
        {
            return true;
        }
    }
}
