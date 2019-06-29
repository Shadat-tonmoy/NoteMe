package com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks;

import android.app.Activity;
import android.content.SharedPreferences;

import com.stcodesapp.noteit.constants.AppMetadata;
import com.stcodesapp.noteit.constants.Constants;

public class AdStrategyTrackingTask {


    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Activity activity;
    private static final int PRIVATE_MODE = 0;

    public AdStrategyTrackingTask(Activity activity) {
        this.activity= activity;
        pref = activity.getSharedPreferences(AppMetadata.AD_SHOWING_TRACKER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setRateUsClicked(boolean isClicked) {
        editor.putBoolean(AppMetadata.IS_RATE_US_CLICKED, isClicked);
        editor.commit();
    }

    public boolean isRateUsClicked() {
        return pref.getBoolean(AppMetadata.IS_RATE_US_CLICKED, false);
    }

    public void updateNoteFieldOpenCount() {
        int totalNoteFieldOpen = pref.getInt(AppMetadata.TOTAL_NOTE_FIELD_OPENED, 0);
        if(totalNoteFieldOpen>=AppMetadata.MAX_NOTE_OPEN_TO_SHOW_FULL_SCREEN_AD)
            totalNoteFieldOpen = Constants.ZERO;
        else totalNoteFieldOpen++;
        editor.putInt(AppMetadata.TOTAL_NOTE_FIELD_OPENED, totalNoteFieldOpen);
        editor.commit();
    }

    public void updateCheckListOpenCount() {
        int totalCheckListOpen = pref.getInt(AppMetadata.TOTAL_CHECK_LIST_OPENED, 0);
        if(totalCheckListOpen >=AppMetadata.MAX_CHECK_LIST_OPEN_TO_SHOW_FULL_SCREEN_AD)
            totalCheckListOpen  = Constants.ZERO;
        else totalCheckListOpen++;
        editor.putInt(AppMetadata.TOTAL_NOTE_FIELD_OPENED, totalCheckListOpen );
        editor.commit();
    }

    public int getTotalNoteFieldOpenCount() {
        return pref.getInt(AppMetadata.TOTAL_NOTE_FIELD_OPENED, 0);
    }

    public int getTotalCheckListOpenCount() {
        return pref.getInt(AppMetadata.TOTAL_CHECK_LIST_OPENED, 0);
    }


}
