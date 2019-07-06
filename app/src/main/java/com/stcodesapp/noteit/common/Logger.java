package com.stcodesapp.noteit.common;

import android.util.Log;

import com.stcodesapp.noteit.BuildConfig;

public class Logger {
    private static final boolean showLog = BuildConfig.DEBUG;


    public static void logMessage(String tag, String message)
    {
        if(showLog)
        {
            Log.e(tag,message);
        }

    }
}
