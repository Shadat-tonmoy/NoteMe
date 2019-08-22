package com.stcodesapp.noteit.controllers.fragmentController;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.PermissionType;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.database.Backup;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.functionalTasks.BackupSavingTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.BackupFragmentScreenManipulationTask;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.BackupFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.BackupFragmentScreen;

public class BackupFragmentController implements BackupFragmentScreen.Listener, BackupSavingTask.Listener
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == RequestCode.WRITE_EXTERNAL_STORAGE_PERMISSION)
        {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
                executeBackupToLocalStorageTask();
            else backupFragmentScreenManipulationTask.showPermissionRequiredMessage(PermissionType.CONTACT_READ_PERMISSION);
        }
    }

    private void executeBackupToLocalStorageTask()
    {
        backupFragmentScreenManipulationTask.showBackupProgressDialog();
        BackupSavingTask backupSavingTask = tasksFactory.getBackupSavingTask();
        backupSavingTask.setListener(this);
        backupSavingTask.execute(Constants.LOCAL_STORAGE_BACKUP);
//        Toast.makeText(activity, "Will backup to local storage", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackupToLocalStorageClicked()
    {
        executeBackupToLocalStorageTask();
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

    @Override
    public void onBackupProcessFinished()
    {
        backupFragmentScreenManipulationTask.showBackupDoneView();

    }
}
