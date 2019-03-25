package com.stcodesapp.noteit.tasks.navigationTasks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.stcodesapp.noteit.activities.NoteAddActivity;

public class ScreenNavigationTasks {

    private final Activity activity;


    public ScreenNavigationTasks(Activity activity) {
        this.activity = activity;
    }

    public void toNoteFieldScreen(Bundle args)
    {
        Intent intent = new Intent(activity, NoteAddActivity.class);
        intent.putExtras(args);
        activity.startActivity(intent);
    }

    public void closeScreen()
    {
        activity.finish();
    }
}


