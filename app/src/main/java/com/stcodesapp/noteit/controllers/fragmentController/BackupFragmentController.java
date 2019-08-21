package com.stcodesapp.noteit.controllers.fragmentController;

import android.app.Activity;
import android.widget.Toast;

import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.functionalTasks.BackupSavingTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.BackupFragmentScreenManipulationTask;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.BackupFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.BackupFragmentScreen;

public class BackupFragmentController implements BackupFragmentScreen.Listener
{

    private Activity activity;
    private TasksFactory tasksFactory;
    private BackupFragmentScreenView backupFragmentScreenView;
    private BackupFragmentScreenManipulationTask backupFragmentScreenManipulationTask;

    public BackupFragmentController(Activity activity, TasksFactory tasksFactory) {
        this.activity = activity;
        this.tasksFactory = tasksFactory;
        this.backupFragmentScreenManipulationTask = tasksFactory.getBackupFragmentScreenManipulationTask();
    }

    public void bindView(BackupFragmentScreenView backupFragmentScreenView)
    {
        this.backupFragmentScreenView = backupFragmentScreenView;
        this.backupFragmentScreenManipulationTask.bindView(backupFragmentScreenView);
    }

    public void onStart()
    {
        backupFragmentScreenView.registerListener(this);
    }

    public void onStop()
    {
        backupFragmentScreenView.unregisterListener(this);
    }

    @Override
    public void onBackupToLocalStorageClicked()
    {
        BackupSavingTask backupSavingTask = tasksFactory.getBackupSavingTask();
        backupSavingTask.execute();
        Toast.makeText(activity, "Will backup to local storage", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackupToCloudStorageClicked() {

    }

    @Override
    public void onRestoreFromLocalStorageClicked()
    {
        Toast.makeText(activity, "Will restore from local storage", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRestoreFromCloudStorageClicked() {

    }
}
