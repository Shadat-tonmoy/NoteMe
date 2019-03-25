package com.stcodesapp.noteit.factory;

import android.app.Activity;

import com.stcodesapp.noteit.tasks.navigationTasks.ScreenNavigationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.NoteAddScreenManipulationTasks;

public class TasksFactory {

    private Activity activity;

    public TasksFactory(Activity activity) {
        this.activity = activity;
    }

    public NoteAddScreenManipulationTasks getNoteAddScreenManipulationTasks()
    {
        return new NoteAddScreenManipulationTasks(activity);
    }

    public ScreenNavigationTasks getScreenNavigationTasks()
    {
        return new ScreenNavigationTasks(activity);
    }
}
