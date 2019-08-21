package com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass;

import android.app.Activity;

import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.BackupFragmentScreenView;

public class BackupFragmentScreenManipulationTask
{

    private Activity activity;
    private BackupFragmentScreenView backupFragmentScreenView;

    public BackupFragmentScreenManipulationTask(Activity activity) {
        this.activity = activity;
    }

    public void bindView(BackupFragmentScreenView backupFragmentScreenView) {
        this.backupFragmentScreenView = backupFragmentScreenView;
    }
}
