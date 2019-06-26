package com.stcodesapp.noteit.tasks.promotionalTask;

import android.app.Activity;
import android.content.SharedPreferences;
import com.stcodesapp.noteit.constants.AppMetadata;

public class RateUSPopupTrackingTasks {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Activity activity;
    private static final int PRIVATE_MODE = 0;

    public RateUSPopupTrackingTasks(Activity activity) {
        this.activity= activity;
        pref = activity.getSharedPreferences(AppMetadata.RATE_US_POPUP_TRACKER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setRateUsClicked(boolean isClicked) {
        editor.putBoolean(AppMetadata.IS_RATE_US_CLICKED, isClicked);
        editor.commit();
    }

    public boolean isRateUsClicked() {
        return pref.getBoolean(AppMetadata.IS_RATE_US_CLICKED, false);
    }

    public void updateTotalNoteCount() {
        int totalNote = pref.getInt(AppMetadata.TOTAL_NOTE, 0);
        totalNote++;
        editor.putInt(AppMetadata.TOTAL_NOTE, totalNote);
        editor.commit();
    }

    public int getTotalNote() {
        return pref.getInt(AppMetadata.TOTAL_NOTE, 0);
    }
}
