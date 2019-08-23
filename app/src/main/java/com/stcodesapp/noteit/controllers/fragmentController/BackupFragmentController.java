package com.stcodesapp.noteit.controllers.fragmentController;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.PermissionType;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.functionalTasks.dataBackupTasks.BackupRestoringTask;
import com.stcodesapp.noteit.tasks.functionalTasks.dataBackupTasks.BackupSavingTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.BackupFragmentScreenManipulationTask;
import com.stcodesapp.noteit.tasks.utilityTasks.AppPermissionTrackingTasks;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.BackupFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.BackupFragmentScreen;

public class BackupFragmentController implements BackupFragmentScreen.Listener, BackupSavingTask.Listener, BackupRestoringTask.Listener, BackupFragmentScreenManipulationTask.Listener
{

    private Activity activity;
    private TasksFactory tasksFactory;
    private BackupFragmentScreenView backupFragmentScreenView;
    private BackupFragmentScreenManipulationTask backupFragmentScreenManipulationTask;
    private int localStorageOption = Constants.LOCAL_STORAGE_PHONE;

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
                executeBackupToLocalStorageTask(Constants.LOCAL_STORAGE_BACKUP);
            else backupFragmentScreenManipulationTask.showPermissionRequiredMessage(PermissionType.WRITE_EXTERNAL_STORAGE);
        }
        else if(requestCode == RequestCode.READ_EXTERNAL_STORAGE_PERMISSION)
        {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
                executeBackupRestoreTask(Constants.LOCAL_STORAGE_RESTORE);
            else backupFragmentScreenManipulationTask.showPermissionRequiredMessage(PermissionType.READ_EXTERNAL_STORAGE_PERMISSION);
        }
    }

    private void executeBackupToLocalStorageTask(int backupType)
    {
        backupFragmentScreenManipulationTask.showBackupProgressDialog(Constants.BACKUP);
        BackupSavingTask backupSavingTask = tasksFactory.getBackupSavingTask();
        backupSavingTask.setListener(this);
        backupSavingTask.execute(backupType,localStorageOption);
    }

    private void executeBackupRestoreTask(int backupType)
    {
        backupFragmentScreenManipulationTask.showBackupProgressDialog(Constants.RESTORE);
        BackupRestoringTask backupRestoringTask = tasksFactory.getBackupRestoringTask();
        backupRestoringTask.setListener(this);
        backupRestoringTask.execute(backupType,localStorageOption);
    }

    @Override
    public void onBackupToLocalStorageClicked()
    {
        executeBackupToLocalStorageTask(Constants.LOCAL_STORAGE_BACKUP);
    }

    @Override
    public void onBackupToCloudStorageClicked() {

    }

    @Override
    public void onRestoreFromLocalStorageClicked()
    {
        backupFragmentScreenManipulationTask.showBackupRestoreWarningDialog(this);
    }

    @Override
    public void onRestoreFromCloudStorageClicked() {

    }

    @Override
    public void onLocalBackupStorageOptionSelected(int checkedID)
    {
        Logger.logMessage("StorageSelected",checkedID+" ");
        switch (checkedID)
        {
            case R.id.phone_storage_option:
                localStorageOption = Constants.LOCAL_STORAGE_PHONE;
                break;
            case R.id.sd_card_storage_option:
                localStorageOption = Constants.LOCAL_STORAGE_SD_CARD;
                break;
        }
    }

    @Override
    public void onBackupProcessFinished()
    {
        backupFragmentScreenManipulationTask.showBackupDoneView();

    }

    @Override
    public void onBackupProcessFailed()
    {
        backupFragmentScreenManipulationTask.showBackupFailedView();
    }

    @Override
    public void onBackupRestoreFinished()
    {
        backupFragmentScreenManipulationTask.showRestoreDoneView();

    }

    @Override
    public void onBackupRestoreFailed()
    {
        backupFragmentScreenManipulationTask.showRestoreFailedView();

    }

    @Override
    public void onRestoreFromLocalStorageConfirmed()
    {
        if(AppPermissionTrackingTasks.hasReadExternalStoragePermission(activity))
            executeBackupRestoreTask(Constants.LOCAL_STORAGE_RESTORE);
    }

    @Override
    public void onRestoreFromCloudStorageConfirmed() {

    }
}
