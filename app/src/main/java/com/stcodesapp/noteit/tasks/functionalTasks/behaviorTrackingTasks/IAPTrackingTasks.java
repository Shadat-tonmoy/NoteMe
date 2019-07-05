package com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks;

import android.app.Activity;
import android.content.SharedPreferences;

import com.stcodesapp.noteit.constants.AppMetadata;

public class IAPTrackingTasks {

    private Activity activity;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final int PRIVATE_MODE = 0;

    public IAPTrackingTasks(Activity activity) {
        this.activity= activity;
        pref = activity.getSharedPreferences(AppMetadata.IAP_TRACKER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isPaidUser()
    {
        return pref.getBoolean(AppMetadata.IS_PAID_USER, false);
    }


    public void setPaidUser(boolean isPaid) {
        editor.putBoolean(AppMetadata.IS_PAID_USER, isPaid);
        editor.commit();
    }
}
