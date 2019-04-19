package com.stcodesapp.noteit.tasks.navigationTasks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.stcodesapp.noteit.ui.activities.ManualContactActivity;
import com.stcodesapp.noteit.ui.activities.NoteFieldActivity;

public class ActivityNavigationTasks {

    private Activity activity;

    public ActivityNavigationTasks(Activity activity) {
        this.activity = activity;
    }

    public void toSettingsScreen(Bundle args)
    {
        Intent intent = new Intent(activity, NoteFieldActivity.class);
        intent.putExtras(args);
        activity.startActivity(intent);
    }

    public void toNoteFieldScreen(Bundle args)
    {
        Intent intent = new Intent(activity, NoteFieldActivity.class);
        intent.putExtras(args);
        activity.startActivity(intent);
    }

    public void toManualContactScreen(Bundle args)
    {
        Intent intent = new Intent(activity, ManualContactActivity.class);
        intent.putExtras(args);
        activity.startActivity(intent);
    }

    public void closeScreen()
    {
        activity.finish();
    }
}
