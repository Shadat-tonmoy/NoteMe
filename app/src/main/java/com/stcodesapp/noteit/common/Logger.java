package com.stcodesapp.noteit.common;

import android.util.Log;

public class Logger {
    private static final boolean showLog = true;


    public static void logMessage(String tag, String message)
    {
        if(showLog)
        {
            Log.e(tag,message);
        }

    }
}
